package javaee.json.demo.model.animal;

/**
 * Abstract class representing an animal.
 */
public abstract class Pet {
    private String name;
    private int age;
    private boolean furry;

    public Pet() {}

    public Pet(String name, int age, Boolean furry) {
        this.name = name;
        this.age = age;
        this.furry = furry;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isFurry() {
        return furry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFurry(Boolean furry) {
        this.furry = furry;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", furry=" + furry +
                '}';
    }
}
