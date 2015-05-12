package joazlazer.mods.amc.util;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static Object getPrivateField(Class className, Object instance, String name) {
        Field privateField = null;
        try {
            privateField = className.getDeclaredField(name);
            privateField.setAccessible(true);
            Object obj = null;
            try {
                obj = privateField.get(instance);
                privateField.setAccessible(false);
                return obj;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }
}
