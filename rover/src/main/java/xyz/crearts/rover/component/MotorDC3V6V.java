package xyz.crearts.rover.component;

import com.pi4j.io.gpio.*;

public class MotorDC3V6V extends AbstractMotor {
    private GpioPinPwmOutput speed;
    private GpioPinDigitalOutput forward;
    private GpioPinDigitalOutput backward;

    public MotorDC3V6V(String id, final GpioController gpio, Pin pinSpeed, Pin pinForward, Pin pinBackward) {
        super("motor-DC3V-6V", id);

        speed = gpio.provisionPwmOutputPin(pinSpeed, "SPEED", 0);
        forward = gpio.provisionDigitalOutputPin(pinForward, "FORWARD", PinState.LOW);
        backward = gpio.provisionDigitalOutputPin(pinBackward, "BACKWARD", PinState.LOW);

    }

    @Override
    public void create() {
        speed.setShutdownOptions(true);
        forward.setShutdownOptions(true);
        backward.setShutdownOptions(true);

        super.create();
    }

    @Override
    public void destroy() {
        moveForward(0);

        super.destroy();
    }


    @Override
    public void moveForward(int speed) {
        this.speed.setPwm(speed);
        this.forward.high();
        this.backward.low();

        super.moveForward(speed);
    }

    @Override
    public void moveBackward(int speed) {
        this.speed.setPwm(speed);
        this.forward.low();
        this.backward.high();

        super.moveBackward(speed);
    }

    @Override
    public void stop() {
        moveForward(0);

        super.stop();
    }
}
