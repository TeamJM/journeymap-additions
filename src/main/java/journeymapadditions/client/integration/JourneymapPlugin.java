package journeymapadditions.client.integration;

import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.event.ClientEvent;
import journeymapadditions.JourneymapAdditions;

@ClientPlugin
public class JourneymapPlugin implements IClientPlugin
{
    private IClientAPI jmApi;

    @Override
    public void initialize(IClientAPI jmApi)
    {
        this.jmApi = jmApi;
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

            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }
}
