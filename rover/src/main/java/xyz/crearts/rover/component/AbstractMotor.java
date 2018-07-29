package xyz.crearts.rover.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractMotor extends Component implements Motor {
    public AbstractMotor(String type, String id) {
        super(type, id);
    }

    @Override
    public void moveForward(int speed) {
        log.info("%s:%s moveForward, speed: %d", getType(), getId(), speed);
    }

    @Override
    public void moveBackward(int speed) {
        log.info("%s:%s moveBackward, speed: %d", getType(), getId(), speed);
    }

    @Override
    public void stop() {
        log.info("%s:%s stop");
    }
}
