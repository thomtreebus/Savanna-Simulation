import java.util.List;
import java.util.Random;
import java.util.Arrays;

/**
 * A simple model of a cheetah.
 * Cheetahs age, move, eat prey, and die.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Cheetah extends Predator
{
    // Characteristics shared by all cheetahs (class variables).

    // The age at which a Cheetah can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a Cheetah can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a Cheetah breeding.
    private static final double BREEDING_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    //The initial food level of the Cheetah.
    private static final int INITIAL_FOOD_LEVEL = 30;
    //The maximum food level, once this value is reached the Cheetah can't eat more.
    private static final int MAX_FOOD_LEVEL = 45;
    //The cheetah is diurnal which means it is active during the day and sleeps at night.
    private static final boolean DIURNAL = true;
    //A list of species that are part of the cheetah's diet.
    private static final List<String> EDIBLE_SPECIES = Arrays.asList("Gazelle", "Zebra");
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();


    /**
     * Create a Cheetah. A Cheetah be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the Cheetah will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cheetah(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE/2);
            foodLevel = rand.nextInt(INITIAL_FOOD_LEVEL);
        }
        else {
            age = 0;
            foodLevel = INITIAL_FOOD_LEVEL;
        }
    }

    /**
     *
     * @param randomAge True if the Cheetah is created with a random age,
     *                  False if the new Cheetah is aged 0.
     * @param field The simulation field.
     * @param location Location to create the new Cheetah.
     * @return A new cheetah object with age 0.
     */
    protected Animal newAnimalObject(boolean randomAge, Field field, Location location) {
        return new Cheetah(false, field, location);
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
     * @return The initial food level when the Lion is created
     */
    protected int getINITIAL_FOOD_LEVEL()
    {
        return INITIAL_FOOD_LEVEL;
    }

    /**
     * @return The lion's sleep pattern.
     */
    protected boolean isDIURNAL()
    {
        return DIURNAL;
    }

    /**
     * @return The list of species that the cheetah can eat.
     */
    protected List<String> getEDIBLE_SPECIES() {
        return EDIBLE_SPECIES;
    }
}

