package joazlazer.mods.amc.api.spell;

import cpw.mods.fml.relauncher.SideOnly;
import joazlazer.mods.amc.casting.CastingStatus;
import joazlazer.mods.amc.casting.client.ClientCastingStatus;
import joazlazer.mods.amc.util.Color;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;

public class SpellBase {
    private ArrayList<String> tooltip;
    // The unlocalized name for the order.
    private String unlocName;
    private boolean isSpecialty;
    private int x, y;
    private ResourceLocation textureLocation;
    private Color color;

    public void setLargeTexture(ResourceLocation largeTexture) {
        this.largeTexture = largeTexture;
    }

    private ResourceLocation largeTexture;

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the textureLocation
     */
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    /**
     * @param textureLocation the textureLocation to set
     */
    public void setTextureLocation(ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }

    /**
     * @return the isSpecialty
     */
    public boolean isSpecialty() {
        return isSpecialty;
    }

    /**
     * @param isSpecialty the isSpecialty to set
     */
    public void setSpecialty(boolean isSpecialty) {
        this.isSpecialty = isSpecialty;
    }

    /**
     * @return the tooltip
     */
    public ArrayList<String> getTooltip() {
        return tooltip;
    }

    /**
     * @param tooltip the tooltip to set
     */
    public void setTooltip(ArrayList<String> tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * @return the unlocName
     */
    public String getUnlocName() {
        return unlocName;
    }

    /**
     * @param unlocName the unlocName to set
     */
    public void setUnlocName(String unlocName) {
        this.unlocName = unlocName;
    }

    public void onStartCasting(EntityPlayer player, World world, CastingStatus status) {

    }

    public void onStopCasting(EntityPlayer player, World world, CastingStatus status) {

    }

    public void onCancelCasting(EntityPlayer player, World world, CastingStatus status) {

    }

    public void onInterruptCasting(EntityPlayer player, World world, CastingStatus status) {

    }

    public void onServerCastTick(EntityPlayer player, World world, CastingStatus status) {

    }

    public void renderOtherPlayer(World world, float partialTicks, ClientCastingStatus status) {

    }

    public void renderFirstPerson(World world, float partialTicks, ClientCastingStatus status) {

    }

    public void renderThirdPerson(World world, float partialTicks, ClientCastingStatus status) {

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public ResourceLocation getLargeTexture() {
        return largeTexture;
    }
}
