package webserver.view;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

public class FileView implements View {
    private String name;

    public FileView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(name);
        httpResponse.ok();
        httpResponse.appendContentHeader(FileIoUtils.loadMIMEFromClasspath(name), body.length);
        httpResponse.setBody(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileView fileView = (FileView) o;
        return Objects.equals(name, fileView.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
