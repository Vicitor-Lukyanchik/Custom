package com.example.custom.service.impl;

import com.example.custom.converter.ChopToDtoConverter;
import com.example.custom.dto.ChopDto;
import com.example.custom.dto.IndexDto;
import com.example.custom.entity.Chop;
import com.example.custom.repository.ChopRepository;
import com.example.custom.service.ChopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.example.custom.specification.ChopSpecifications.findByNameWithSortByParameters;
import static com.example.custom.specification.ChopSpecifications.findWithSortByParameters;

@Service
@RequiredArgsConstructor
public class ChopServiceImpl implements ChopService {

    private final ChopToDtoConverter toDtoConverter;

    private final ChopRepository chopRepository;
    private int pageSize = 5;

    @Override
    public Page<Chop> findAll(IndexDto indexDto) {
        if (indexDto.getPageSize() != 0) {
            pageSize = indexDto.getPageSize();
        }
        if (indexDto.getName().isEmpty()) {
            return findAllWithPaginationAndSorting(indexDto.getOffset(), indexDto.getSortParameters());
        }
        return findAllWithPaginationAndSortingAndSearch(indexDto.getOffset(), indexDto.getSortParameters(), indexDto.getName());
    }

    private Page<Chop> findAllWithPaginationAndSorting(int offset, Map<String, Boolean> parameters) {
        return chopRepository.findAll(findWithSortByParameters(parameters), PageRequest.of(offset, pageSize));
    }

    private Page<Chop> findAllWithPaginationAndSortingAndSearch(int offset, Map<String, Boolean> parameters, String name) {
        return chopRepository.findAll(findByNameWithSortByParameters(name, parameters), PageRequest.of(offset, pageSize));
    }

    @Override
    public Optional<ChopDto> findByNumber(String number) {
        Optional<Chop> interest = chopRepository.findByNumber(number);
        if (interest.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toDtoConverter.convert(interest.get()));
    }
}
