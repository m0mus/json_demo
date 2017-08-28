package javaee.json.demo.model.animal;

/**
 * Class representing a dog.
 */
public class Dog extends Pet {
    private boolean barking;

    public Dog() {}

    public Dog(String name, int age, Boolean hasFur, Boolean barking) {
        super(name, age, hasFur);
        this.barking = barking;
    }

    public boolean isBarking() {
        return barking;
    }

    public void setBarking(Boolean barking) {
        this.barking = barking;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", furry=" + isFurry() +
                ", barking=" + barking +
                "}";
    }
}
