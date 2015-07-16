package joazlazer.mods.amccore;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class AMCCClassTransformer implements net.minecraft.launchwrapper.IClassTransformer {
    @Override
    public byte[] transform(String workingClass, String newName, byte[] bytes) {
        if (newName.equals("net.minecraft.client.Minecraft")) {
            System.out.println("[AMCCore] Patching " + workingClass);
            return patchMinecraftASM(workingClass, bytes, false);
        } else if (newName.equals("net.minecraft.server.integrated.IntegratedServer")) {
            System.out.println("[AMCCore] Patching " + workingClass);
            return patchIntegratedServerASM(workingClass, bytes, false);
        }

        return bytes;
    }

    public byte[] patchMinecraftASM(String name, byte[] bytes, boolean obfuscated) {
        byte[] bcode; {
            ClassReader classReader = new ClassReader(bytes);
            ClassWriter classWriter = new ClassWriter(classReader, Opcodes.ASM4);
            MinecraftPatcher mp = new MinecraftPatcher(classWriter);
            classReader.accept(mp, 0);
            bcode = classWriter.toByteArray();
        }
        return bcode;
    }

    public static class MinecraftPatcher extends ClassVisitor {
        public MinecraftPatcher(ClassVisitor cv) {
            super(ASM5, cv);
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
        }
    }

    public byte[] patchIntegratedServerASM(String name, byte[] bytes, boolean obfuscated) {
        byte[] bcode; {
            ClassReader classReader = new ClassReader(bytes);
            ClassWriter classWriter = new ClassWriter(classReader, Opcodes.ASM4);
            IntegratedServerPatcher isp = new IntegratedServerPatcher(classWriter);
            classReader.accept(isp, ClassReader.SKIP_FRAMES);
            bcode = classWriter.toByteArray();
        }
        return bcode;
    }

    public static class IntegratedServerPatcher extends ClassVisitor {
        public IntegratedServerPatcher(ClassVisitor cv) {
            super(ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String sign, String[] ex) {
            MethodVisitor mv = super.visitMethod(access, name, desc, sign, ex);
            if(name.equals("func_71217_p") || name.equals("tick")) {
                return new AddSaveExceptionTransformer(mv);
            }
            return mv;
        }

        public static class AddSaveExceptionTransformer extends MethodVisitor {
            public int counter;
            public int counter2;
            public boolean ladded;
            public Label mylabel;
            public AddSaveExceptionTransformer(MethodVisitor mv) {
                super(ASM4, mv);
            }

            @Override
            public void visitInsn(int opcode) {
                super.visitCode();
                if(opcode == Opcodes.IFEQ) {
                    counter++;
                    if(counter == 2) {
                        mylabel = new Label();
                        super.visitFieldInsn(GETSTATIC, "joazlazer/mods/amccore/PauseTweaks", "inMenu", Type.BOOLEAN_TYPE.getDescriptor());
                        super.visitJumpInsn(IFEQ, mylabel);
                    }
                }
            }

            @Override
            public void visitLabel(Label label) {
                if(counter2 == 2 && !ladded) {
                    super.visitLabel(mylabel);
                    ladded = true;
                }
                super.visitLabel(label);
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {
                super.visitIntInsn(opcode, operand);
                if(opcode == Opcodes.ICONST_0) {
                    counter2++;
                }
            }
        }
    }
}
