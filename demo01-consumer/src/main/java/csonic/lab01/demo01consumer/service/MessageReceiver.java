package csonic.lab01.demo01consumer.service;
//https://anand-guptaa.medium.com/event-driven-architecture-using-aws-sqs-and-spring-boot-d29fc3b1b25b


import csonic.lab01.demo01consumer.dto.OrderDto;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.messaging.Message;

@Service
public class MessageReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);


    //@SqsListener(value = "generar-xml")
    public void receiveMessage1(Message<?> message) {



        logger.info("Message received on listen method at {}", OffsetDateTime.now());
        logger.info("message received {} ",message.getPayload());
        logger.info("message received {} ","=========FAILED=========");
        var rp = 5 / 0;

        Acknowledgement.acknowledge(message);

    }


    @SqsListener(value = "generar-xml")
    public void receiveMessage2(Message<OrderDto> message) {

        logger.info("Message received on listen method at {}", OffsetDateTime.now());
        logger.info("message received {} ",message);

        //var x = 5 / 0;
        Acknowledgement.acknowledge(message);
    }


}
