package joazlazer.mods.amccore.tweaks;

import joazlazer.mods.amc.sevencommons.asm.MCPNames;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class IntegratedServerPauseFix extends ClassVisitor {
    public IntegratedServerPauseFix(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String sign, String[] ex) {
        MethodVisitor mv = super.visitMethod(access, name, desc, sign, ex);
        if (name.equals(MCPNames.method(MCPNames.M_SERVER_TICK))) {
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
            super.visitInsn(opcode);
            if (opcode == Opcodes.IFEQ) {
                System.out.println("IFEQ " + (counter + 1));
                counter++;
                if (counter == 2) {
                    mylabel = new Label();
                    super.visitFieldInsn(GETSTATIC, "joazlazer/mods/amccore/PauseTweaks", "inMenu", Type.BOOLEAN_TYPE.getDescriptor());
                    super.visitJumpInsn(IFEQ, mylabel);
                }
            }
        }

        @Override
        public void visitLabel(Label label) {
            if (counter2 == 2 && !ladded) {
                super.visitLabel(mylabel);
                ladded = true;
            }
            super.visitLabel(label);
        }

        @Override
        public void visitIntInsn(int opcode, int operand) {
            super.visitIntInsn(opcode, operand);
            if (opcode == Opcodes.ICONST_0) {
                counter2++;
            }
        }
    }
}
