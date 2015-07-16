package joazlazer.mods.amccore;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class AMCCClassTransformer implements net.minecraft.launchwrapper.IClassTransformer {
    @Override
    public byte[] transform(String workingClass, String newName, byte[] bytes) {
        if (workingClass.equals("bao")) {
            System.out.println("********* INSIDE OBFUSCATED MINECRAFT ABOUT TO PATCH: " + workingClass);
            return patchMinecraftASM(workingClass, bytes, true);
        }

        if (workingClass.equals("net.minecraft.client.Minecraft")) {
            System.out.println("********* INSIDE MINECRAFT ABOUT TO PATCH: " + workingClass);
            return patchMinecraftASM(workingClass, bytes, false);
        }

        if (workingClass.equals("bsx")) {
            System.out.println("********* INSIDE OBFUSCATED INTEGRATED SERVER ABOUT TO PATCH: " + workingClass);
            return patchIntegratedServerASM(workingClass, bytes, false);
        }

        if (workingClass.equals("net.minecraft.server.integrated.IntegratedServer")) {
            System.out.println("********* INSIDE INTEGRATED SERVER ABOUT TO PATCH: " + workingClass);
            return patchIntegratedServerASM(workingClass, bytes, false);
        }

        return bytes;
    }

    public byte[] patchMinecraftASM(String name, byte[] bytes, boolean obfuscated) {
        //String targetMethodName = obfuscated ? "ak" : "runGameLoop";

        byte[] bcode; {
            ClassReader classReader = new ClassReader(bytes);
            ClassWriter classWriter = new ClassWriter(classReader, Opcodes.ASM4);
            CustomClassVisitor ccv = new CustomClassVisitor(classWriter);
            classReader.accept(ccv, 0);
            bcode = classWriter.toByteArray();
        }

        //set up ASM class manipulation stuff. Consult the ASM docs for details
        ClassReader cr = new ClassReader(bcode);
        ClassNode classNode = new ClassNode();
        cr.accept(classNode, 0);

        Iterator<FieldNode> fields = classNode.fields.iterator();
        while(fields.hasNext()) {
            FieldNode f = fields.next();
            System.out.println(f.name);
        }

        return bcode;





    }

    public byte[] patchIntegratedServerASM(String name, byte[] bytes, boolean obfuscated) {
        String targetMethodName;
        //Our target method
        if (obfuscated == true)
            targetMethodName = "u";
        else
            targetMethodName = "tick";

        //set up ASM class manipulation stuff. Consult the ASM docs for details
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        //Now we loop over all of the methods declared inside the Explosion class until we get to the targetMethodName "runGameLoop"
        Iterator<MethodNode> methods = classNode.methods.iterator();
        while (methods.hasNext()) {
            MethodNode m = methods.next();

            //Check if this is runGameLoop and it's method signature is ()V which means that it returns a void (V)
            if ((m.name.equals(targetMethodName) && m.desc.equals("()V"))) {
                LabelNode L5 = null;
                AbstractInsnNode targetNode = null;
                System.out.println("********* Inside target method!");
                {
                    AbstractInsnNode currentNode;

                    @SuppressWarnings("unchecked")
                    Iterator<AbstractInsnNode> iter = m.instructions.iterator();

                    int c = 0;

                    //Loop over the instruction set and find the instruction L5 node.
                    while (iter.hasNext()) {
                        currentNode = iter.next();

                        //Found it! save the index location of instruction L5 node.
                        if (currentNode.getOpcode() == ICONST_0) {
                            c++;
                            if (c == 2) {
                                iter.next();
                                L5 = (LabelNode) iter.next();
                                System.out.println("found it!");
                                System.out.println(L5);
                            }
                        }
                    }
                }

                {
                    AbstractInsnNode currentNode;

                    @SuppressWarnings("unchecked")
                    Iterator<AbstractInsnNode> iter = m.instructions.iterator();

                    int c = 0;

                    //Loop over the instruction set and find the instruction IFEQ node that goes to L5.
                    while (iter.hasNext()) {
                        currentNode = iter.next();

                        //Found it! save the index location of instruction IFEQ and the second node for this instruction
                        if (currentNode.getOpcode() == IFEQ) {
                            c++;
                            if (c == 2) {
                                targetNode = currentNode;
                                System.out.println("found it!");
                                System.out.println(targetNode.toString());
                            }
                        }
                    }
                }

                // Make new instruction list
                InsnList toInject = new InsnList();

                // Add own instruction lists.
                toInject.add(new VarInsnNode(ALOAD, 0));
                toInject.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/Minecraft", "inMenu", org.objectweb.asm.Type.BOOLEAN_TYPE.getDescriptor()));
                toInject.add(new JumpInsnNode(IFEQ, L5));


                m.instructions.insert(targetNode, toInject);

                System.out.println("Patching Complete!");
                break;
            }
        }
        //ASM specific for cleaning up and returning the final bytes for JVM processing.
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    public static class CustomClassVisitor extends ClassVisitor {
        public CustomClassVisitor(ClassVisitor cv) {
            super(ASM5, cv);
        }

        @Override
        public void visitEnd() {
            super.visitField(Opcodes.ACC_PUBLIC, "inMenu", Type.BOOLEAN_TYPE.getDescriptor(), null, null);
            super.visitEnd();
        }
    }
}
