package behrooz.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbit.queue.name}")
    private String queueName;

    @Scheduled(fixedRateString = "${send.scheduled.delay}", initialDelay = 500)
    public void send() {
        rabbitTemplate.convertAndSend(queueName, new Date());
    }

    @RabbitListener(queues = "${rabbit.queue.name}", concurrency = "4")
    public void receiveMessage(Date date) {
        System.out.println("Received <" + date.toString() + ">");
    }
}
