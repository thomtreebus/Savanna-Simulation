import java.util.Random;
/**
 * A model of the weather in the simulation.
 * Different types of weather can occur in the simulation.
 * Each weathertype causes certain animals to behave differently
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Weather {

    //Boolean for each weather type
    //True means that the weather is of that type (e.g. 'rain=true' means its raining)
    private boolean raining;
    private boolean fog;
    private boolean lightning;

    //A shared random number generator to control the weather.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a weather object and set all the weather types to false.
     */
    public Weather()
    {
        this.raining = false;
        this.fog = false;
        this.lightning = false;
        updateWeather();
    }


    /**
     * Method to update the weather randomly.
     */
    public void updateWeather()
    {
        raining = rand.nextBoolean();
        fog = rand.nextBoolean();
        lightning = rand.nextBoolean();
    }

    /**
     * Check what the weather status is and return a string
     * @return A string that describes the weather status
     */
    public String getWeatherStatus()
    {
        String weatherString = "";
        if(isRaining()) {
            weatherString += " rain ";
        }
        if(isFoggy()) {
            weatherString += " fog ";
        }
        if(isLightning()) {
            weatherString += " lightning ";
        }
        return weatherString;
    }

    /**
     * @return If it currently raining or not.
     */
    public boolean isRaining()
    {
        return raining;
    }
    /**
     * @return If it is currently foggy or not.
     */
    public boolean isFoggy()
    {
        return fog;
    }

    /**
     * @return If there is currently lightning or not.
     */
    public boolean isLightning()
    {
        return lightning;
    }

}
