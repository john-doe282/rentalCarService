package com.andrew.rental.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MqttRentalMessage<T> {
    @NonNull
    @JsonProperty("message_type")
    private MqttMessageType requestType;

    @NonNull
    T payload;
}
