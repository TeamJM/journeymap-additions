package journeymapadditions.network.packets;

import journeymapadditions.network.data.PacketContainer;
import journeymapadditions.network.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class PacketManager
{
    protected final Map<Class<?>, PacketContainer<?>> PACKET_MAP = new HashMap<>();

    public PacketManager()
    {
        setupPacket(ChunkInfoPacket.CHANNEL, ChunkInfoPacket.class, ChunkInfoPacket::encode, ChunkInfoPacket::decode, ChunkInfoPacket::handle);
    }

    private <T> void setupPacket(ResourceLocation packetIdentifier, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler)
    {
        PacketContainer<T> container = new PacketContainer<>(packetIdentifier, messageType, encoder, decoder, handler);
        PACKET_MAP.put(messageType, container);
    }
}
