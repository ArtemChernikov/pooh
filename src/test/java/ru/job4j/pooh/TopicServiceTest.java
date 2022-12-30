package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TopicServiceTest {
    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req(Req.GET, "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req(Req.POST, "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req(Req.GET, "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req(Req.GET, "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result1.status()).isEqualTo(Resp.GOOD_STATUS);
        assertThat(result2.text()).isEqualTo("");
        assertThat(result2.status()).isEqualTo(Resp.BAD_STATUS);
    }

    @Test
    public void whenPostNewDataAndGoodStatus() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        topicService.process(
                new Req(Req.GET, "topic", "weather", paramForSubscriber1)
        );
        Resp resp = topicService.process(
                new Req(Req.POST, "topic", "weather", paramForPublisher)
        );
        assertThat(resp.text()).isEqualTo("");
        assertThat(resp.status()).isEqualTo(Resp.GOOD_STATUS);
    }

    @Test
    public void whenNewSubscriberGetAndBadStatus() {
        TopicService topicService = new TopicService();
        String paramForSubscriber1 = "client407";
        Resp resp = topicService.process(
                new Req(Req.GET, "topic", "weather", paramForSubscriber1)
        );
        assertThat(resp.text()).isEqualTo("");
        assertThat(resp.status()).isEqualTo(Resp.BAD_STATUS);
    }

    @Test
    public void whenSubscriberGetAndGoodStatus() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        topicService.process(
                new Req(Req.GET, "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req(Req.POST, "topic", "weather", paramForPublisher)
        );
        Resp resp = topicService.process(
                new Req(Req.GET, "topic", "weather", paramForSubscriber1)
        );
        assertThat(resp.text()).isEqualTo(paramForPublisher);
        assertThat(resp.status()).isEqualTo(Resp.GOOD_STATUS);
    }
}