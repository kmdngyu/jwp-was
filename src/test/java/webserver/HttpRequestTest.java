package webserver;

import org.junit.jupiter.api.Test;
import utils.HttpRequestUtils;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.request.RequestMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";
    final String INDEX_URL = "/index.html";

    @Test
    void getPath_indexUrl_true() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_index_test.txt"));
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(request.getFilePath()).isEqualTo(HttpRequestUtils.ROOT_TEMPLATE_FILE_PATH + INDEX_URL);
    }

    @Test
    void getParameter_userInfo_true() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_param_test.txt"));
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(request.getParam("userId")).isEqualTo("id");
        assertThat(request.getParam("password")).isEqualTo("password");
        assertThat(request.getParam("name")).isEqualTo("gyu");
    }

    @Test
    void getMethod_post_true() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_post_test.txt"));
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(request.getMethod()).isEqualTo(RequestMethod.POST);
    }

    @Test
    void getBody_userId_true() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_post_test.txt"));
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(request.getBody("userId")).isEqualTo("javajigi");
        assertThat(request.getBody("password")).isEqualTo("password");
    }
}