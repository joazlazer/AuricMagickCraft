package joazlazer.mods.amc.api.order;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import joazlazer.mods.amc.common.reference.Reference;
import joazlazer.mods.amc.utility.GuiColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class OrderBase extends IForgeRegistryEntry.Impl<OrderBase> {
    public static IForgeRegistry<OrderBase> registry;
    // The texture path for the icon.
    private ResourceLocation texture;
    private ResourceLocation largeTexture;
    // The unlocalized name for the order.
    private String unlocName;
    private String practicer;
    private Color color;

    public OrderBase() {
        color = Color.LIGHT_GRAY;
    }

    public ResourceLocation getLargeTexture() {
        return largeTexture;
    }

    public void setLargeTexture(ResourceLocation largeTexture) {
        this.largeTexture = largeTexture;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getPracticer() {
        return practicer;
    }

    public OrderBase setPracticer(String practicer) {
        this.practicer = practicer;
        return this;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getUnlocName();
    }

    public String getUnlocName() {
        // Return the unlocalized name.
        return unlocName;
    }

    public OrderBase setUnlocName(String newName) {
        // Change the unlocalized name to the new one.
        unlocName = newName;
        return this;
    }

    public ResourceLocation getTexture() {
        // Return the texture location.
        return texture;
    }

    public OrderBase setTexture(ResourceLocation newLoc) {
        // Change the texture location to the new one.
        texture = newLoc;
        return this;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerRegistries(RegistryEvent.NewRegistry event) {
            RegistryBuilder rb = new RegistryBuilder<OrderBase>();
            rb.setType(OrderBase.class);
            rb.setName(new ResourceLocation(Reference.MOD_ID, "orders"));
            registry = rb.create();
        }
    }

    @SideOnly(Side.CLIENT)
    public List<String> getTooltip() {
        ArrayList<String> tt = new ArrayList<>();
        tt.add(GuiColor.YELLOW + I18n.format("order." + this.getUnlocName() + ".name"));
        tt.add(GuiColor.ITALIC + I18n.format("order." + this.getUnlocName() + ".desc"));
        return tt;
    }

    public String getName() {
        return I18n.format("order." + this.getUnlocName() + ".name");
    }

    public String getDescription() {
         return I18n.format("order." + this.getUnlocName() + ".desc");
    }

    public List<String> getInfo() {
        List<String> lines = new ArrayList<>();
        String baseInfo = I18n.format("order." + this.getUnlocName() + ".info");
        Pattern paragraphParsing = Pattern.compile("(?:^|\\$%\\$)([\\S\\s]+?)(?=$|\\$%\\$)");
        Matcher paragraphMatcher = paragraphParsing.matcher(baseInfo);
        while(paragraphMatcher.find()) {
            lines.add(paragraphMatcher.group(1));
        }

        // Add null lines between paragraphs
        int numOfLines = lines.size();
        for(int i = 0; i < numOfLines - 1; ++i) {
            int j = 2 * i + 1;
            lines.add(j, null);
        }
        return lines;
    }
}