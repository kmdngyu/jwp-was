package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.parser.HttpRequestParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private DispatcherServlet dispatcherServlet;

    public RequestHandler(Socket connectionSocket, DispatcherServlet dispatcherServlet) {
        this.connection = connectionSocket;
        this.dispatcherServlet = dispatcherServlet;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        DispatcherServlet dispatcherServlet = new DispatcherServlet();

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out), httpRequest.getHttpVersion());
            dispatcherServlet.doDispatch(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
