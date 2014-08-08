package joazlazer.mods.amc.util;

public class RecipeHelper {
    public static Object[] flipCircular(Object[] input) {
        Object[] output = new Object[9];
        output[0] = flipLine((String) input[0]);
        output[1] = ((String) output[0]).substring(1,2) + ((String) input[1]).substring(1,2) + ((String) output[0]).substring(1,2);
        output[2] = flipLine((String) input[2]);
        for(int i = 3; i < 9; i++) output[i] = input[i];
        return output;
    }

    public static String flipLine(String line) {
        return line.substring(1,2) + line.substring(0,1) + line.substring(1,2);
    }
}
