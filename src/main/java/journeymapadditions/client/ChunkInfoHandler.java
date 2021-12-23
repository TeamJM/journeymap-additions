package journeymapadditions.client;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.client.integration.SlimeChunkOverlayHandler;
import journeymapadditions.network.packet.ChunkInfoPacket;

public class ChunkInfoHandler
{

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
