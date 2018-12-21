package cc.wangzijie.penetration.tcpserver.server.worker;

import cc.wangzijie.penetration.tcpserver.server.entity.ActiveClientTable;
import cc.wangzijie.penetration.tcpserver.server.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

/**
 * 类: 工作线程.
 *
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018-12-20
 */
public class Worker implements Runnable {

    /**
     * 活跃客户端列表.
     */
    private ActiveClientTable table;

    /**
     * 本线程所监听的Socket.
     */
    private Socket socket;

    /**
     * 本线程所监听的Socket输入流.
     */
    private BufferedReader input;

    /**
     * 本线程所监听的Socket输出流.
     */
    private PrintWriter output;

    /**
     * 构造方法: 创建线程, 设置本线程要监听的Socket, 获取该Socket的输入和输出流.
     *
     * @param table  活跃客户端列表
     * @param socket 本线程所监听的Socket
     * @throws IOException 异常说明
     */
    public Worker(ActiveClientTable table, Socket socket) throws IOException {
        this.table = table;
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());
    }

    /**
     * 线程执行的操作.
     */
    @Override
    public void run() {
        // 提取客户端的IP地址和端口号
        String clientIP   = socket.getInetAddress().getHostAddress();
        int    clientPort = socket.getPort();
        String threadName = Thread.currentThread().getName();
        LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ]线程启动: 处理来自[ {}:{} ]的客户端请求", threadName, clientIP, clientPort);

        // 将本线程所监听的客户端Socket添加到活跃客户端列表
        this.table.add(this.socket);
        LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]线程启动成功", threadName, clientIP, clientPort);

        // 开始通信
        try {
            // 返回活跃客户端列表
            LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]运行中: 开始输出活跃客户端列表", threadName, clientIP, clientPort);
            output.write("活跃客户端列表: " + this.table.getSocketList() + "\n");
            output.flush();
            LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]运行中: 活跃客户端列表输出完成", threadName, clientIP, clientPort);

            // 开始读取输入流
            String line;
            LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]运行中: 开始读取输入流", threadName, clientIP, clientPort);
            while ((line = input.readLine()) != null) {
                // 获取输入长度
                int length = line.length();
                LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]运行中: 从输入流读取到行: {}",
                             threadName,
                             clientIP,
                             clientPort,
                             line.replace("&nbsp", " "));

                // 反序列化
                //                JSONObject request = JSONObject.parseObject(line);
                //                JSONObject headers = request.getJSONObject("headers");

                //                // 根据读取行的内容执行操作
                //                if (line.startsWith("ConnectTo_")) {
                //                    String[] parts      = line.split("_");
                //                    String   targetIP   = parts[1];
                //                    String   targetPort = parts[2];
                //
                //                }

                // 返回响应
                String response = "accept: "+length;
                output.write(response.replace(" ", "&nbsp")+"\n");
                output.flush();
                LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]运行中: 返回响应: {}",
                             threadName,
                             clientIP,
                             clientPort,
                             response);
            }
        } catch (IOException e) {
            LogUtil.error("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]运行出错: 捕获到IO异常: {}",
                          threadName,
                          clientIP,
                          clientPort,
                          e.getMessage());
            e.printStackTrace();
        } finally {
            // 开始关闭
            LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]开始关闭", threadName, clientIP, clientPort);

            // 将本线程所监听的客户端Socket从活跃客户端列表移除
            this.table.remove(this.socket);

            // 尝试释放资源
            try {
                // 关闭输入流
                if (Objects.nonNull(this.input)) {
                    this.input.close();
                }

                // 关闭输出流
                if (Objects.nonNull(this.output)) {
                    this.output.close();
                }

                // 关闭Socket
                if (Objects.nonNull(this.socket)) {
                    this.socket.close();
                }
            } catch (IOException e) {
                LogUtil.error("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]正在关闭: 资源释放失败: 捕获到IO异常: {}",
                              threadName,
                              clientIP,
                              clientPort,
                              e.getMessage());
                e.printStackTrace();
            } finally {
                LogUtil.info("[ TCP内网穿透(P2P)服务 ][ {} ][ 客户端 {}:{} ]已关闭", threadName, clientIP, clientPort);
            }
        }

    }
}
