package xyz.crearts.rover.service;

import lombok.Data;
import net.java.games.input.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.crearts.rover.event.EventRoverControl;
import xyz.crearts.rover.model.RoverControlState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class Processor implements Runnable {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public Processor() {
        new Thread(this).start();
    }


    @Override
    public void run() {
        try {

            System.out.println(Arrays.toString(ControllerEnvironment.getDefaultEnvironment().getControllers()));

            List<Controller> gamepads = Arrays.stream(ControllerEnvironment.getDefaultEnvironment().getControllers()).filter(controller ->
                    controller.getType().equals(Controller.Type.GAMEPAD)).collect(Collectors.toList());
            Controller gamepad = gamepads.get(0); // only working with one gamepad

            Event event;

            RoverControlState state = RoverControlState.builder().build();
            while (true) {
                gamepad.poll();

                EventQueue eq = gamepad.getEventQueue();
                event = new Event();

                boolean updated = false;
                while (eq.getNextEvent(event)) {
                    Component comp = event.getComponent();
                    switch (comp.getName()) {
                        case "z":
                            state.setLeftWeal((int) ((event.getValue() + 1) * 512));
                            updated = true;
                            break;
                        case "rz":
                            state.setRightWeal((int) ((event.getValue() + 1) * 512));
                            updated = true;
                            break;
                    }

                    /*
                    StringBuffer buffer = new StringBuffer(gamepad.getName());
                    buffer.append(" at ");
                    buffer.append(event.getNanos()).append(", ");
                    Component comp = event.getComponent();
                    buffer.append(comp.getName()).append(" changed to ");
                    float value = event.getValue();
                    if(comp.isAnalog()) {
                        buffer.append(value);
                    } else {
                        if(value==1.0f) {
                            buffer.append("On");
                        } else {
                            buffer.append("Off");
                        }
                    }
                    System.out.println(buffer.toString());
                    */
                }

                if (updated) {
                    System.out.println("left: " + state.getLeftWeal() + "\r\nright: " + state.getRightWeal() + "\r\n");
                    applicationEventPublisher.publishEvent(
                            new EventRoverControl(
                                    this,
                                    RoverControlState.builder()
                                            .leftWeal(state.getLeftWeal())
                                            .rightWeal(state.getRightWeal())
                                            .build()
                            )
                    );
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
