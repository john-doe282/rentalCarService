package com.andrew.rental.service.impl;

import com.andrew.rental.model.Car;
import com.andrew.rental.model.Status;
import com.andrew.rental.repository.CarRepository;
import com.andrew.rental.service.CarService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public Car getCarById(UUID id) throws NotFoundException {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            return carOptional.get();
        }

        throw new NotFoundException("Car does not exist");
    }

    @Override
    public void setStatusById(UUID id, Status status) throws NotFoundException {
        Optional<Car> carOptional = carRepository.findById(id);
        if (!carOptional.isPresent()) {
            throw new NotFoundException("Car does not exist");
        }
        Car car = carOptional.get();
        car.setStatus(status);

        carRepository.save(car);

    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void deleteCarById(UUID id) throws NotFoundException {
        if (!carRepository.existsById(id)) {
            throw new NotFoundException("Car does not exist");
        }

        carRepository.deleteById(id);
    }
}