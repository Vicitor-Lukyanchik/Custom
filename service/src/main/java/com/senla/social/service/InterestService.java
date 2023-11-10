package com.senla.social.service;

import com.senla.social.entity.Interest;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public interface InterestService {

    void create(Interest interest);

    Interest findByName(String name);
}
