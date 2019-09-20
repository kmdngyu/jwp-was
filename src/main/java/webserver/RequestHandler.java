package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.MappingHandler;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

            Map<String, Object> servlets = new HashMap<>();
            servlets.put("/", new HomeServlet());
            servlets.put("/user/create", new UserCreateServlet());

            HttpServlet httpServlet = MappingHandler.getDispatcher(request.getAbsPath(), servlets);
            HttpResponse httpResponse = httpServlet.run(request);
            httpResponse.render(new DataOutputStream(out));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
