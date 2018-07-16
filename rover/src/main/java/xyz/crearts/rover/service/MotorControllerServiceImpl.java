package xyz.crearts.rover.service;

import com.pi4j.io.gpio.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import xyz.crearts.dto.MotorSide;
import xyz.crearts.dto.MovingDirection;

import javax.annotation.PreDestroy;

@Service
@Profile("pi")
public class MotorControllerServiceImpl implements MotorControllerService {
    private static int MAX_SPEED = 1024;
    @Data
    @Builder
    static public class Motor {
        GpioPinPwmOutput speed;
        GpioPinDigitalOutput forward;
        GpioPinDigitalOutput backward;
    }


    final private GpioController gpio;

    final private Motor leftWeal;
    final private Motor rightWeal;

    public MotorControllerServiceImpl() {
        gpio = GpioFactory.getInstance();

        leftWeal = Motor.builder()
                .speed(gpio.provisionPwmOutputPin(RaspiPin.GPIO_23, "SPEED", 0))
                .forward(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "FORWARD", PinState.LOW))
                .backward(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "BACKWARD", PinState.LOW))
                .build();


        rightWeal = Motor.builder()
                .speed(gpio.provisionPwmOutputPin(RaspiPin.GPIO_26, "SPEED", 0))
                .forward(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "FORWARD", PinState.LOW))
                .backward(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "BACKWARD", PinState.LOW))
                .build();

        leftWeal.speed.setShutdownOptions(true);
        leftWeal.forward.setShutdownOptions(true);
        leftWeal.backward.setShutdownOptions(true);

        rightWeal.speed.setShutdownOptions(true);
        rightWeal.forward.setShutdownOptions(true);
        rightWeal.backward.setShutdownOptions(true);
    }

    @Override
    public void move(MotorSide motor, MovingDirection direction, int speed) {
        Motor weel = null;
        switch (motor) {
            case MOTOR_LEFT:
                weel = leftWeal;
                break;
            case MOTOR_RIGHT:
                weel = rightWeal;
                break;
        }

        switch (direction) {
            case DIR_FORWARD:
                weel.forward.high();
                weel.backward.low();
                break;
            case DIR_BACKWARD:
                weel.forward.low();
                weel.backward.high();
                break;
        }

        weel.speed.setPwm(speed);
    }

    @PreDestroy
    public void preDestroy() {
        move(MotorSide.MOTOR_LEFT, MovingDirection.DIR_FORWARD, 0);
        move(MotorSide.MOTOR_RIGHT, MovingDirection.DIR_FORWARD, 0);

        gpio.shutdown();
    }
}
