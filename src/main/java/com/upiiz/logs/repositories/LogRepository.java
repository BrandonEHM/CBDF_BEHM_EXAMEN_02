package com.upiiz.logs.repositories;


import com.upiiz.logs.entities.LogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<LogEntity, Long> {
}