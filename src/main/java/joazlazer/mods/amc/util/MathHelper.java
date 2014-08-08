package joazlazer.mods.amc.util;

import joazlazer.mods.amc.util.vec.Vertex2D;
import joazlazer.mods.amc.util.vec.VertexList;
import javax.vecmath.Vector4d;
import java.util.HashMap;

public class MathHelper {

    public static final HashMap<String, VertexList> circleCache = new HashMap<String, VertexList>();
    public static final HashMap<String, VertexList> arcCache = new HashMap<String, VertexList>();

    public static double angleBetweenIntersectingVectors(Vector4d a, Vector4d b) {
        double radians = a.angle(b);
        double radianUnit = 180.0D / Math.PI;
        return (radians * radianUnit);
    }

    public static VertexList arc(int cx, int cy, int radius, int num_segments, int startAngle, int arcAngle) {
        return null;
    }

    public static VertexList circle(int cx, int cy, int radius) {
        return circle(cx, cy, radius, getEstimatedSegmentCount("circle", radius));
    }

    public static VertexList circle(int cx, int cy, int radius, int num_segments) {
        // If the circle is in the cache, return the cached circle,
        if(circleCache.containsKey(getCacheKey(radius, num_segments))) return circleCache.get(getCacheKey(radius, num_segments)).copy().translate(cx, cy);

        // Create the ending variable.
        VertexList vertices = new VertexList();

        // Populate the ending list.
        {
            float theta = 2 * 3.141592654F / (float) num_segments;
            float tangetial_factor = (float) Math.tan(theta);
            float radial_factor = (float) Math.cos(theta);
            float x = radius;
            float y = 0;

            for(int ii = 0; ii < num_segments; ii++) {

                // Add a point.
                vertices.add(new Vertex2D((int) (x + cx), (int) (y + cy)));

                float tx = -y;
                float ty = x;

                x += tx * tangetial_factor;
                y += ty * tangetial_factor;

                x *= radial_factor;
                y *= radial_factor;
            }
        }

        if(!circleCache.containsKey(getCacheKey(radius, num_segments))) circleCache.put(getCacheKey(radius, num_segments), vertices);

        // Return the ending variable.
        return vertices;
    }

    public static int getEstimatedSegmentCount(String type, int arg) {
        if(type.equalsIgnoreCase("circle")) {
            return (int) (10.0D * Math.sqrt(arg));
        }
        else return -1;
    }

   public static String getCacheKey(int radius, int num_segments) {
        return radius + ":" + num_segments;
    }
}
