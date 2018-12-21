package cc.wangzijie.penetration.tcpserver.server.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 类: 日志工具类.
 *
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018-12-20
 */
public class LogUtil {

    /**
     * 线程安全的日期时间格式化工具类.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 获取当前时间.
     * @return 格式化的当前时间.
     */
    private static String getNow(){
        return "[ " + FORMATTER.format(LocalDateTime.now()) + " ]";
    }

    /**
     * 处理参数.
     *
     * @param log 日志语句.
     * @param params 填充参数.
     * @return 处理后的日志语句.
     */
    private static String process(String log, Object ... params){
        // 处理参数.
        for (Object param : params) {
            if (log.contains("{}")) {
                log = log.replaceFirst("\\{}", param.toString());
            } else {
                break;
            }
        }

        return log;
    }

    /**
     * 打印 Info 级别的日志.
     *
     * @param log 日志语句.
     * @param params 填充参数.
     */
    public static void info(String log, Object ... params) {
        System.out.println(getNow() + LogLevel.INFO.getLabel() + process(log, params));
    }

    /**
     * 打印 Debug 级别的日志.
     *
     * @param log 日志语句.
     * @param params 填充参数.
     */
    public static void debug(String log, Object ... params) {
        System.out.println(getNow() + LogLevel.DEBUG.getLabel() + process(log, params));
    }

    /**
     * 打印 Error 级别的日志.
     *
     * @param log 日志语句.
     * @param params 填充参数.
     */
    public static void error(String log, Object ... params) {
        System.out.println(getNow() + LogLevel.ERROR.getLabel() + process(log, params));
    }

}
