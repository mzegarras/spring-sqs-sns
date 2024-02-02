package csonic.lab01.demo01.service;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements  TransactionService{

    private final SnsTemplate snsTemplate;

    @Autowired
    public TransactionServiceImpl(SnsTemplate snsTemplate){
        this.snsTemplate=snsTemplate;
    }

    @Override
    public void send(String messagePayload) {



    Message<String> msg = MessageBuilder
                .withPayload(messagePayload)

                .setHeader("ms-name","app01")
                .build();



       snsTemplate.sendNotification("transactions-sns",
               msg,
               "ProductCreated");


    }
}
