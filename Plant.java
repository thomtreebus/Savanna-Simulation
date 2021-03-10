import java.util.List;
import java.util.Random;
/**
 * A class representing shared characteristics of plants in the simulation.
 * A plant can grow, spread to neighbouring locations, and die
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public abstract class Plant extends Species {

    //A shared random number generator to control the plant spreading
    private static final Random rand = Randomizer.getRandom();
    //All plants can be eaten by animals.
    private static final boolean EDIBLE = true;
    /**
     * Create a new plant at a location in the field
     * @param field The field currenlty occupied
     * @param location The location within the field
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        setLocation(location);
    }

    /**
     * A plant has a probability of spreading to an adjacent location in the simulation
     * @param newPlants A list of new plants
     */
    protected void spread(List<Species> newPlants) {
        if(rand.nextDouble() <= getSPREADING_PROBABILITY()) {
            Field field = getField();
            //Get a list of free adjacent locations and spread to them.
            List<Location> free = field.getFreeAdjacentLocations(getLocation());
            for (int i = 0; i < free.size(); i++) {
                Location location = free.remove(0);
                Plant newPlant = newPlantObject(field, location);
                newPlants.add(newPlant);
            }
        }
    }

    /**
     * This is what the plant does when the simulation is running.
     * It ages and spreads to new locations.
     * @param newPlants A list of new plants
     */
    public void act(List<Species> newPlants) {
        incrementAge();
        //Plants grow twice as fast when it's raining.
        if(Simulator.weather.isRaining()) {
            incrementAge();
        }
        if(age <= getMAX_AGE()) {
            spread(newPlants);
        }

    }

    //abstract methods

    /**
     * Creates a new animal object
     * @param field The simulation field.
     * @param location Location to create the new plant.
     * @return A new plant object
     */
    protected abstract Plant newPlantObject(Field field, Location location);

    /**
     * @return The plant's max age
     */
    protected abstract int getMAX_AGE();

    /**
     * @return The plant's food value
     */
    protected abstract int getFOOD_VALUE();

    /**
     * @return The edible stage of the plant
     */
    protected abstract int getEDIBLE_AGE();

    /**
     * @return The proability that the plant will spread to a neighbouring cell
     */
    protected abstract double getSPREADING_PROBABILITY();

    /**
     * @return Whether a plant can be eaten or not.
     */
    protected boolean isEDIBLE() {
        return EDIBLE;
    }

}
