package journeymapadditions.integration;

import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.event.ClientEvent;
import journeymapadditions.JourneymapAdditions;

import java.util.EnumSet;

import static journeymap.client.api.event.ClientEvent.Type.MAPPING_STOPPED;
import static journeymap.client.api.event.ClientEvent.Type.REGISTRY;

@ClientPlugin
public class JourneymapPlugin implements IClientPlugin
{
    private IClientAPI jmApi;

    @Override
    public void initialize(IClientAPI jmApi)
    {
        this.jmApi = jmApi;
        this.jmApi.subscribe(getModId(), EnumSet.of(MAPPING_STOPPED, REGISTRY));
        SlimeChunkOverlayHandler.init(jmApi);
    }

    @Override
    public String getModId()
    {
        return JourneymapAdditions.MOD_ID;
    }

    @Override
    public void onEvent(ClientEvent clientEvent)
    {
        try
        {
            switch (clientEvent.type)
            {
                case MAPPING_STOPPED:
                    this.jmApi.removeAll(JourneymapAdditions.MOD_ID);
                    break;
                case REGISTRY:
                    JourneymapAdditions.getLogger().info("Initializing client configs");
                    JourneymapAdditions.getInstance().setClientProperties(new ClientProperties());
                    break;
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }
}
