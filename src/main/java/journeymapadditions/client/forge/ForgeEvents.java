package journeymapadditions.client.forge;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.client.integration.SlimeChunkOverlayHandler;
import journeymapadditions.network.dispatch.ClientNetworkDispatcher;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEvents
{
    /**
     * Listen for Forge chunk load, show polygon overlay if it is a slime chunk.
     */
    @SubscribeEvent
    public void onChunkLoadEvent(ChunkEvent.Load event)
    {
        try
        {
            if (event.getWorld() != null)
            {
                ChunkPos chunkPos = event.getChunk().getPos();
                ClientNetworkDispatcher.sendChunkInfoRequest(chunkPos);
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    /**
     * Listen for Forge chunk unload, remove polygon overlay if it is a slime chunk.
     */
    @SubscribeEvent
    public void onChunkUnloadEvent(ChunkEvent.Unload event)
    {
        SlimeChunkOverlayHandler.getInstance().remove(event.getChunk().getPos());
    }
}
