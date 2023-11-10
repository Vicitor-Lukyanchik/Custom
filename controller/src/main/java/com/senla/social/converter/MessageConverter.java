package com.senla.social.converter;

import com.senla.social.dto.message.AllMessageDto;
import com.senla.social.entity.Message;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Component
public class MessageConverter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AllMessageDto convert(Message message, String name){
        AllMessageDto result = new AllMessageDto();
        result.setName(name);
        result.setText(message.getText());
        result.setDateTime(message.getDateTime().format(DATE_TIME_FORMATTER));
        return result;
    }
}
