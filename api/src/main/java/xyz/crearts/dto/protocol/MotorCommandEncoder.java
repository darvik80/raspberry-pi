package xyz.crearts.dto.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xyz.crearts.dto.MotorCommand;

public class MotorCommandEncoder extends MessageToByteEncoder<MotorCommand> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MotorCommand command, ByteBuf byteBuf) throws IndexOutOfBoundsException {
        byteBuf.writeByte(command.getMotor().ordinal());
        byteBuf.writeByte(command.getDirection().ordinal());
        byteBuf.writeInt(command.getSpeed());
    }
}
