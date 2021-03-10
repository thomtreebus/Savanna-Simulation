import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple savanna biome simulator, based on a rectangular field
 * containing animals(predators and prey) and plants.
 * 
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 200;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 135;
    // The probability that a Lion will be created in any given grid position.
    private static final double LION_CREATION_PROBABILITY = 0.015;
    // The probability that a Lion will be created in any given grid position.
    private static final double CHEETAH_CREATION_PROBABILITY = 0.015;

    // The probability that a gazelle will be created in any given grid position.
    private static final double GAZELLE_CREATION_PROBABILITY = 0.03;
    // The probability that a zebra will be created in any given grid position.
    private static final double ZEBRA_CREATION_PROBABILITY = 0.02;
    // The probability that a giraffe will be created in any given grid position.
    private static final double GIRAFFE_CREATION_PROBABILITY = 0.02;
    // The probability that grass will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.04;
    // The probability that an acacia tree will be created in any given grid position.
    private static final double ACACIA_CREATION_PROBABILITY = 0.04;

    // List of animals in the field.
    private List<Species> species;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    //The object to keep track of time in the simulation
    Time time = new Time();
    //Object to control the weather in the simulation.
    static Weather weather = new Weather();
    //Object to control disease in the simulation.
    static Disease disease = new Disease();
    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        species = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Lion.class, Color.YELLOW);
        view.setColor(Cheetah.class, Color.RED);
        view.setColor(Gazelle.class, Color.BLUE);
        view.setColor(Zebra.class, Color.DARK_GRAY);
        view.setColor(Giraffe.class, Color.MAGENTA);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Acacia.class, Color.ORANGE);

        //Update the weather
        weather.updateWeather();

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(500);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // uncomment this to run more slowly

            //Update the weather every 5 steps.
            if(step % 5 == 0) {
                weather.updateWeather();
            }
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * species
     */
    public void simulateOneStep()
    {
        step++;
        if(step % 5 == 0){
            time.incrementHour();
        }

        // Provide space for newborn species.
        List<Species> newSpecies = new ArrayList<>();

        // Let all species act.
        for(Iterator<Species> it = species.iterator(); it.hasNext(); ) {
            Species s = it.next();
            if(!s.isAlive()) {
                it.remove();
            }else {
                s.act(newSpecies);
            }
        }

        // Add the newly born species to the main lists.
        species.addAll(newSpecies);

        view.showStatus(step, field, time.getHour(), weather, disease);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        species.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field, time.getHour(), weather, disease);
    }

    /**
     * Randomly populate the field with preadators and prey.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= GIRAFFE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Giraffe giraffe = new Giraffe(true, field, location);
                    species.add(giraffe);
                }
                else if(rand.nextDouble() <= ZEBRA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Zebra zebra = new Zebra(true, field, location);
                    species.add(zebra);
                }
                else if(rand.nextDouble() <= GAZELLE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Gazelle gazelle = new Gazelle(true, field, location);
                    species.add(gazelle);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(field, location);
                    species.add(grass);
                }
                else if(rand.nextDouble() <= ACACIA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Acacia acacia = new Acacia(field, location);
                    species.add(acacia);
                }
                else if(rand.nextDouble() <= LION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lion lion = new Lion(true, field, location);
                    species.add(lion);
                }
                else if(rand.nextDouble() <= CHEETAH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Cheetah cheetah = new Cheetah(true, field, location);
                    species.add(cheetah);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    public static void main(String[] args)
    {
        //Create a simulator and run it for 500 steps.
        Simulator simulator = new Simulator();
        simulator.runLongSimulation();
    }
}
