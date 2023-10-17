package com.example.custom.service;

import com.example.custom.dto.ChopDto;
import com.example.custom.dto.IndexDto;
import com.example.custom.entity.Chop;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ChopService {

    Page<Chop> findAll(IndexDto indexDto);

    Optional<ChopDto> findByNumber(String number);
}
