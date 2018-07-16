package xyz.crearts.dto.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xyz.crearts.dto.MotorCommand;
import xyz.crearts.dto.MotorSide;
import xyz.crearts.dto.MovingDirection;

import java.util.List;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public class MotorCommandDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws IndexOutOfBoundsException {
        list.add(MotorCommand.builder()
                .motor(MotorSide.get(byteBuf.readByte()))
                .direction(MovingDirection.get(byteBuf.readByte()))
                .speed(byteBuf.readInt())
                .build()
        );
    }
}
