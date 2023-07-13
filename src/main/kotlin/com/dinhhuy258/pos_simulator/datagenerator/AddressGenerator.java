package com.dinhhuy258.pos_simulator.datagenerator;

import com.dinhhuy258.pos_simulator.types.DeliveryAddress;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Random;

class AddressGenerator {
    private static final AddressGenerator ourInstance = new AddressGenerator();
    private final Random random;

    private DeliveryAddress[] addresses;

    private int getIndex() {
        return random.nextInt(100);
    }

    static AddressGenerator getInstance() {
        return ourInstance;
    }

    private AddressGenerator() {
        final String DATAFILE = "src/main/resources/data/address.json";
        final ObjectMapper mapper;
        random = new Random();
        mapper = new ObjectMapper();
        try {
            addresses = mapper.readValue(new File(DATAFILE), DeliveryAddress[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    DeliveryAddress getNextAddress() {
        return addresses[getIndex()];
    }

}
