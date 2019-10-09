package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMethod;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;
import webserver.servlet.exception.MethodNotAllowedException;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractRequestServlet implements HttpServlet {
    public static final String METHOD_NOT_ALLOW_MESSAGE = "지원하지 않는 메소드 입니다.";

    @Override
    public boolean canMapping(RequestUri requestUri) {
        return requestUri.isSameAbsPath(getUrl());
    }

    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }

        if (httpRequest.getMethod() == RequestMethod.POST) {
            doPost(httpRequest, httpResponse);
            return;
        }
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }

    @Override
    public void move(ModelAndView mv, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        View view = mv.getView();
        view.render(mv.getModelMap(), httpRequest, httpResponse);
    }

    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }

    protected ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }


    protected abstract String getUrl();

}
