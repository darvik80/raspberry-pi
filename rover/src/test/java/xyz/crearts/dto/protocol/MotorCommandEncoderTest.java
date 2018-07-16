package xyz.crearts.dto.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import xyz.crearts.dto.MotorCommand;
import xyz.crearts.dto.MotorSide;
import xyz.crearts.dto.MovingDirection;

import static org.junit.Assert.assertEquals;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public class MotorCommandEncoderTest {

    @Test
    public void testEncode() {
        MotorCommand cmd = MotorCommand
                .builder()
                .motor(MotorSide.MOTOR_RIGHT)
                .direction(MovingDirection.DIR_FORWARD)
                .speed(1024)
                .build();

        MotorCommandEncoder encoder = new MotorCommandEncoder();

        ByteBuf buf = Unpooled.buffer();
        encoder.encode(null, cmd, buf);
        assertEquals(1, buf.getByte(0));
        assertEquals(0, buf.getByte(1));
        assertEquals(1024, buf.getInt(2));
        assertEquals(6, buf.writerIndex());
    }

}
