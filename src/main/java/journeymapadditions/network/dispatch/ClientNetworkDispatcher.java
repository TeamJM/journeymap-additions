package journeymapadditions.network.dispatch;

import journeymapadditions.network.PacketRegistry;
import journeymapadditions.network.packet.ChunkInfoPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.ChunkPos;

public class ClientNetworkDispatcher
{
    public static void sendChunkInfoRequest(ChunkPos chunkPos)
    {
        // might be null if player is not fully logged in.
        if (Minecraft.getInstance().getConnection() != null)
        {
            PacketRegistry.REGISTRY.sendToServer(new ChunkInfoPacket(true, chunkPos));
        }
    }
}
