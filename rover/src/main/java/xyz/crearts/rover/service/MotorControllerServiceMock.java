package xyz.crearts.rover.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import xyz.crearts.dto.MotorCommand;
import xyz.crearts.dto.MotorSide;
import xyz.crearts.dto.MovingDirection;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

@Service
@Profile({ "dev", "test"} )
@Slf4j
public class MotorControllerServiceMock extends RoboControllerService implements MotorControllerService {
    public MotorControllerServiceMock() {
        super("motor");

        log.warn("Start motor");
    }

    @Override
    public void move(MotorSide motor, MovingDirection direction, int speed) {
        log.info("Move {}, {}, {}", motor, direction, speed);
    }

    public void execute(MotorCommand command) {
        log.info("Invoke execute: {}", command);
        move(command.getMotor(), command.getDirection(), command.getSpeed());
    }
}
