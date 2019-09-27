package webserver.servlet;

import helper.IOHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

class UserCreateServletTest {
    @DisplayName("유저 생성")
    @Test
    void doPost_userDataByBody_redirect() throws IOException {
        BufferedReader bufferedReader = IOHelper.createBuffer(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: text/html,*/*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
    }
}