package com.example.custom.service;

import com.example.custom.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findByName(String name);
}
