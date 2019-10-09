package webserver.servlet;

import db.DataBase;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.session.HttpSession;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class UserListServlet extends AbstractRequestServlet {
    private final String url = "/user/list";
    private Resolver resolver;

    public UserListServlet(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        Map<String, Object> model = new HashMap<>();
        HttpSession httpSession = httpRequest.getSession();
        if (isLoggedIn(httpSession)) {
            model.put("users", DataBase.findAll());
            return new ModelAndView(resolver.createView("/user/list"), model);
        }
        return new ModelAndView(new RedirectView("/user/login"));
    }

    private boolean isLoggedIn(HttpSession httpSession) {
        return httpSession.getAttribute("user") != null;
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
