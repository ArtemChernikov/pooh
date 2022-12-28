package ru.job4j.pooh;

/**
 * Класс описывает модель ответа от сервиса
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 27.12.2022
 */
public class Resp {
    /**
     * Поле статус, если запрос прошел успешно
     */
    static final String GOOD_STATUS = "200";
    /**
     * Поле статус, если нет данных
     */
    static final String BAD_STATUS = "204";
    /**
     * Поле текст ответа
     */
    private final String text;
    /**
     * Поле статус ответа (HTTP response status codes)
     */
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }
}
