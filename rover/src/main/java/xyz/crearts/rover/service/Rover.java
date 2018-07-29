package xyz.crearts.rover.service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.crearts.rover.component.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class Rover extends Component {
    @Value("${rover.mock:false}")
    boolean isMock;

    private Engine engine;

    private GpioController gpio;

    public Rover() {
        super("rover-v1", "rover");
    }

    @Override
    @PostConstruct
    public void create() {
        super.create();


        if (isMock) {
            engine = new EngineL298N(new MotorStub("left"), new MotorStub("right"));
        } else {
            gpio = GpioFactory.getInstance();

            MotorDC3V6V leftWeal = new MotorDC3V6V("left", gpio, RaspiPin.GPIO_23, RaspiPin.GPIO_24, RaspiPin.GPIO_25);
            MotorDC3V6V rightWeal = new MotorDC3V6V("right", gpio, RaspiPin.GPIO_26, RaspiPin.GPIO_27, RaspiPin.GPIO_28);


            engine = new EngineL298N(leftWeal, rightWeal);
            ((Component) engine).create();
        }
    }

    @Override
    @PreDestroy
    public void destroy() {
        super.destroy();

        if (gpio != null) {
            gpio.shutdown();
        }
    }

    public Engine getEngine() {
        return engine;
    }
}
