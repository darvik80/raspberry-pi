package xyz.crearts.rover.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class Component {
    private String type;
    private String id;

    public void create() {
        log.info("{}:{} create", getType(), getId());
    }

    public void destroy() {
        log.info("{}:{} destroy", getType(), getId());
    }
}
