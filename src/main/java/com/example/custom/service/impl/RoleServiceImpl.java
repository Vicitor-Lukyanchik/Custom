package com.example.custom.service.impl;

import com.example.custom.entity.Role;
import com.example.custom.repository.RoleRepository;
import com.example.custom.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
