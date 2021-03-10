import java.util.List;
import java.util.Random;

/**
 * An abstract class representing shared characteristics of all species in the simulation.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public abstract class Species {
    //Characteristics shared by all species
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;


    // Whether the species is alive or not.
    private boolean alive;
    //The species' age
    protected int age;

    /**
     * Create a new species (animal/plant)
     * @param field The current field
     * @param location The location on the field.
     */
    public Species(Field field, Location location) {
        this.field = field;
        setLocation(location);
        alive = true;
        age = 0;
    }

    /**
     * Increases the species age by 1.
     */
    protected void incrementAge()
    {
        age++;
        if(age>getMAX_AGE()) {
            setDead();
        }
    }
    /**
     * Check whether the animal/plant is alive or not.
     * @return true if the animal/plant is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal/plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Place the species at the new location in the given field.
     * @param newLocation The species' new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    /**
     * Return the species' location.
     * @return The species' location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Return the species' field.
     * @return The species' field.
     */
    protected Field getField()
    {
        return field;
    }


    //Abstract methods
    /**
     * Make this species act - animals hunt, sleep, eat
     *                       - plants grow
     * @param newSpecies A list to receive newly born animals.
     */
    abstract public void act(List<Species> newSpecies);

    /**
     *  @return the species' max age.
     */
    protected abstract int getMAX_AGE();

    /**
     * @return the species' food value.
     */
    protected abstract int getFOOD_VALUE();
    /**
     * Check if a species can be eaten by other species or not.
     * @return If the species can be eaten by other species.
     */
    protected abstract boolean isEDIBLE();

}
