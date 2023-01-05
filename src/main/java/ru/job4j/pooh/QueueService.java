package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Класс описыввает режим queue со следующими возможностями:
 * Отправитель посылает запрос на добавление данных с указанием очереди (weather) и значением параметра (temperature=18).
 * Сообщение помещается в конец очереди. Если очереди нет в сервисе, то создается новая очередь и помещается в нее сообщение.
 * Получатель посылает запрос на получение данных с указанием очереди. Сообщение забирается из начала очереди и удаляется.
 * Если в очередь приходят несколько получателей, то они поочередно получают сообщения из очереди.
 * Каждое сообщение в очереди может быть получено только одним получателем.
 * В режиме "queue" все потребители получает данные из одной очереди.
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 28.12.2022
 */
public class QueueService implements Service {

    private final Map<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String httpRequestType = req.httpRequestType();
        String text = "";
        if (Req.POST.equals(httpRequestType)) {
            queues.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queues.get(req.getSourceName()).add(req.getParam());
        }
        if (Req.GET.equals(httpRequestType)) {
            text = queues.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
        }
        return text == null ? new Resp("", Resp.BAD_STATUS) : new Resp(text, Resp.GOOD_STATUS);
    }
}
