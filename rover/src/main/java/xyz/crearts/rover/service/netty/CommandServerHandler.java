package xyz.crearts.rover.service.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.crearts.dto.Command;
import xyz.crearts.dto.ModuleType;
import xyz.crearts.rover.service.RoboControllerService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandServerHandler extends SimpleChannelInboundHandler<Command> {
    private Map<ModuleType, RoboControllerService> modules = new HashMap<>();

    public void registerModule(ModuleType moduleType, RoboControllerService service) {
        modules.put(moduleType, service);
    }

    public static final CommandServerHandler INSTANCE = new CommandServerHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) throws Exception {
        RoboControllerService module = modules.get(command.module());

        if (module != null) {
            Method method = module.getClass().getMethod("execute", command.getClass());
            method.invoke(module, command);
        }
    }
}
