package webserver.servlet;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.session.HttpSession;
import webserver.view.ModelAndView;

import java.io.IOException;

public class UserLoginServlet extends AbstractRequestServlet {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRequestServlet.class);

    private final String url = "/user/login";
    private Resolver resolver;

    public UserLoginServlet(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return new ModelAndView(resolver.createView(url));
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String id = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");
        User user = DataBase.findUserById(id);
        HttpSession httpSession = httpRequest.getSession();
        if (canLogin(password, user)) {
            httpSession.setAttribute("user", user);
            httpResponse.setCookie(Cookie.SESSION_ID_KEY, httpSession.getId());
            return new ModelAndView(resolver.createView("redirect:/"));
        }
        return new ModelAndView(resolver.createView("redirect:/user/login_failed.html"));
    }

    private boolean canLogin(String password, User user) {
        return user != null && user.isCorrectPassword(password);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
