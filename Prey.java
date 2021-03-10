import java.util.List;
/**
 * A class that controls certain behaviours exhibited by prey in the simulation.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public abstract class Prey extends Animal{

    //Prey are edible
    private static final boolean EDIBLE = true;
    //Prey only eat plants and therefore don't need visibility in order to find food.
    private static final boolean HUNTING_VISIBILITY_REQUIRED = false;

    /**
     * Creates a new prey.
     * @param field The simulation field.
     * @param location The location where the prey is created
     */
    public Prey(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * @return Whether a prey can be eaten by other animal's or not.
     */
    protected boolean isEDIBLE()
    {
        return EDIBLE;
    }

    /**
     * @return If the prey needs to be able to see in order to hunt for food.
     */
    protected boolean getHUNTING_VISIBILITY_REQUIRED() {
        return HUNTING_VISIBILITY_REQUIRED;
    }
}
