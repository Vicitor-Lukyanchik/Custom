package com.example.custom.converter;

import com.example.custom.dto.ChopDto;
import com.example.custom.entity.Chop;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChopToDtoConverter implements Converter<Chop, ChopDto> {

    @Override
    public ChopDto convert(Chop chop) {
        return ChopDto.builder()
                .id(chop.getId()).description(chop.getDescription())
                .name(chop.getName()).number(chop.getNumber())
                .build();
    }
}
