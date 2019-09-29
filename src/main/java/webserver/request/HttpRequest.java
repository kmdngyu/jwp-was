package webserver.request;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader header;
    private RequestBody body;

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
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

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public RequestUri getUri() {
        return requestLine.getUri();
    }

    public boolean isHeaderContain(String key, String value) {
        return header.getHeader(key).contains(value);
    }
}
