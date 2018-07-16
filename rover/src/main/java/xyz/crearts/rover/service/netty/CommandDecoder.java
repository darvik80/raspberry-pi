package xyz.crearts.rover.service.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.util.ObjectUtils;
import xyz.crearts.dto.*;
import xyz.crearts.dto.protocol.MotorCommandDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandDecoder extends ByteToMessageDecoder {
    private Map<ModuleType, ByteToMessageDecoder> decoders = new HashMap<>();

    public void registerDecoder(ModuleType module, ByteToMessageDecoder decoder) {
        decoders.put(module, decoder);
    }

    public CommandDecoder() {
        registerDecoder(ModuleType.MOTOR, new MotorCommandDecoder());
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws IndexOutOfBoundsException {
        final int readerIndex = byteBuf.readerIndex();
        if (byteBuf.writerIndex() == readerIndex) {
            return;
        }

        ChannelPipeline p = channelHandlerContext.pipeline();
        final ModuleType groupId = ModuleType.get(byteBuf.getByte(readerIndex));
        ByteToMessageDecoder decoder = decoders.get(groupId);

        if (!ObjectUtils.isEmpty(decoder)) {
            p.addAfter(channelHandlerContext.name(), null, new CommandEncoder());
            p.addAfter(channelHandlerContext.name(), null, decoder);
        } else {
            byteBuf.skipBytes(byteBuf.readableBytes());
            channelHandlerContext.close();
        }

        p.remove(this);
    }
}
