package journeymapadditions.network.data;

import net.minecraft.server.level.ServerPlayer;

public interface NetworkHandler
{
    <T> void sendToServer(T packet);

    <T> void sendToClient(T packet, ServerPlayer player);
}
