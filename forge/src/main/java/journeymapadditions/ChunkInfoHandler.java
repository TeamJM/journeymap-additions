package journeymapadditions;

import journeymapadditions.integration.SlimeChunkOverlayHandler;
import journeymapadditions.network.dispatch.ClientNetworkDispatcher;
import journeymapadditions.network.packet.ChunkInfoPacket;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.Map;

public class ChunkInfoHandler
{
    private static Map<ChunkPos, Boolean> chunkInfoCache = new HashMap<>();

    public static void chunkLoad(ChunkPos chunkPos)
    {
        if (chunkInfoCache.get(chunkPos) == null)
        {
            ClientNetworkDispatcher.sendChunkInfoRequest(chunkPos);
        }
    }

    public static void handle(ChunkInfoPacket packet)
    {
        try
        {
            // handle slimechunks
            if (packet.isSlimeChunk())
            {
                SlimeChunkOverlayHandler.getInstance().addChunk(packet.getChunkPos());
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }
}
