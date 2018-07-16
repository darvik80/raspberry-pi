package xyz.crearts.rover.service;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public class RoboControllerService {
    private String module;

    public RoboControllerService(String module) {
        this.module = module;
    }

    public String getModule() {
        return this.module;
    }
}
