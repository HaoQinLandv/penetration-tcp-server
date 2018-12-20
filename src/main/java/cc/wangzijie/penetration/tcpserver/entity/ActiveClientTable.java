package cc.wangzijie.penetration.tcpserver.entity;

import cc.wangzijie.penetration.tcpserver.util.LogUtil;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类: 活跃客户端列表.
 *
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018 -12-20
 */
public class ActiveClientTable {

    /* ---- [ Attribute ] ---- */

    /**
     * 活跃客户端Socket列表.
     */
    private List<Socket> socketList;

    /**
     * 活跃客户端计数器.
     */
    private AtomicInteger counter;

    /* ---- [ Constructor ] ---- */

    /**
     * 构造方法: 创建并初始化活跃客户端列表.
     */
    public ActiveClientTable() {
        this.socketList = Collections.synchronizedList(new ArrayList<>());
        this.counter = new AtomicInteger(0);
    }

    /* ---- [ Custom Method ] ---- */

    /**
     * 同步方法: 向列表中添加客户端Socket.
     *
     * @param socket 目标客户端Socket.
     */
    public synchronized void add(Socket socket) {
        if (!this.socketList.contains(socket)) {
            this.socketList.add(socket);
            LogUtil.info("[ TCP内网穿透(P2P)服务 ][ 活跃客户端列表 ]已将目标Socket[ {} ]添加到列表中, 目前活跃客户端数量为: {}", socket, this.counter.incrementAndGet());
        } else {
            LogUtil.error("[ TCP内网穿透(P2P)服务 ][ 活跃客户端列表 ]列表中已持有目标Socket[ {} ]", socket);
        }
    }

    /**
     * 同步方法: 从列表中移除客户端Socket.
     *
     * @param socket 目标客户端Socket.
     */
    public synchronized void remove(Socket socket) {
        if (this.socketList.contains(socket)) {
            this.socketList.remove(socket);
            LogUtil.info("[ TCP内网穿透(P2P)服务 ][ 活跃客户端列表 ]已将目标Socket[ {} ]从列表中移除, 目前活跃客户端数量为: {}", socket, this.counter.decrementAndGet());
        } else {
            LogUtil.error("[ TCP内网穿透(P2P)服务 ][ 活跃客户端列表 ]列表中未持有目标Socket[ {} ]", socket);
        }
    }

    /* ---- [ Getter && Setter ] ---- */

    /**
     * Gets socket list.
     *
     * @return the socket list
     */
    public List<Socket> getSocketList() {
        return socketList;
    }

    /**
     * Sets socket list.
     *
     * @param socketList the socket list
     */
    public void setSocketList(List<Socket> socketList) {
        this.socketList = socketList;
    }

    /**
     * Gets counter.
     *
     * @return the counter
     */
    public AtomicInteger getCounter() {
        return counter;
    }

    /**
     * Sets counter.
     *
     * @param counter the counter
     */
    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }
}
