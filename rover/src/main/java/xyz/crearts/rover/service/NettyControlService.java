package xyz.crearts.rover.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.crearts.rover.service.netty.CommandServerInitializer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class NettyControlService extends Thread {
    @Value("${netty.port:9001}")
    private int port;


    @PostConstruct
    public void postConstruct() {
        this.start();
    }

    @PreDestroy
    public void preDestroy() throws InterruptedException {
        this.interrupt();
        this.join();
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new CommandServerInitializer());
            b.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }}
