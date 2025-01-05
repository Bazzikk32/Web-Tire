package com.example.Web_Koleso.repositories;

import com.example.Web_Koleso.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
//    Optional<Warehouse> findByName(String name);
    Warehouse findByName(String name);
}
