package journeymapadditions.events;

import journeymap.client.api.display.IThemeButton;
import journeymap.client.api.display.ModPopupMenu;
import journeymap.client.api.display.ThemeButtonDisplay;
import journeymap.client.api.event.forge.FullscreenDisplayEvent;
import journeymap.client.api.event.forge.PopupMenuEvent;
import journeymapadditions.JourneymapAdditions;
import journeymapadditions.integration.ClientProperties;
import journeymapadditions.integration.SlimeChunkOverlayHandler;
import journeymapadditions.network.dispatch.ClientNetworkDispatcher;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.ChunkPos;

public class EventHandler
{
    /**
     * Listen for Forge chunk load, show polygon overlay if it is a slime chunk.
     */
    public void onChunkLoadEvent(ChunkPos chunkPos)
    {
        try
        {
            ClientNetworkDispatcher.sendChunkInfoRequest(chunkPos);
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    public void onPopupEvent(ModPopupMenu menu)
    {
        try
        {
            menu.createSubItemList("Slime Chunks")
                    .addMenuItem("Slime IT", b -> SlimeChunkOverlayHandler.getInstance().disable());
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    public void onItemDisplayEvent(ThemeButtonDisplay themeButtonDisplay)
    {
        ClientProperties properties = JourneymapAdditions.getInstance().getClientProperties();
        try
        {

            themeButtonDisplay
                    .addThemeToggleButton(new TranslatableComponent("jm.additions.slime.button.on").getString(),
                            new TranslatableComponent("jm.additions.slime.button.off").getString(),
                            "slime",
                            properties.slimeChunksEnabled.get(),
                            b -> {
                                b.toggle();
                                properties.slimeChunksEnabled.set(b.getToggled());
                            });

        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    /**
     * Listen for Forge chunk unload, remove polygon overlay if it is a slime chunk.
     */
    public void onChunkUnloadEvent(ChunkPos chunkPos)
    {
        SlimeChunkOverlayHandler.getInstance().remove(chunkPos);
    }
}
