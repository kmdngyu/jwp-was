package webserver.handler;

import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MappingHandler {
    private static Map<String, Object> servlets = new HashMap<>();

    static {
        servlets.put("/", new HomeServlet());
        servlets.put("/user/create", new UserCreateServlet());
    }

    public static HttpServlet getServlets(String absPath) {
        Optional path = Optional.ofNullable(servlets.get(absPath));
        return (HttpServlet) path.orElse(new FileServlet());
    }
}
