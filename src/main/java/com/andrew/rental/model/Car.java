package com.andrew.rental.model;

import com.andrew.rental.AddCarRequest;
import com.andrew.rental.GetCarResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "car")
@Data
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor
@Builder
public final class Car {
    @Id
    @GeneratedValue
    private UUID id;

    private String model;
    private String type;
    private int pricePerHour;

    @Enumerated(EnumType.STRING)
    private Status status;


    @Column(name = "owner_id")
    @JsonProperty("owner_id")
    private UUID ownerId;

    public GetCarResponse toGetCarResponse() {
        return GetCarResponse.newBuilder().
                setId(id.toString()).
                setModel(model).
                setType(type).
                setPricePerHour(pricePerHour).
                setOwnerId(ownerId.toString()).
                setStatus(com.andrew.rental.Status.valueOf(status.toString())).
                build();
    }

    public static Car fromAddRequest (AddCarRequest carRequest) {
        return new CarBuilder().
                model(carRequest.getModel()).
                type(carRequest.getType()).
                pricePerHour(carRequest.getPricePerHour()).
                ownerId(UUID.fromString(carRequest.getOwnerId())).
                status(Status.valueOf(carRequest.getStatus().toString())).
                build();
    }
}
