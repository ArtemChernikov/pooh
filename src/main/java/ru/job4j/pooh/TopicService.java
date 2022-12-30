package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 28.12.2022
 */
public class TopicService implements Service {
    private final Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>>
            topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String httpRequestType = req.httpRequestType();
        String text = "";
        if (Req.POST.equals(httpRequestType)) {
            topics.getOrDefault(req.getSourceName(), new ConcurrentHashMap<>())
                    .forEach((key, value) -> value.offer(req.getParam()));
        }
        if (Req.GET.equals(httpRequestType)) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topic = topics.get(req.getSourceName());
            topic.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            text = topic.get(req.getParam()).poll();
        }
        return text == null ? new Resp("", Resp.BAD_STATUS) : new Resp(text, Resp.GOOD_STATUS);
    }
}
