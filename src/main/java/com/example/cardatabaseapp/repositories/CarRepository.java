package com.example.cardatabaseapp.repositories;

import com.example.cardatabaseapp.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
    Page<Car> findAllByOrderByIdDesc(Pageable pageable);

    Page<Car> findAllByBrandIgnoreCaseContainingOrModelIgnoreCaseContainingOrEngineIgnoreCaseContainingOrBodyTypeIgnoreCaseContainingOrGearTypeIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String brand, String model, String engine, String bodyType, String gearType, String description, Pageable pageable);
}
