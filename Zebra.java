import java.util.List;
import java.util.Random;
import java.util.Arrays;

/**
 * A simple model of a zebra.
 * Zebras age, move, breed, eat, and die.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Zebra extends Prey
{
    // Characteristics shared by all zebras (class variables).

    // The age at which a zebra can start to breed.
    private static final int BREEDING_AGE = 9;
    // The age to which a zebra can live.
    private static final int MAX_AGE = 120;
    // The likelihood of a zebra breeding.
    private static final double BREEDING_PROBABILITY = 0.9;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    //The food value of the zebra when it is eaten
    private static final int FOOD_VALUE = 4;
    //The food level of the zebra when it is created
    private static final int INITIAL_FOOD_LEVEL = 10;
    //The max food level, once this value is reached, the zebra can't eat more.
    private static final int MAX_FOOD_LEVEL = 25;
    //The zebra is diurnal which means it is active during the day and sleeps at night.
    private static final boolean DIURNAL = true;
    //A list of species that are part of the zebra's diet.
    private static final List<String> EDIBLE_SPECIES = Arrays.asList("Grass", "Acacia");
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new zebra. A zebra may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the zebra will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Zebra(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE/3);
            foodLevel = rand.nextInt(INITIAL_FOOD_LEVEL);
        }
        else{
            age = 0;
            foodLevel = INITIAL_FOOD_LEVEL;
        }
    }

    /**
     * Creates a new zebra
     * @param randomAge True if the zebra is created with a random age,
     *                  False if the new zebra is aged 0.
     * @param field The simulation field.
     * @param location Location to create the new zebra.
     * @return The new zebra
     */
    protected Animal newAnimalObject(boolean randomAge, Field field, Location location) {
        return new Zebra(false, field, location);
    }

    /**
     * @return The maximum age
     */
    protected int getMAX_AGE()
    {
        return MAX_AGE;
    }

    /**
     * @return The breeding age
     */
    protected int getBREEDING_AGE()
    {
        return BREEDING_AGE;
    }

    /**
     * @return The breeding probability
     */
    protected double getBREEDING_PROBABILITY()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return The maximum litter size
     */
    protected int getMAX_LITTER_SIZE()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     *
     * @return The max food value
     */
    protected int getMAX_FOOD_LEVEL()
    {
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return The initial food level when the zebra is created
     */
    protected int getINITIAL_FOOD_LEVEL() {
        return INITIAL_FOOD_LEVEL;
    }

    /**
     * @return The zebra's food value when eaten
     */
    protected int getFOOD_VALUE()
    {
        return FOOD_VALUE;
    }

    /**
     * @return The lion's sleep pattern.
     */
    protected boolean isDIURNAL()
    {
        return DIURNAL;
    }

    /**
     * @return The list of species that the zebra can eat.
     */
    protected List<String> getEDIBLE_SPECIES() {
        return EDIBLE_SPECIES;
    }
}

