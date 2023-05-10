package com.example.wuiz.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByOwner(String owner);
}
