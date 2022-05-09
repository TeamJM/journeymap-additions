package journeymapadditions.network.dispatch;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.packets.ChunkInfoPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;

public class ClientNetworkDispatcher
{
    public static void sendChunkInfoRequest(ChunkPos chunkPos)
    {
        // might be null if player is not fully logged in.
        if (Minecraft.getInstance().getConnection() != null)
        {
            JourneymapAdditions.getInstance().getNetworkHandler().sendToServer(new ChunkInfoPacket(true, chunkPos));
        }
    }
}
