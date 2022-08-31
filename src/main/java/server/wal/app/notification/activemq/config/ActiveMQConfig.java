package server.wal.app.notification.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.*;

@Configuration
public class ActiveMQConfig {

    @Bean
    public ActiveMQTopic morningTopic() {
        return new ActiveMQTopic()
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        // java8에서 LocalDate, LocalDateTime 타입 사용을 위해 선언한 설정
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ISO_LOCAL_DATE));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ISO_LOCAL_DATE_TIME));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(ISO_LOCAL_DATE));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ISO_LOCAL_DATE_TIME));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);

        converter.setObjectMapper(objectMapper);

        return converter;
    }

}
