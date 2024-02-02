package csonic.lab01.demo01consumer.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementResultCallback;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;


import java.time.OffsetDateTime;
import java.util.Collection;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
public class AwsConfigv2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsConfigv2.class);

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Bean
    SqsAsyncClient sqsAsyncClient(){
        return SqsAsyncClient
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        // add more Options
    }

    /*
    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {

        return SqsMessageListenerContainerFactory.builder()
                .configure(options -> options.acknowledgementMode(AcknowledgementMode.MANUAL))
                .sqsAsyncClient(sqsAsyncClient)
                //.acknowledgementResultCallback(new AckResultCallback())
                .build();
    }
     */



    static class AckResultCallback implements AcknowledgementResultCallback<Object> {

        @Override
        public void onSuccess(Collection<Message<Object>> messages) {
            //AcknowledgementResultCallback.super.onSuccess(messages);
            LOGGER.info("Ack with success at {}", OffsetDateTime.now());
        }

        @Override
        public void onFailure(Collection<Message<Object>> messages, Throwable t) {
            //AcknowledgementResultCallback.super.onFailure(messages, t);
            LOGGER.error("Ack with fail", t);
        }
    }


    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(
            SqsAsyncClient sqsAsyncClient,
            MappingJackson2MessageConverter jacksonConverter) {

        var converter = new SqsMessagingMessageConverter();
        converter.setPayloadMessageConverter(jacksonConverter);
        converter.setPayloadTypeHeader(APPLICATION_JSON_VALUE);


        var factory = SqsMessageListenerContainerFactory
                .builder()
                .configure(options->{
                    options.messageConverter(converter);
                    options.acknowledgementMode(AcknowledgementMode.MANUAL);
                })
                .sqsAsyncClient(sqsAsyncClient)
                .build();

        return factory;

    }


    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper()
                .findAndRegisterModules()
                .enable(USE_BIG_DECIMAL_FOR_FLOATS)
                .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
        var messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setSerializedPayloadClass(String.class); //= String::class.java
        messageConverter.setStrictContentTypeMatch(false);
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

}
