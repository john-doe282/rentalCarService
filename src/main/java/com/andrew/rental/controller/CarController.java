package com.andrew.rental.controller;

import com.andrew.rental.model.Car;
import com.andrew.rental.service.CarService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping("{id}")
    Car getCarById(@PathVariable("id") UUID id) throws NotFoundException {
        return carService.getCarById(id);
    }

    @PostMapping
    void addCar(@Validated @RequestBody Car car) {
        carService.addCar(car);
    }

    @DeleteMapping("{id}")
    void deleteCar(@PathVariable("id") UUID id) throws NotFoundException {
        carService.deleteCarById(id);
    }
}