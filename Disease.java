/**
 * A class representing a disease in the simulation.
 * Animals can be infected with disease and spread it to other animals.
 * Once an animal is infected, they have a limited number of steps left before they die.
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Disease
{
    //The number of steps an animal has before they die of the disease
    private int stepsRemaining;
    //The number of animals that have died due to disease.
    private int diseaseDeaths;

    /**
     * Create a disease object.
     */
    public Disease(){
        stepsRemaining = 5;
    }

    /**
     * @return The number of steps the animal has left before they die of disease
     */
    public int getDaysRemaning(){
        return stepsRemaining;
    }

    /**
     * Reduce the number of steps the infected animal has left to live.
     */
    public void decrementStepsRemaining(){
        if(stepsRemaining > 0){
            stepsRemaining--;
        }
    }

    /**
     * Increment the number of deaths caused by disease by 1.
     */
    public void incrementDiseaseDeaths(){
        diseaseDeaths++;
    }

    /**
     * @return The number of disease deaths.
     */
    public int getDiseaseDeaths(){
        return diseaseDeaths;
    }
}
