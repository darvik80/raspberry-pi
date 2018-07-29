package xyz.crearts.rover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.crearts.rover.service.Rover;

@Controller
public class IndexController {
    private Rover rover;

    public IndexController(Rover rover) {
        this.rover = rover;
    }

    @GetMapping("/")
    public String indexAction() {
        return "index";
    }

    @GetMapping("/move")
    public String moveAction() {
        rover.getEngine().moveForward(1024, 1024);

        return "index";
    }
    @GetMapping("/stop")
    public String stopAction() {
        rover.getEngine().moveForward(0, 0);

        return "index";
    }
}
