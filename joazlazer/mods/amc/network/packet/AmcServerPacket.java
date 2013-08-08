package joazlazer.mods.amc.network.packet;

import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;

public abstract class AmcServerPacket {
	public abstract void handle(DataInputStream iStream, EntityPlayer player);
}
