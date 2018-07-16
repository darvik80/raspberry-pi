package xyz.crearts.rover.service.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.util.StringUtils;
import xyz.crearts.dto.Response;

public class CommandEncoder extends MessageToByteEncoder<Response> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response command, ByteBuf byteBuf) throws IndexOutOfBoundsException {
        byteBuf.writeShort(command.getCode());
        if (StringUtils.isEmpty(command.getMessage())) {
            byteBuf.writeInt(0);
        } else {
            byteBuf.writeInt(command.getMessage().getBytes().length);
            byteBuf.writeBytes(command.getMessage().getBytes());
        }
    }
}
