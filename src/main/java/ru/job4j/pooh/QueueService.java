package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 28.12.2022
 */
public class QueueService implements Service {

    private Map<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String httpRequestType = req.httpRequestType();
        String text = "";
        if (Req.POST.equals(httpRequestType)) {
            queues.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queues.get(req.getSourceName()).add(req.getParam());
        }
        if (Req.GET.equals(httpRequestType)) {
            ConcurrentLinkedQueue<String> rsl = queues.get(req.getSourceName());
            text = rsl == null ? null : rsl.poll();
        }
        return text == null ? new Resp("", Resp.BAD_STATUS) : new Resp(text, Resp.GOOD_STATUS);
    }
}
