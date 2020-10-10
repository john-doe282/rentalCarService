package com.andrew.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "car")
@Data
@RequiredArgsConstructor
@DynamicUpdate
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
    private UUID ownerId;

}
