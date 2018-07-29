package xyz.crearts.rover.component;

public interface Motor {
    void moveForward(int speed);
    void moveBackward(int speed);
    void stop();
}
