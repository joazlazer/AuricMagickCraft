package joazlazer.mods.amc.entity.player;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.api.spell.SpellBlink;
import joazlazer.mods.amc.handlers.NetworkHandler;
import joazlazer.mods.amc.network.MessagePlayerTrackerUpdate;
import joazlazer.mods.amc.util.LogHelper;
import joazlazer.mods.amc.util.exception.MapNoContainsKeyException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerTracker {
    // Current hash map.
    public ConcurrentHashMap<String, PlayerData> playerStats = new ConcurrentHashMap<String, PlayerData>();

    public PlayerTracker() {
        FMLCommonHandler.instance().bus().register(this);
        PlayerDataRegistry.registerBoolean(PlayerTrackerConstants.AWAKE_BOOLEAN, PlayerTrackerConstants.AWAKE_BOOLEAN_DEFAULT);
        PlayerDataRegistry.registerString(PlayerTrackerConstants.ORDER_STRING, PlayerTrackerConstants.ORDER_STRING_DEFAULT);
        PlayerDataRegistry.registerInteger(PlayerTrackerConstants.SELECTED_SPELL, PlayerTrackerConstants.SELECTED_SPELL_DEFAULT);
        PlayerDataRegistry.registerString(PlayerTrackerConstants.SELECTED_SPELL_NAME, PlayerTrackerConstants.SELECTED_SPELL_NAME_DEFAULT);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        playerStats.put(event.player.getDisplayName(), PlayerDataFactory.newData(event.player));
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        System.out.println("LOGGED OUT! :D :D");
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (hasData(event.player.getDisplayName()))
            getData(event.player.getDisplayName()).player = new WeakReference<EntityPlayer>(event.player);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (hasData(event.player.getDisplayName()))
            getData(event.player.getDisplayName()).player = new WeakReference<EntityPlayer>(event.player);
    }

    public boolean hasData(EntityPlayer player) {
        return playerStats.containsKey(player.getDisplayName());
    }

    public boolean hasData(String username) {
        return playerStats.containsKey(username);
    }

    public boolean isAwake(String username) {
        try {
            return hasData(username) && getData(username).getBoolean(PlayerTrackerConstants.AWAKE_BOOLEAN);
        } catch (Exception ex) {
            LogHelper.fatal("Something terribly has gone wrong with PlayerTracker!!!:");
            LogHelper.fatal(ex.getStackTrace());
            throw new RuntimeException((ex));
        }
    }

    public PlayerData getData(String username) {
        if (hasData(username)) return playerStats.get(username);
        else {
            LogHelper.error("Player stats get accessor failed: no PlayerData objects exists that is mapped to username '" + username + "'.");
            return null;
        }
    }

    public PlayerData getData(EntityPlayer player) {
        return getData(player.getDisplayName());
    }

    public void awaken(EntityPlayer player, OrderBase newOrder) {
        try {
            System.out.println("TOLD TO AWAKEN!");
            if (!playerStats.contains(player.getDisplayName()))
                playerStats.put(player.getDisplayName(), PlayerDataFactory.newData(player));
            getData(player).setBoolean(PlayerTrackerConstants.AWAKE_BOOLEAN, true);
            updateToOther(player.getDisplayName(), PlayerTrackerConstants.AWAKE_BOOLEAN, true);
            getData(player).setString(PlayerTrackerConstants.ORDER_STRING, newOrder.getUnlocName());
            updateToOther(player.getDisplayName(), PlayerTrackerConstants.ORDER_STRING, newOrder.getUnlocName());
            try {
                AuricMagickCraft.PlayerTracker.getData(player).setString("selectedSpellName", new SpellBlink().getUnlocName());
            } catch (MapNoContainsKeyException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            LogHelper.fatal("Something terribly has gone wrong with PlayerTracker!!!:");
            LogHelper.fatal(ex.getStackTrace());
            throw new RuntimeException(ex);
        }
    }

    public void updateToOther(String username, String id, Object obj) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            System.out.println("UPDATING TO SERVER");
            NetworkHandler.Network.sendToServer(new MessagePlayerTrackerUpdate(username, id, obj));
        } else {
            System.out.println("UPDATING TO " + username + "'s CLIENT!!!");
            NetworkHandler.Network.sendTo(new MessagePlayerTrackerUpdate(username, id, obj), (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(username));
        }
    }
}
