package cc.wangzijie.penetration.tcpserver.server;

import cc.wangzijie.penetration.tcpserver.server.entity.ActiveClientTable;
import cc.wangzijie.penetration.tcpserver.server.util.LogUtil;
import cc.wangzijie.penetration.tcpserver.server.worker.Worker;
import cc.wangzijie.penetration.tcpserver.server.worker.WorkerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类: 服务启动器.
 *
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018-12-21
 */
public class ServerStarter {

    /* ---- [ Attribute ] ---- */

    /**
     * 默认监听端口号.
     */
    private static final int DEFAULT_PORT = 13614;

    /**
     * 监听端口号.
     */
    private int port;

    /* ---- [ Constructor ] ---- */

    /**
     * 构造方法: 创建并初始化ServerStarter.
     */
    public ServerStarter() {
        this.port = DEFAULT_PORT;
    }

    /**
     * 构造方法: 创建并初始化ServerStarter.
     */
    public ServerStarter(int port) {
        this.port = port;
    }

    /* ---- [ Custom Method ] ---- */

    public void Start() {
        LogUtil.info("[ TCP内网穿透(P2P)服务 ]启动服务: 监听端口[ {} ]", this.port);
        try {
            // 创建线程池
            LogUtil.info("[ TCP内网穿透(P2P)服务 ]启动中: 开始创建线程池");
            ThreadPoolExecutor threadPool;
            threadPool = new ThreadPoolExecutor(2,
                                                4,
                                                30,
                                                TimeUnit.SECONDS,
                                                new ArrayBlockingQueue<>(8),
                                                new WorkerFactory());
            LogUtil.info("[ TCP内网穿透(P2P)服务 ]启动中: 线程池创建成功");

            // 创建ServerSocket, 绑定并监听端口: port
            LogUtil.info("[ TCP内网穿透(P2P)服务 ]启动中: 开始绑定并监听端口[ {} ]", this.port);
            ServerSocket serverSocket = new ServerSocket(this.port);
            LogUtil.info("[ TCP内网穿透(P2P)服务 ]启动中: 绑定并监听端口[ {} ]成功", this.port);
            LogUtil.info("[ TCP内网穿透(P2P)服务 ]启动成功");

            // 声明活跃客户端列表
            ActiveClientTable table = new ActiveClientTable();

            // 持续监听端口, 等待客户端连接.
            while (true) {
                try {
                    LogUtil.info("[ TCP内网穿透(P2P)服务 ]运行中: 正在监听端口[ {} ]", this.port);
                    // accept方法阻塞, 直到有客户端连接到服务器.
                    Socket socket = serverSocket.accept();
                    // 提取客户端的IP地址和端口号
                    String clientIP   = socket.getInetAddress().getHostAddress();
                    int    clientPort = socket.getPort();
                    LogUtil.info("[ TCP内网穿透(P2P)服务 ]运行中: 接收到来自[ {}:{} ]的客户端请求", clientIP, clientPort);
                    // 将新任务添加到线程池的任务队列
                    threadPool.execute(new Worker(table, socket));
                    LogUtil.info("[ TCP内网穿透(P2P)服务 ]运行中: 已将来自[ {}:{} ]的客户端请求添加到线程池任务队列", clientIP, clientPort);
                } catch (IOException e) {
                    LogUtil.error("[ TCP内网穿透(P2P)服务 ]运行出错: 捕获到IO异常: {}", e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            LogUtil.error("[ TCP内网穿透(P2P)服务 ]启动失败: 捕获到IO异常: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            LogUtil.info("[ TCP内网穿透(P2P)服务 ]已结束");
        }
    }

    /* ---- [ Getter && Setter ] ---- */

}
