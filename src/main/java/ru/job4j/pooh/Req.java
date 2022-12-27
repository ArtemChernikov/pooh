package ru.job4j.pooh;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 27.12.2022
 */
public class Req {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] partsOfContent = content.split(System.lineSeparator());
        String[] mainContent = partsOfContent[0].split("/");
        String httpRequestType = mainContent[0].trim();
        String param = "";
        if (GET.equals(httpRequestType) && mainContent.length == 5) {
            param = mainContent[mainContent.length - 2].split(" ")[0];
        }
        if (POST.equals(httpRequestType)) {
            param = partsOfContent[partsOfContent.length - 1];
        }
        return new Req(httpRequestType, mainContent[1], mainContent[2].split(" ")[0], param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
