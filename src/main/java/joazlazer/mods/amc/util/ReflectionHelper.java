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
                return obj;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                privateField.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static void setPrivateField(Class className, Object instance, String name, Object newValue) {
        Field privateField = null;
        try {
            privateField = className.getDeclaredField(name);
            privateField.setAccessible(true);
            try {
                privateField.set(instance, newValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                privateField.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
