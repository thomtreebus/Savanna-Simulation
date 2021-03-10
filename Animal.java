import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals in the simulation.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public abstract class Animal extends Species
{
    //The animal's gender, Male or Female
    private String gender;
    //If the animal is infected with a disease.
    private boolean infected;
    //The food level of the animal
    protected int foodLevel;
    //A shared random number generator
    private static final Random rand = Randomizer.getRandom();
    //Chance of an animal being randomly infected by disease
    private static final double RANDOM_INFECTION_CHANCE = 0.005;
    //Chance of an animal infected with a disease spreading it to other animals.
    private static final double SPREAD_INFECTION_CHANCE = 0.01;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field, location);
        setLocation(location);
        setGender();
        foodLevel = getINITIAL_FOOD_LEVEL();
    }

    /**
     * This is what an animal doe most of the time: it looks for food.
     * In the process, it might breed, die of hunger,
     * or die of old age. It can also randomly be infected with a disease,
     * or spread a disease to other animals.
     * @param newAnimals A list to return newly born animals.
     */
    public void act(List<Species> newAnimals)
    {
        incrementAge();
        randomlyContractInfection();
        infectAdjacentAnimals();
        incrementHunger();
        //The animal can only breed and find food when it is awake and still alive.
        if(isAlive() && (isAwake(Time.isDay()))) {
            //give birth to any new animals
            giveBirth(newAnimals);
            if(canFindFood()) {
                // Move towards a source of food if found.
                Location newLocation = findFood();
                if(newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }

    /**
     * Incrementing the animal's hunger by decreasing it's food level
     * If foodLevel reaches 0, then the animal dies of starvation.
     */
    protected void incrementHunger()
    {
        if(isAwake(Time.isDay())){
            foodLevel--;
            if(foodLevel <= 0) {
                setDead();
            }
        }

    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    protected void giveBirth(List<Species> newAnimals)
    {
        //Only females can give birth
        if(gender.equals("female")) {
            //New animals are born into adjacent locations
            //Get a list of adjacent free locations
            Field field = getField();
            List<Location> free = field.getFreeAdjacentLocations(getLocation());
            //Get the number of new animals to add to the field.
            int births = breed();
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location location = free.remove(0);
                Animal child = newAnimalObject(false, field, location);
                newAnimals.add(child);
            }
        }

    }

    /**
     * Generate a number representing the number of births,
     * if the animal can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBREEDING_PROBABILITY()) {
            births = rand.nextInt(getMAX_LITTER_SIZE()) + 1;
        }
        return births;
    }

    /**
     * Check if an animal can breed. The animal can only breed if:
     * They are old enough to breed
     * There is an animal in an adjacent spot of the same species
     * The two animals have the same gender.
     * @return Whether or not the animal can breed
     */
    private boolean canBreed()
    {
        boolean sameSpecies = false;
        boolean oppositeGender = false;

        if(age < getBREEDING_AGE()) {
            return false;
        }else{
            //Get a list of adjacent locations.
            Field field = getField();
            List<Location> adjacentLocations = field.adjacentAnimals(getLocation());
            //Get the animal at the current location
            Object animal = field.getObjectAt(getLocation());

            for (Location location : adjacentLocations) {
                //select the species from the adjacent location
                Object partner = field.getObjectAt(location);
                if(partner != null) {
                    //Check if the animal in the adjacent location is of the same species.
                    sameSpecies = partner.getClass().equals(animal.getClass());
                    //Check if the animals are opposite gender (males can only breed with females)
                    Animal part = (Animal) partner;
                    oppositeGender = !gender.equals(part.gender);
                }
            }
        }
        return sameSpecies && oppositeGender;
    }

    /**
     * The animal will look for other species to eat in adjacent locations.
     * @return The location where the animal found food, or null if it didn't find any food to eat.
     */
    protected Location findFood()
    {
        Field field = getField();
        //create a list of adjancent locations
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Object species = field.getObjectAt(where);
            if (species != null) {
                String speciesString = species.getClass().getName();
                //Check if the species is a possible food source for the animal
                if (getEDIBLE_SPECIES().contains(speciesString)) {
                    eat((Species) species);
                    //return the location of where the species was eaten.
                    return where;
                }
            }
        }
        return null;  
    }

    /**
     * Check if an animal is able to find food at the current step.
     * @return If an animal can find food given the weather conditions.
     */
    protected boolean canFindFood()
    {
        return !getHUNTING_VISIBILITY_REQUIRED() || !Simulator.weather.isFoggy();
    }

    /**
     * Eat another species and in return,
     * add the food value of the eaten species to the animal's food level.
     * @param target the target species that the animal will eat.
     */
    protected void eat(Species target)
    {
        if (target.isAlive() && target.isEDIBLE()) {
            //Add the food value of the species that has just been eaten to the animal's foodLevel
            foodLevel += target.getFOOD_VALUE();
            target.setDead();
        }
    }

    /**
     * Randomly assign a gender to an animal
     */
    private void setGender()
    {
        String[] genders = {"male", "female"};
        gender = genders[rand.nextInt(2)];
    }

    /**
     * Return the animal's gender
     * @return The animal's gender
     */
    protected String getGender()
    {
        return gender;
    }

    /**
     * @return If the animal is awake or not.
     */
    protected boolean isAwake(boolean isDay)
    {
        return (isDIURNAL() == isDay);
    }

    /**
     * @return If the animal is infected with disease or not.
     */
    protected boolean isInfected(){
        return infected;
    }

    /**
     * Infect the animal with disease.
     */
    protected void setInfected(){
        if(!infected){
            infected = true;
        }
    }

    /**
     * Animal's have a small chance of being randomly infected with a disease, if they get infected
     * then they will have the disease.
     */
    protected void randomlyContractInfection(){
        if(rand.nextDouble() <= RANDOM_INFECTION_CHANCE){
            infected = true;
        } 
    }

    /**
     * An animal infected with disease will have a small chance of infecting animals that are
     * located in adjacent locations in the field.
     */
    protected void infectAdjacentAnimals(){
        //Animals can only spread disease if they are infected.
        if(isInfected() && isAlive()){
            //The simulation field
            Field field = getField();
            //create a list of adjancent locations
            List<Location> adjacent = field.adjacentAnimals(getLocation());
            for (Location location : adjacent) {
                Object adjacentAnimal = field.getObjectAt(location);
                if(adjacentAnimal != null) {
                    Animal a = (Animal) adjacentAnimal;
                    //Infect the other animal
                    if(rand.nextDouble() <= SPREAD_INFECTION_CHANCE){
                        a.setInfected();
                    }
                }
            }
            //Decrease the number of steps left before the animal dies of disease
            Simulator.disease.decrementStepsRemaining();
            if(Simulator.disease.getDaysRemaning() == 0){
                setDead();
                Simulator.disease.incrementDiseaseDeaths();
            }
        }
    } 

    //Abstract methods
    /**
     * Creates a new animal object
     * @param randomAge True if the animal is created with a random age,
     *                  False if the new animal is aged 0.
     * @param field The simulation field.
     * @param location Location to create the new animal.
     * @return A new animal object
     */
    protected abstract Animal newAnimalObject(boolean randomAge, Field field, Location location);

    /**
     * @return The age when the animal can start breeding.
     */
    protected abstract int getBREEDING_AGE();

    /**
     * @return The likelihood of the animal breeding.
     */
    protected abstract double getBREEDING_PROBABILITY();

    /**
     * @return The max number of births the animal can have at once.
     */
    protected abstract int getMAX_LITTER_SIZE();

    /**
     * @return The max food value, at this value, the animal can no longer eat.
     */
    protected abstract int getMAX_FOOD_LEVEL();

    /**
     * @return The initial food level the animal has when it is created
     */
    protected abstract int getINITIAL_FOOD_LEVEL();

    /**
     * @return The animal's sleeping habit, true if it sleeps at night, false if it sleeps during the day.
     */
    protected abstract boolean isDIURNAL();

    /**
     * @return A list of species that are part of the animal's diet.
     */
    protected abstract List<String> getEDIBLE_SPECIES();

    /**
     * Check if the animal needs to be able to see clearly in order to find food
     * @return True if the animal needs clear visibility to hunt.
     */
    protected abstract boolean getHUNTING_VISIBILITY_REQUIRED();
}
