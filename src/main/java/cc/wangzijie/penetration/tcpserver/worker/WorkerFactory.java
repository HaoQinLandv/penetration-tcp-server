package cc.wangzijie.penetration.tcpserver.worker;

import cc.wangzijie.penetration.tcpserver.util.LogUtil;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类: 工作线程工厂.
 *
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018-12-20
 */
public class WorkerFactory implements ThreadFactory {

    /* ---- [ Attribute ] ---- */

    /**
     * 计数器.
     */
    private final AtomicInteger counter;

    /* ---- [ Constructor ] ---- */

    /**
     * 构造方法: 创建工作线程工厂.
     */
    public WorkerFactory() {
        this.counter = new AtomicInteger(1);
    }

    /* ---- [ Custom Method ] ---- */

    /**
     * 创建线程.
     *
     * @param r 创建线程参数.
     * @return 创建的线程.
     */
    @Override
    public Thread newThread(Runnable r) {
        String threadName = this.counter.getAndIncrement() + "号工作线程";
        Thread thread = new Thread(r, threadName);
        LogUtil.info("[ TCP内网穿透(P2P)服务 ]创建线程[ {} ]: {}", thread.getId(), thread.getName());
        return thread;
    }

    /* ---- [ Getter && Setter ] ---- */
}
