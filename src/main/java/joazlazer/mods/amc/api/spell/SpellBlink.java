package joazlazer.mods.amc.api.spell;

import joazlazer.mods.amc.casting.CastingStatus;
import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.util.Color;
import joazlazer.mods.amc.util.GuiColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.HashMap;

public class SpellBlink extends SpellBase {
    public static final String DURING_CAST_STATE_KEY = "duringCastStateKey";
    public static class BlinkStatus {
        private boolean casting;
        public BlinkStatus setCasting(boolean newValue) {
            casting = newValue;
            return this;
        }
        public boolean getCasting() {
            return casting;
        }
    }
    public static HashMap<String, BlinkStatus> Blinks = new HashMap<String, BlinkStatus>();

    public SpellBlink() {
        super();
        setUnlocName("spellBlink");
        setTextureLocation(Textures.Spells.BLINK);
        setLargeTexture(Textures.Spells.BLINK_LARGE);
        setColor(new Color(73, 150, 36));
        ArrayList<String> tt = new ArrayList<String>();
        populateTooltip(tt);
        this.setTooltip(tt);
    }

    public void populateTooltip(ArrayList<String> tt) {
        tt.add(GuiColor.MAGENTA + " - Blink:");
        tt.add(GuiColor.LIGHTGRAY + "  A spell used for short-distance teleportation.");
        tt.add(GuiColor.LIGHTGRAY + "  One of the most basic spells, it is known by most ");
        tt.add(GuiColor.LIGHTGRAY + "  mages. It is cast by concentrating on where");
        tt.add(GuiColor.LIGHTGRAY + "  one wants to be and it will be so.");
        tt.add("");
        tt.add(GuiColor.TURQUISE + " - Cooldown: " + GuiColor.LIGHTGRAY + "Low");
        tt.add(GuiColor.PINK + " - Mana Cost: " + GuiColor.LIGHTGRAY + "Very Low");
        tt.add(GuiColor.YELLOW + " - Specialty? " + GuiColor.LIGHTGRAY + this.isSpecialty());
    }

    @Override
    public void onServerCastTick(EntityPlayer player, World world, CastingStatus status) {
        System.out.print(".");
    }

    @Override
    public void onStartCasting(EntityPlayer player, World world, CastingStatus status) {
        System.out.println("Blink onStartCasting(EntityPlayer, World, CastingStatus) called. player: " + player + "; username: " + player.getDisplayName() + "; world: " + world + "; status: " + status);
        if(Blinks.containsKey(player.getDisplayName()) && Blinks.get(player.getDisplayName()).getCasting()) {
            Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(false));
            System.out.println("We are casting, so turn it off");
        }
        else {
            Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(true));
            System.out.println("Set casting to true");
            System.out.println();
            status.receiveTick = true;
        }
    }

    @Override
    public void onCancelCasting(EntityPlayer player, World world, CastingStatus status) {
        Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(false));
        status.receiveTick = false;
    }

    @Override
    public void onInterruptCasting(EntityPlayer player, World world, CastingStatus status) {
        Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(false));
        status.receiveTick = false;
    }

    @Override
    public void onStopCasting(EntityPlayer player, World world, CastingStatus status) {
        if(!Blinks.get(player.getDisplayName()).getCasting()) {
            status.receiveTick = false;
            // Teleport the player here.
        }
    }


}
