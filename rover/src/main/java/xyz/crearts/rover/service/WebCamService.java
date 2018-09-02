package xyz.crearts.rover.service;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Profile("webcam")
public class WebCamService {

    @PostConstruct
    public void postConstruct() throws IOException {
        List<Webcam> webcams = Webcam.getWebcams();
        webcams.forEach(webcam -> {
            try {
                Dimension[] dimensions = webcam.getViewSizes();
                try {
                    webcam.setViewSize(WebcamResolution.HD.getSize());
                } catch (Exception ex) {
                    webcam.setViewSize(dimensions[dimensions.length - 1]);
                }
                webcam.open();
                ImageIO.write(webcam.getImage(), "PNG", new File(webcam.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                webcam.close();
            }
        });
    }
}
