package journeymapadditions.events;

import journeymap.client.api.event.forge.FullscreenDisplayEvent;
import journeymap.client.api.event.forge.PopupMenuEvent;
import journeymapadditions.JourneymapAdditions;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEvents
{
    private final EventHandler handler;

    public ForgeEvents()
    {
        this.handler = new EventHandler();
    }

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
                handler.onChunkLoadEvent(chunkPos);
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    @SubscribeEvent
    public void onPopupEvent(PopupMenuEvent.FullscreenPopupMenuEvent event)
    {
        try
        {
            if (!event.isCanceled())
            {
                handler.onPopupEvent(event.getPopupMenu());
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    @SubscribeEvent
    public void onItemDisplayEvent(FullscreenDisplayEvent.AddonButtonDisplayEvent event)
    {
        handler.onItemDisplayEvent(event.getThemeButtonDisplay());
    }

    /**
     * Listen for Forge chunk unload, remove polygon overlay if it is a slime chunk.
     */
    @SubscribeEvent
    public void onChunkUnloadEvent(ChunkEvent.Unload event)
    {
        handler.onChunkUnloadEvent(event.getChunk().getPos());
    }
}
