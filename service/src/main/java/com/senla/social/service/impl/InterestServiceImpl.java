package com.senla.social.service.impl;

import com.senla.social.entity.Interest;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.InterestRepository;
import com.senla.social.service.InterestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class InterestServiceImpl implements InterestService {

    /**
     * For work with interest database
     */
    private final InterestRepository interestRepository;

    /**
     * For creating new interest
     * @param interest that will create
     */
    @Override
    @Transactional
    public void create(@Valid Interest interest) {
        Interest createdInterest = interestRepository.save(interest);
        log.info("In create - interest successfully have been created by id : {}", createdInterest.getId());
    }

    /**
     * For finding interest by name
     * @param name to find interest
     * @return
     */
    @Override
    @Transactional
    public Interest findByName(String name) {
        Interest result = interestRepository.findByName(name);

        if (result == null) {
            log.warn("IN findByName - haven't founded by name: {}", name);
            throw new ServiceException("Interest haven't founded by name : " + name);
        }

        log.info("IN findByName - have founded interest by name: {}", name);
        return result;
    }
}
