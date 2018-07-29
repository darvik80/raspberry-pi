package xyz.crearts.rover.component;

public interface Engine {
    void moveForward(int leftSpeed, int rightSpeed);
    void moveBackward(int leftSpeed, int rightSpeed);
    void stop();
}
