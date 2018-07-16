package xyz.crearts.rover;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = RoverApplication.class)
public class RoverApplicationTests {

    class TestRunnable implements Runnable {
        private String name;
        public TestRunnable(String name) {
            this.name = name;
            System.out.println("construct");
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            System.out.println(Thread.currentThread().getName() + " +start");
            try {
                for (int count = 0; count < 4; ++count) {
                    System.out.println(Thread.currentThread().getName() + " " + count);
                    Thread.sleep(500);
                }
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            System.out.println(Thread.currentThread().getName() + " +stop");
        }
    };


    @Test
    public void contextLoads() {
    }

    @Test
    public void testCommandExecution() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);


        Future<?> first = service.submit(new TestRunnable("first"));
        Future<?> second = service.submit(new TestRunnable("second"));

        Thread.sleep(3000);
        second.cancel(true);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

}
