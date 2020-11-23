package com.andrew.rental.controller.grpc;

import com.andrew.rental.*;
import com.andrew.rental.model.Car;
import com.andrew.rental.model.Status;
import com.andrew.rental.service.CarService;
import io.grpc.stub.StreamObserver;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@GRpcService
public class GrpcCarController extends CarServiceGrpc.CarServiceImplBase {
    @Autowired
    private CarService carService;

    @Override
    public void all(AllCarsRequest request, StreamObserver<AllCarsResponse> responseObserver) {
        List<Car> cars = carService.findAll();
        List<GetCarResponse> convertedCars = cars.stream().
                map(Car::toGetCarResponse).
                collect(Collectors.toList());

        AllCarsResponse response = AllCarsResponse.newBuilder().
                addAllCars(convertedCars).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @SneakyThrows
    @Override
    public void get(GetCarRequest request, StreamObserver<GetCarResponse> responseObserver) {
        Car car = carService.getCarById(UUID.fromString(request.getId()));
        responseObserver.onNext(car.toGetCarResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddCarRequest request, StreamObserver<AddCarResponse> responseObserver) {
        carService.addCar(Car.fromAddRequest(request));
        responseObserver.onNext(AddCarResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @SneakyThrows
    @Override
    public void delete(DeleteCarRequest request, StreamObserver<DeleteCarResponse> responseObserver) {
        carService.deleteCarById(UUID.fromString(request.getId()));
        responseObserver.onNext(DeleteCarResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @SneakyThrows
    @Override
    public void setStatus(SetStatusRequest request, StreamObserver<SetStatusResponse> responseObserver) {
        UUID id = UUID.fromString(request.getId());
        com.andrew.rental.model.Status status = com.andrew.rental.model.Status.valueOf(request.getStatus().toString());
        carService.setStatusById(id, status);
        responseObserver.onNext(SetStatusResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
