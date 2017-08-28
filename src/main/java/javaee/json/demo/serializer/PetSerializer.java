package javaee.json.demo.serializer;

import javaee.json.demo.model.animal.Pet;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/**
 * Created by dmitry on 04/04/2017.
 */
public class PetSerializer implements JsonbSerializer<Pet> {
    @Override
    public void serialize(Pet pet, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
        jsonGenerator.writeStartObject();
        serializationContext.serialize(pet.getClass().getName(), pet, jsonGenerator);
        jsonGenerator.writeEnd();
    }
}
