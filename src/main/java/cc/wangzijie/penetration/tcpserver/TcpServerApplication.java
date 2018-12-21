package cc.wangzijie.penetration.tcpserver;

import cc.wangzijie.penetration.tcpserver.server.ServerStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类: 服务器主类.
 *
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018-12-20
 */
@SpringBootApplication
public class TcpServerApplication {

    /**
     * SpringBoot启动入口.
     *
     * @param args 命令行参数.
     */
    public static void main(String[] args) {
        // 启动SpringBoot
        SpringApplication.run(TcpServerApplication.class, args);

        // 启动服务, 设置监听端口
        new ServerStarter().Start();
    }

}

