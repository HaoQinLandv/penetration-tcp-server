package cc.wangzijie.penetration.tcpserver.util;

/**
 * 类: 日志级别枚举.
 *
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018 -12-20
 */
public enum LogLevel {

    /* ---- [ Enumeration ] ---- */

    /**
     * Debug log level.
     */
    DEBUG(1, "[ DEBUG ]"),
    /**
     * Info log level.
     */
    INFO(1, "[ INFO ]"),
    /**
     * Error log level.
     */
    ERROR(0, "[ ERROR ]");

    /* ---- [ Attribute ] ---- */

    private int level;
    private String label;

    /* ---- [ Constructor ] ---- */

    LogLevel(int level, String label) {
        this.level = level;
        this.label = label;
    }

    /* ---- [ Custom Method ] ---- */



    /* ---- [ Getter && Setter ] ---- */

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
