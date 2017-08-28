package javaee.json.demo;

import javaee.json.demo.model.animal.Carrier;
import javaee.json.demo.model.animal.Cat;
import javaee.json.demo.model.animal.Dog;
import javaee.json.demo.model.animal.Pet;
import javaee.json.demo.model.person.Person;
import javaee.json.demo.model.person.PhoneNumber;
import javaee.json.demo.serializer.PetDeserializer;
import javaee.json.demo.serializer.PetSerializer;

import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * JSON-B/P demo main class.
 *
 * @author Dmitry Kornilov
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println((new ArrayList<Person>()).getClass());
        System.out.println((new ArrayList<Person>()).getClass().getGenericSuperclass());

        new Demo().start();
    }

    private void start() {
        while (true) {
            printOptions();
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine()) {
                case "q":
                case "Q":
                    return;
                case "1":
                    jsonParserDemo();
                    break;
                case "2":
                    jsonGeneratorDemo();
                    break;
                case "3":
                    jsonStreamDemo();
                    break;
                case "4":
                    jsonPointerDemo();
                    break;
                case "5":
                    jsonPatchDemo();
                    break;
                case "6":
                    jsonbSerializationDemo();
                    break;
                case "7":
                    jsonbDeserializationDemo();
                    break;
                case "8":
                    jsonbDeserializationGenericListDemo();
                case "9":
                    jsonbSerializerDemo();
                    break;
            }
        }
    }

    private void printOptions() {
        System.out.println();
        System.out.println("----------------------------------- JSON-B/P Demo ---------------------------------");
        System.out.println();
        System.out.println("Choose a scenario to demonstrate or press Q to end the demonstration:");
        System.out.println();
        System.out.println("1. JSON parser");
        System.out.println("2. JSON generator");
        System.out.println("3. JSON stream demo");
        System.out.println("4. JSON pointer");
        System.out.println("5. JSON patch");
        System.out.println("6. JSON-B serialization");
        System.out.println("7. JSON-B deserialization");
        System.out.println("8. JSON-B deserialization generic list");
        System.out.println("9. JSON-B serializer/deserializer list");

        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
    }

    /**
     * Demonstrates parsing a JSON document.
     *
     * 1. Parse document and list all JSON-P events
     * 2. List all keys corresponding to events
     * 3. Demonstrates skipping some parts of the document
     */
    private void jsonParserDemo() {
        String key = "";
        JsonParser parser = Json.createParser(Demo.class.getResourceAsStream("/jasons.json"));
        while (parser.hasNext()) {
            JsonParser.Event e = parser.next();
            System.out.print(e.name());

            switch (e) {
                case START_ARRAY:
                    if (key.equals("phoneNumbers")) {
                        parser.skipArray();
                    }
                    break;
                case KEY_NAME:
                    key = parser.getString();
                    System.out.print(" - " + parser.getString());
                    break;
                case VALUE_STRING:
                    System.out.print(" - " + parser.getString());
            }

            System.out.println();
        }
    }

    /**
     * Demonstrates creating of JSON document in the code.
     *
     * 1. Create a JSON document similar to jason.json using JsonGenerator
     */
    private void jsonGeneratorDemo() {
        JsonGenerator gen = Json.createGenerator(System.out);
        gen.writeStartArray()
                .writeStartObject()
                    .write("name", "Jason Bourne")
                    .write("profession", "Super Agent")
                    .writeStartArray("phoneNumbers")
                        .writeStartObject()
                            .write("type", "home")
                            .write("number", "123 456 789")
                        .writeEnd()
                    .writeEnd()
                .writeEnd()
                .writeEnd()
                .flush();
    }

    /**
     * Demonstrates using of Java 8 Stream API in JSON-P
     *
     * 1. Read cart.json document and calculate the total amount of items in the shopping cart
     */
    private void jsonStreamDemo() {
        JsonReader reader = Json.createReader(Demo.class.getResourceAsStream("/cart.json"));
        Double sum = reader.readArray().stream()
                .mapToDouble(o->o.asJsonObject().getJsonNumber("price").doubleValue())
                .sum();
        System.out.println(sum);
    }

    /**
     * Demonstrates using of JsonPointer
     *
     * 1. Create JsonPointer pointing on JSON document property and read value of this property
     */
    private void jsonPointerDemo() {
        JsonReader reader = Json.createReader(Demo.class.getResourceAsStream("/jasons.json"));
        JsonArray jasons = reader.readArray();

        JsonPointer p = Json.createPointer("/1/profession");
        JsonValue profession = p.getValue(jasons);

        System.out.println(profession.toString());
    }

    /**
     * Demonstrates using of JsonPatch
     *
     * 1. Create JsonPatch with multiple operations
     * 2. Demonstrate removing objects from a Json document
     * 3. Demonstrate changing properties values in a Json document
     */
    private void jsonPatchDemo() {
        JsonReader reader = Json.createReader(Demo.class.getResourceAsStream("/jasons.json"));
        JsonArray jasons = reader.readArray();

        JsonPatch patch = Json.createPatchBuilder()
                .remove("/2")
                .replace("/0/profession", "Maniac Killer")
                .replace("/1/profession", "Super Agent")
                .build();

        JsonArray result = patch.apply(jasons);
        System.out.println(result.toString());
    }

    /**
     * Demonstrates JSON-B serialization
     *
     * 1. Using of default mapping
     * 2. Using customizations
     * 3. Using formatting
     * 4. Using JsonbProperty and JsonbNillable annotations
     */
    private void jsonbSerializationDemo() {
        List<Person> jasons = new ArrayList<>(3);

        List<PhoneNumber> jasonBournePhones = new ArrayList<>(2);
        jasonBournePhones.add(new PhoneNumber("home", "123 456 789"));
        jasonBournePhones.add(new PhoneNumber("work", "123 555 555"));

        jasons.add(new Person("Jason Bourne", "Super Agent", jasonBournePhones));

        List<PhoneNumber> jasonVoorheesPhones = new ArrayList<>(1);
        jasonVoorheesPhones.add(new PhoneNumber("home", "666 666 666"));

        jasons.add(new Person("Jason Voorhees", "Maniac Killer", jasonVoorheesPhones));
        jasons.add(new Person("Jason", "Argonauts Leader", null));

        JsonbConfig config = new JsonbConfig()
                .withFormatting(true);
        Jsonb jsonb = JsonbBuilder.create(config);
        System.out.println(jsonb.toJson(jasons));
    }

    /**
     * Demonstrates JSON-B deserialization and how JSON-P and JSON-B can be used together.
     *
     * 1. Demonstrate how JSON-P and JSON-B can be used together
     * 2. Read Json document using JSON-P
     * 3. Use Json Pointer to get an object
     * 4. Use JSON-B to deserialize this object to POJO
     */
    private void jsonbDeserializationDemo() {
        JsonReader reader = Json.createReader(Demo.class.getResourceAsStream("/jasons.json"));
        JsonArray jasons = reader.readArray();

        JsonPointer p = Json.createPointer("/1");
        JsonValue voorhees = p.getValue(jasons);

        Jsonb jsonb = JsonbBuilder.create();
        Person person = jsonb.fromJson(voorhees.toString(), Person.class);

        System.out.println(person);
    }

    /**
     * Demonstrates deserialization of generic list.
     *
     * 1. Deserialize jasons.json into List&lt;Person&gt;
     */
    private void jsonbDeserializationGenericListDemo() {
        Jsonb jsonb = JsonbBuilder.create();
        Type type = new ArrayList<Person>() {}.getClass().getGenericSuperclass();

        List<Person> jasons = jsonb.fromJson(Demo.class.getResourceAsStream("/jasons.json"), type);
        System.out.println(jasons.get(1).getName());
    }

    /**
     * Demonstrates custom serializer and deserializer used to serialize/deserialize polymorphic classes.
     */
    private void jsonbSerializerDemo() {
        // Create a list of carrier objects
        List<Carrier<Pet>> carriers = new ArrayList<>();
        carriers.add(new Carrier<>(Carrier.TYPE.BAG, new Cat("Harris", 10, true, true)));
        carriers.add(new Carrier<>(Carrier.TYPE.CRATE, new Dog("Falco", 4, false, false)));
        Type carrierListType = new ArrayList<Carrier<Pet>>() {}.getClass().getGenericSuperclass();

        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withSerializers(new PetSerializer())
                .withDeserializers(new PetDeserializer());

        Jsonb jsonb = JsonbBuilder.create(config);

        String json = jsonb.toJson(carriers, carrierListType);
        System.out.println(json);

        List<Carrier<Pet>> list = jsonb.fromJson(json, carrierListType);
        System.out.println(list.get(0).getCarriedPet().getClass().getName());
    }
}