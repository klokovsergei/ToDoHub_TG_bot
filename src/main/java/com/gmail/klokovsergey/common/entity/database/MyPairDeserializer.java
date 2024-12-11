package com.gmail.klokovsergey.common.entity.database;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

public class MyPairDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        return null;
    }
}
