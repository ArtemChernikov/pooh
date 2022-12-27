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
