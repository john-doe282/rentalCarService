package com.andrew.rental.service;

import com.andrew.rental.model.Car;
import com.andrew.rental.model.Status;
import javassist.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface CarService {
    void addCar(Car car);
    Car getCarById(UUID id) throws NotFoundException;
    void setStatusById(UUID id, Status status) throws NotFoundException;
    List<Car> findAll();
    void deleteCarById(UUID id) throws NotFoundException;
}
