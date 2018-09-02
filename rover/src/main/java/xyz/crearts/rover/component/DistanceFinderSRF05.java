package xyz.crearts.rover.component;

import com.pi4j.io.gpio.*;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class DistanceFinderSRF05 extends Component implements Runnable {
    private static final int SCAN_DELAY = 1000;
    private final static int TIMEOUT = 21000;
    private final static float SOUND_SPEED = 340.29f;

    private long distance;

    public synchronized long getDistance() {
        return distance;
    }

    public synchronized void setDistance(long distance) {
        this.distance = distance;
    }

    @Data
    @Builder
    public static class Configuration  {
        private GpioController gpio;
        private Pin trig;
        private Pin echo;
        private Consumer<Long> callback;
    }

    private GpioPinDigitalOutput trig;
    private GpioPinDigitalInput echo;
    private Consumer<Long> callback;

    private Thread ctrlThread;

    public DistanceFinderSRF05(String id, Configuration config) {
        super("srf05", id);

        this.trig = config.gpio.provisionDigitalOutputPin(config.trig, "TRIG", PinState.LOW);
        this.echo = config.gpio.provisionDigitalInputPin(config.echo, "ECHO", PinPullResistance.PULL_DOWN);
        this.callback = config.callback;
    }

    public void create() {
        super.create();

        ctrlThread = new Thread(this);
        ctrlThread.start();
    }

    public void destroy() {
        super.destroy();

        ctrlThread.interrupt();
        try {
            ctrlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                //meter.reset();
                this.trig.high();
                Thread.sleep(0, 10000);
                this.trig.low();

                int countdown = TIMEOUT;
                while(this.echo.isLow() && --countdown > 0){ //Wait until the ECHO pin gets HIGH

                }
                if (countdown == 0 || countdown == TIMEOUT) {
                    continue;
                }

                countdown = TIMEOUT;
                long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                while(this.echo.isHigh() && --countdown > 0){ //Wait until the ECHO pin gets LOW

                }
                if (countdown == 0 || countdown == TIMEOUT) {
                    continue;
                }

                long endTime= System.nanoTime();

                long distance =  (long)((double) (endTime - startTime) / 2 / 10000000 * SOUND_SPEED);
                if (distance == getDistance()) {
                    continue;
                }
                setDistance(distance);
                if (this.callback != null) {
                    this.callback.accept(distance);
                }

                Thread.sleep(SCAN_DELAY);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
