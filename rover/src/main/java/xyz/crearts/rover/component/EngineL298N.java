package xyz.crearts.rover.component;

import lombok.Builder;

public class EngineL298N extends Component implements Engine {
    private Motor leftWeal;
    private Motor rightWeal;

    public EngineL298N(Motor leftWeal, Motor rightWeal) {
        this("main", leftWeal, rightWeal);
    }

    public EngineL298N(String id, Motor leftWeal, Motor rightWeal) {
        super("engine-L298N", id);

        if (leftWeal instanceof Component) {
            ((Component) leftWeal).create();
        }

        if (rightWeal instanceof Component) {
            ((Component) rightWeal).create();
        }

        this.leftWeal = leftWeal;
        this.rightWeal = rightWeal;
    }

    public void moveForward(int leftSpeed, int rightSpeed) {
        this.leftWeal.moveForward(leftSpeed);
        this.rightWeal.moveForward(rightSpeed);
    }

    public void moveBackward(int leftSpeed, int rightSpeed) {
        this.leftWeal.moveBackward(leftSpeed);
        this.rightWeal.moveBackward(rightSpeed);
    }

    public void stop() {
        this.leftWeal.moveForward(0);
        this.rightWeal.moveForward(0);
    }

    public Motor getLeftWeal() {
        return leftWeal;
    }

    public void setLeftWeal(Motor leftWeal) {
        this.leftWeal = leftWeal;
    }

    public Motor getRightWeal() {
        return rightWeal;
    }

    public void setRightWeal(Motor rightWeal) {
        this.rightWeal = rightWeal;
    }
}
