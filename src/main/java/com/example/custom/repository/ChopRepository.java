package com.example.custom.repository;

import com.example.custom.entity.Chop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChopRepository extends JpaRepository<Chop, Long>, JpaSpecificationExecutor<Chop> {
    Optional<Chop> findByNumber(String number);
}
