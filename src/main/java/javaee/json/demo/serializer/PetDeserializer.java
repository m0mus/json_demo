package javaee.json.demo.serializer;

import javaee.json.demo.model.animal.Pet;

import javax.json.bind.JsonbException;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;

/**
 * Created by dmitry on 04/04/2017.
 */
public class PetDeserializer implements JsonbDeserializer<Pet> {
    @Override
    public Pet deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
        jsonParser.next();

        String className = jsonParser.getString();
        jsonParser.next();

        try {
            return deserializationContext.deserialize(Class.forName(className).asSubclass(Pet.class), jsonParser);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonbException("Cannot deserialize object.");
        }
    }
}
