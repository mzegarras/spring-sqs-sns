package csonic.lab01.demo01.service;

import csonic.lab01.demo01.dto.OrderDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class OrderServiceImpl implements OrderService {

    private static final String QUEUE_NAME = "standar-queue1";

    private final SqsTemplate sqsTemplate;

    @Autowired
    public OrderServiceImpl(SqsTemplate sqsTemplate){
        this.sqsTemplate=sqsTemplate;
    }

    @Override
    public void send(String messagePayload) {

        /*Message<String> msg = MessageBuilder.withPayload(messagePayload)
                .setHeader("sender", "app1")
                .setHeader("contentType","application/json")
                .setHeaderIfAbsent("country", "AE")
                .build();

        sqsTemplate.send(to -> to
                .queue("standar-queue1")
                .payload(msg)
        );*/


        sqsTemplate
                .send(sqsSendOptions ->
                        sqsSendOptions
                                .queue("generar-xml")
                                .payload("myPayload")
                );
    }

    @Override
    public void sendOrder(OrderDto messagePayload) {
        var dto = OrderDto.builder()
                .id(UUID.randomUUID())
                .customerId("12")
                .build();



        Message<OrderDto> msg = MessageBuilder
                .withPayload(dto)
                //.setHeader("sender", "app1")
                .setHeader("contentType","application/json")
                //.setHeaderIfAbsent("country", "AE")
                .build();

        sqsTemplate.send(to -> to
                .queue("generar-xml")
                .payload(msg)
        );
    }



}
