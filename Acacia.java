import java.util.List;

/**
 * A simple model of an acacia tree.
 * Acacia trees can age, spread, and die.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Acacia extends Plant {

    //The max age of the acacia tree
    private static final int MAX_AGE = 14;
    //The food value of the acacia tree when it is eaten
    private static final int FOOD_VALUE = 15;
    //The age when the acacia tree is at a stage where it can be eaten
    private static final int EDIBLE_AGE = 2;
    //The probability that the acacia tree will spread to an adjacent spot on the field
    private static final double SPREADING_PROBABILITY = 0.063;

    /**
     * Create a new acacia tree and place it on the field
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Acacia(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * @param field The simulation field.
     * @param location Location to create the new acacia tree.
     * @return The new acacia tree
     */
    protected Plant newPlantObject(Field field, Location location) {
        return new Acacia(field, location);
    }

    /**
     * @return The acacia tree's max age
     */
    protected int getMAX_AGE() {
        return MAX_AGE;
    }

    /**
     * @return The acacia tree's food value
     */
    protected int getFOOD_VALUE() {
        return FOOD_VALUE;
    }

    /**
     * @return The acacia tree's edible age
     */
    protected int getEDIBLE_AGE() {
        return EDIBLE_AGE;
    }

    /**
     * @return The acacia tree's spreading probability
     */
    protected double getSPREADING_PROBABILITY() {
        return SPREADING_PROBABILITY;
    }
}
