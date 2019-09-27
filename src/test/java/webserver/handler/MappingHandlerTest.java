package webserver.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.RequestUri;
import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import static org.assertj.core.api.Assertions.assertThat;

class MappingHandlerTest {
    @DisplayName("리퀘스트 서블릿 매핑")
    @Test
    void getServlets_request_true() {
        HttpServlet homeServlet = MappingHandler.getServlets(new RequestUri("/"));
        HttpServlet userCreateServlet = MappingHandler.getServlets(new RequestUri("/user/create"));
        assertThat(homeServlet).isInstanceOf(HomeServlet.class);
        assertThat(userCreateServlet).isInstanceOf(UserCreateServlet.class);
    }

    @DisplayName("정적 파일 서블릿 매핑")
    @Test
    void getServlets_staticFile_true() {
        HttpServlet fileServlet = MappingHandler.getServlets(new RequestUri("/abc.html"));
        assertThat(fileServlet).isInstanceOf(FileServlet.class);
    }
}