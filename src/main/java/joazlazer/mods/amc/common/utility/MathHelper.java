package joazlazer.mods.amc.common.utility;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.awt.geom.Point2D;

public class MathHelper {
    /**
     * Interpolates between the two given y values
     * @param y1
     * @param y2
     * @param mu The x value to use; in the domain [0,1]
     * @return
     */
    public static float sinInterp(float y1, float y2, float mu) {
        // Ensure mu is clamped;
        mu = net.minecraft.util.math.MathHelper.clamp(mu, 0.0f, 1.0f);

        // Calculate the alpha value at mu
        float alpha = 0.5f * (float)Math.sin(((3.142f * mu) - 1.571f)) + 0.5f;

        // Return the y value between the two given
        return (y2 - y1) * alpha + y1;
    }

    /**
     * Same as sinInterp, except that once mu >= limit, the function is equal to y2
     * @param y1
     * @param y2
     * @param limit The x value at which the function will reach its y2 and stay there; in the domain [0,1]
     * @param mu The x value to use for blending; in the domain [0,1]
     * @return
     */
    public static float limitedSinInterp(float y1, float y2, float limit, float mu) {
        if(mu >= limit) {
            return y2;
        } else {
            return sinInterp(y1, y2, mu * (1f / limit));
        }
    }

    /**
     * Converts a Vec3i object to a Vec3d
     * @param vector
     * @return
     */
    public static Vec3d toVec3d(Vec3i vector) {
        return new Vec3d(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Lerps between the two given yaw values, ensuring that the output is wrapped to fall under the [-180,180] interval
     * @param yawA
     * @param yawB
     * @param mu The x value to use for blending; in the domain [0,1]
     * @return
     */
    public static float lerpYaw(float yawA, float yawB, float mu) {
        // Ensure mu is clamped;
        mu = net.minecraft.util.math.MathHelper.clamp(mu, 0.0f, 1.0f);

        if(Math.abs(yawA - yawB) <= 180f) {
            // wrapping unneeded
            float delta = yawB - yawA;

            // return normal lerp
            return yawA + (delta * mu);
        } else {
            // wrapping needed
            float delta = 0f;
            if(yawA < yawB) {
                // wrap backwards and around
                delta = -360f + (yawB - yawA);
            } else {
                // wrap forwards and around
                delta = 360f + (yawB - yawA);
            }

            // return normal lerp, except correct for wrapping
            return wrapScale(yawA + (delta * mu), -180f, 180f);
        }
    }

    /**
     * Constrains the input to fall under the bounds set to correct a value in a continuous scale (such as angle)
     * @param in
     * @param boundMin Must be less than boundMax
     * @param boundMax Must be greater than boundMin
     * @return A corrected output that falls between [boundMin, boundMax]
     */
    public static float wrapScale(float in, float boundMin, float boundMax) {
        // If in is behind the scale bounds, shift it forwards
        while(in < boundMin) {
            in += boundMax - boundMin;
        }

        // If in is in front of the scale bounds, shift it backwards
        while(in > boundMax) {
            in += boundMin - boundMax;
        }

        return in;
    }

    /**
     * Standard linear interpolation
     * @param a
     * @param b
     * @param mu The x value to use for blending; in the domain [0,1]
     * @return
     */
    public static float lerp(float a, float b, float mu) {
        // Ensure mu is clamped;
        mu = net.minecraft.util.math.MathHelper.clamp(mu, 0.0f, 1.0f);

        float delta = b - a;
        return a + (delta * mu);
    }

    /**
     * Convenience pass-through to net.minecraft.utility.math.MathHelper.clamp(in, min, max)
     * @param in
     * @param min
     * @param max
     * @return
     */
    public static float clamp(float in, float min, float max) {
        return net.minecraft.util.math.MathHelper.clamp(in, min, max);
    }

    public static Point2D randomPointInCircle(double radius) {
        double randRadius = Math.random() * radius;
        double randAngle = Math.random() * (2d * Math.PI);
        float x = (float)(randRadius * Math.cos(randAngle));
        float y = (float)(randRadius * Math.sin(randAngle));
        return new Point2D.Float(x, y);
    }

    public static float middlePeakInterp(float y1, float y2, float mu) {
        // Ensure mu is clamped;
        mu = net.minecraft.util.math.MathHelper.clamp(mu, 0.0f, 1.0f);

        // Calculate the alpha value at mu
        float alpha = -2f * Math.abs(mu - 0.5f) + 1f;

        // Return the y value between the two given
        return (y2 - y1) * alpha + y1;
    }
}
