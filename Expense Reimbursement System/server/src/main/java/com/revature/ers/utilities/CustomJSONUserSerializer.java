package com.revature.ers.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.revature.ers.entities.User;

import java.io.IOException;

public class CustomJSONUserSerializer extends JsonSerializer<User> {

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("userId", user.getUserId());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("role", user.getRole().toString());
        jsonGenerator.writeEndObject();
    }
}
