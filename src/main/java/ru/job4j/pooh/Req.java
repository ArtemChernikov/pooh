package ru.job4j.pooh;

/**
 * Класс служит для парсинга входящих запросов
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 27.12.2022
 */
public class Req {
    static final String GET = "GET";
    static final String POST = "POST";
    /**
     * Поле тип запроса (GET или POST)
     */
    private final String httpRequestType;
    /**
     * Поле режим работы (queue или topic)
     */
    private final String poohMode;
    /**
     * Поле имя конкретной очереди или топика
     */
    private final String sourceName;
    /**
     * Поле содержимое запроса
     */
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    /**
     * Метод используется для парсинга входящего запроса и возвращения модели запроса
     *
     * @param content - запрос
     * @return - возвращает объект {@link Req}
     */
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
