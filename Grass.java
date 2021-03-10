import java.util.List;

/**
 * A simple model of grass.
 * Grass can age, spread, and die.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Grass extends Plant {

    //The max age of the grass
    private static final int MAX_AGE = 18;
    //The food value of the grass when it is eaten
    private static final int FOOD_VALUE = 10;
    //The age when the grass is at a stage where it can be eaten
    private static final int EDIBLE_AGE = 2;
    //The probability that the grass patch will spread to an adjacent spot on the field
    private static final double SPREADING_PROBABILITY = 0.06;

    /**
     * Create a new grass patch and place it on the field
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * @param field The simulation field.
     * @param location Location to create the new grass.
     * @return The new grass object
     */
    protected Plant newPlantObject(Field field, Location location) {
        return new Grass(field, location);
    }

    /**
     * @return The grass' max age
     */
    protected int getMAX_AGE() {
        return MAX_AGE;
    }

    /**
     * @return The grass' food value
     */
    protected int getFOOD_VALUE() {
        return FOOD_VALUE;
    }

    /**
     * @return The grass' edible age
     */
    protected int getEDIBLE_AGE() {
        return EDIBLE_AGE;
    }

    /**
     * @return The grass' spreading probability
     */
    protected double getSPREADING_PROBABILITY() {
        return SPREADING_PROBABILITY;
    }
}
