
import java.util.List;

/**
 * A class that controls certain behaviours exhibited by predators in the simulation.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public abstract class Predator extends Animal{

    //Predators are not eaten by other animal's
    private static final boolean EDIBLE = true;
    //Predators a food value of 0 since they are not eaten by other animal's
    private static final int FOOD_VALUE = 0;
    //Predators need to be able to see clearly in order to hunt
    private static final boolean HUNTING_VISIBILITY_REQUIRED = true;

    /**
     * Creates a new predator.
     * @param field The simulation field.
     * @param location The location where the predator is created
     */
    public Predator(Field field, Location location){
        super(field,location);
    }

    /**
     * @return Whether a prey can be eaten by other animal's or not.
     */
    protected boolean isEDIBLE()
    {
        return EDIBLE;
    }

    /**
     * @return The food value when eaten.
     */
    protected int getFOOD_VALUE()
    {
        return FOOD_VALUE;
    }

    /**
     * @return True if the predator needs to be able to see in order to hunt.
     */
    protected boolean getHUNTING_VISIBILITY_REQUIRED() {
        return HUNTING_VISIBILITY_REQUIRED;
    }
}
