package webserver.http.response;

import webserver.handler.exception.NotSupportedFileType;

import java.util.Arrays;
import java.util.List;

public enum FileType {
    ALL("*", "*/*"),
    CSS("css", "text/css"),
    JAVASCRIPT("js", "application/javascript"),
    HTML("html", "text/html"),
    WOFF("woff", "application/font-woff"),
    TTF("ttf", "application/x-font-ttf"),
    ICO("ico", "image/x-icon");

    private final String extensionName;
    private final String mimeName;

    FileType(String extensionName, String mimeName) {
        this.extensionName = extensionName;
        this.mimeName = mimeName;
    }

    public static boolean isSupportedFile(List<String> target) {
        return Arrays.stream(FileType.values())
                .anyMatch(type -> target.contains(type.mimeName));
    }

    public static FileType getTypeByExtension(String target) {
        return Arrays.stream(FileType.values())
                .filter(type -> target.equals(type.getExtensionName()))
                .findFirst().orElseThrow(() -> new NotSupportedFileType(target));
    }

    public String getExtensionName() {
        return extensionName;
    }

    public String getMimeName() {
        return mimeName;
    }
}
