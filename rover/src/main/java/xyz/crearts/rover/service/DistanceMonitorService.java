package xyz.crearts.rover.service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.crearts.rover.component.DistanceFinderSRF05;
import xyz.crearts.rover.event.EventDistanceChanged;

import java.util.function.Consumer;

@Service
public class DistanceMonitorService implements Consumer<Long> {
    private DistanceFinderSRF05 distanceFinder;
    private ApplicationEventPublisher applicationEventPublisher;

    public DistanceMonitorService(GpioController gpio, ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        distanceFinder = new DistanceFinderSRF05(
                "distance-finder",
                DistanceFinderSRF05.Configuration
                        .builder()
                        .gpio(gpio)
                        .trig(RaspiPin.GPIO_01)
                        .echo(RaspiPin.GPIO_04)
                        .callback(this)
                        .build()
        );

        distanceFinder.create();
    }

    @Override
    public void accept(Long distance) {
        applicationEventPublisher.publishEvent(new EventDistanceChanged(this, distance));
    }
}
