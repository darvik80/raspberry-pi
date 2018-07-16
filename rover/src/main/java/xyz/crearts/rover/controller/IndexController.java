package xyz.crearts.rover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.crearts.dto.MotorSide;
import xyz.crearts.dto.MovingDirection;
import xyz.crearts.rover.service.MotorControllerService;
import xyz.crearts.rover.service.MotorControllerServiceImpl;

@Controller
public class IndexController {
    private MotorControllerService motor;

    public IndexController(MotorControllerService motor) {
        this.motor = motor;
    }

    @GetMapping("/")
    public String indexAction() {
        return "index";
    }

    @GetMapping("/left-move")
    public String moveLeftAction() {
        motor.move(MotorSide.MOTOR_LEFT, MovingDirection.DIR_FORWARD, 1024);

        return "index";
    }
    @GetMapping("/left-stop")
    public String stopLeftAction() {
        motor.move(MotorSide.MOTOR_LEFT, MovingDirection.DIR_FORWARD, 0);

        return "index";
    }

    @GetMapping("/right-move")
    public String moveRightAction() {
        motor.move(MotorSide.MOTOR_RIGHT, MovingDirection.DIR_FORWARD, 1024);

        return "index";
    }
    @GetMapping("/right-stop")
    public String stopRightAction() {
        motor.move(MotorSide.MOTOR_RIGHT, MovingDirection.DIR_FORWARD, 0);

        return "index";
    }

    @GetMapping("/move")
    public String moveAction() {
        motor.move(MotorSide.MOTOR_RIGHT, MovingDirection.DIR_FORWARD, 1024);
        motor.move(MotorSide.MOTOR_LEFT, MovingDirection.DIR_FORWARD, 1024);

        return "index";
    }
    @GetMapping("/stop")
    public String stopAction() {
        motor.move(MotorSide.MOTOR_RIGHT, MovingDirection.DIR_FORWARD, 0);
        motor.move(MotorSide.MOTOR_LEFT, MovingDirection.DIR_FORWARD, 0);

        return "index";
    }
}
