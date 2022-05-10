package journeymapadditions.network;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.data.NetworkHandler;
import journeymapadditions.network.data.PacketContainer;
import journeymapadditions.network.data.PacketContext;
import journeymapadditions.network.data.Side;
import journeymapadditions.network.packets.PacketManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class FabricNetworkHandler extends PacketManager implements NetworkHandler
{
    private final Map<Class<?>, Message<?>> MESSAGE_MAP = new HashMap<>();

    public FabricNetworkHandler(Side side)
    {
        super();
        JourneymapAdditions.getLogger().info("Registering Network for side: {}", side);
        PACKET_MAP.forEach((clazz, packetContainer) -> registerPacket(packetContainer, side));
    }

    private <T> void registerPacket(PacketContainer<T> container, Side side)
    {
        MESSAGE_MAP.put(container.messageType(), new Message<>(container.packetIdentifier(), container.encoder()));
        if (Side.CLIENT.equals(side))
        {
            ClientPlayNetworking.registerGlobalReceiver(container.packetIdentifier(), ((client, listener, buf, responseSender) -> {
                buf.readByte(); // handle forge discriminator
                T message = container.decoder().apply(buf);
                client.execute(() -> container.handler().accept(new PacketContext<>(message, side)));
            }));
        }
        else
        {
            ServerPlayNetworking.registerGlobalReceiver(container.packetIdentifier(), ((server, player, listener, buf, responseSender) -> {
                buf.readByte(); // handle forge discriminator
                T message = container.decoder().apply(buf);
                server.execute(() -> container.handler().accept(new PacketContext<>(player, message, side)));
            }));
        }
    }

    public <T> void sendToServer(T packet)
    {
        Message<T> message = (Message<T>) MESSAGE_MAP.get(packet.getClass());
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte(0); // handle forge discriminator
        message.encoder().accept(packet, buf);
        ClientPlayNetworking.send(message.id(), buf);
    }

    public <T> void sendToClient(T packet, ServerPlayer player)
    {
        Message<T> message = (Message<T>) MESSAGE_MAP.get(packet.getClass());
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte(0); // handle forge discriminator
        message.encoder().accept(packet, buf);
        ServerPlayNetworking.send(player, message.id(), buf);
    }

    public record Message<T>(ResourceLocation id, BiConsumer<T, FriendlyByteBuf> encoder)
    {
    }
}