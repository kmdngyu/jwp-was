package webserver.http.request;

import webserver.http.response.HttpVersion;
import webserver.session.HttpSession;
import webserver.session.SessionStorage;
import webserver.session.UUIDGenerationStrategy;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader header;
    private RequestBody body;
    private Cookie cookie;
    private HttpSession session;

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestBody body, Cookie cookie) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
        this.cookie = cookie;
        this.session = initSession();
    }

    private HttpSession initSession() {
        String sessionId = cookie.getSessionId();
        if (isSessionExists(sessionId)) {
            return SessionStorage.get(sessionId);
        }
        return SessionStorage.create(new UUIDGenerationStrategy());
    }

    private boolean isSessionExists(String sessionId) {
        return sessionId != null && SessionStorage.exists(sessionId);
    }

    public HttpSession getSession() {
        return session;
    }

    public String getAbsPath() {
        return requestLine.getAbsPath();
    }

    public String getParam(String key) {
        return requestLine.getQueryString(key);
    }

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody(String key) {
        return body.getBody(key);
    }

    public RequestUri getUri() {
        return requestLine.getUri();
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeaderValue(String key) {
        return header.getHeader(key);
    }
}
