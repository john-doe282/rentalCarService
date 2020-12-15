package com.andrew.rental.controller.rabbit;

import com.andrew.rental.dto.MqttMessageType;
import com.andrew.rental.model.Car;
import com.andrew.rental.service.CarService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.var;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Service
public class MqttCallbackService implements MqttCallback {
    @Autowired
    private CarService carService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void connectionLost(Throwable throwable) {

    }

    private void handleAddRequest(Object payload) {
        Car car = mapper.convertValue(payload, Car.class);
        carService.addCar(car);
    }

    private void handleDeleteRequest(Object payload) throws NotFoundException {
        String id = mapper.convertValue(payload, String.class);
        carService.deleteCarById(UUID.fromString(id));
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        if (s.equalsIgnoreCase("r/car")) {
            var typeRef = new TypeReference<HashMap<String, Object>>() {};
            HashMap<String, Object> hashMap = mapper.
                    readValue(mqttMessage.getPayload(), typeRef);
            MqttMessageType message_type = (MqttMessageType) hashMap.
                    get("message_type");
            switch (message_type) {
                case ADD:
                    handleAddRequest(hashMap.get("payload"));
                    break;
                case DELETE:
                    handleDeleteRequest(hashMap.get("payload"));
                    break;
                default:
                    throw new IllegalArgumentException("Message type not supported");
            }


        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
