package javaee.json.demo.model.animal;

/**
 * Class representing a cat.
 */
public class Cat extends Pet {
    private boolean cuddly;

    public Cat() {}

    public Cat(String name, int age, Boolean hasFur, Boolean cuddly) {
        super(name, age, hasFur);
        this.cuddly = cuddly;
    }

    public boolean isCuddly() {
        return cuddly;
    }

    public void setCuddly(Boolean cuddly) {
        this.cuddly = cuddly;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", furry=" + isFurry() +
                ", cuddly=" + cuddly +
                "}";
    }
}
