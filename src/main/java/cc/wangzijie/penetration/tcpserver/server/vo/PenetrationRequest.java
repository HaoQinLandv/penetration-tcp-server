package cc.wangzijie.penetration.tcpserver.server.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 类: .
 *
 * @param <T> the type parameter
 * @author <a href="mailto:wangzijie@cnpc.com.cn">王子杰</a>
 * @since 2018 -12-20
 */
public class PenetrationRequest<T> {

    /* ---- [ Attribute ] ---- */

    /**
     * 请求头.
     */
    private Map<String, String> headers;

    /**
     * 请求体.
     */
    private T body;

    /* ---- [ Constructor ] ---- */

    /**
     * 构造方法: 创建并初始化Req.
     */
    public PenetrationRequest() {
        this.headers = new HashMap<>();
        this.body = null;
    }

    /* ---- [ Custom Method ] ---- */

    /**
     * 添加Header.
     *
     * @param key   header key.
     * @param value header value.
     * @return previous value.
     */
    public String setHeader(String key, String value) {
        return this.headers.put(key, value);
    }

    /**
     * 删除Header.
     *
     * @param key header key.
     * @return previous value.
     */
    public String removeHeader(String key) {
        return this.headers.remove(key);
    }

    /**
     * 查询Header.
     *
     * @param key header key.
     * @return header value.
     */
    public String getHeader(String key) {
        return this.headers.get(key);
    }

    /* ---- [ Getter && Setter ] ---- */

    /**
     * Gets headers.
     *
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Sets headers.
     *
     * @param headers the headers
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public T getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(T body) {
        this.body = body;
    }
}
