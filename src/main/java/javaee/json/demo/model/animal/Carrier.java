package javaee.json.demo.model.animal;

import javax.json.bind.annotation.JsonbCreator;

/**
 * A container to carry an animal.
 * @param <P>
 */
public class Carrier<P extends Pet> {
    public enum TYPE {
        BAG, CRATE, TROLLEY
    }

    private TYPE carrierType;
    private P carriedPet;

    @JsonbCreator
    public Carrier(TYPE carrierType, P carriedPet) {
        this.carrierType = carrierType;
        this.carriedPet = carriedPet;
    }

    public TYPE getCarrierType() {
        return carrierType;
    }

    public P getCarriedPet() {
        return carriedPet;
    }

    public void setCarrierType(TYPE carrierType) {
        this.carrierType = carrierType;
    }

    public void setCarriedPet(P carriedPet) {
        this.carriedPet = carriedPet;
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "carrierType=" + carrierType +
                ", carriedPet=" + carriedPet +
                '}';
    }
}
