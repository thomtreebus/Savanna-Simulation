import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    //Prefixes for different labels.
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private final String TIME_PREFIX = "Time: ";
    private final String WEATHER_PREFIX = "Weather: ";
    private final String DISEASE_PREFIX = "Disease Deaths: ";
    //Labels for various statistics.
    private JLabel stepLabel, population, infoLabel, timeLabel, weatherLabel, populationLabel, diseaseLabel;
    private FieldView fieldView;
    //A list to store the count for each species' population
    private JList populationList;
    
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width)
    {
        stats = new FieldStats();
        colors = new LinkedHashMap<>();

        setTitle("Savanna Simulator");
        //Different labels for each statistic
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        timeLabel = new JLabel(TIME_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        weatherLabel = new JLabel(WEATHER_PREFIX, JLabel.CENTER);
        diseaseLabel = new JLabel(DISEASE_PREFIX, JLabel.CENTER);
        populationLabel = new JLabel("Populations: ", JLabel.CENTER);
        populationList = new JList();

        setLocation(100, 50);
        //Create a view of the simulation field
        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        //Create a panel containing info about the simulation
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("STATISTICS"));
        infoPanel.setPreferredSize(new Dimension(180,180));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        stepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(stepLabel);
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(timeLabel);
        weatherLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(weatherLabel);
        diseaseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(diseaseLabel);


        populationList.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel populationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        populationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //populationPanel.add(populationLabel);
        populationPanel.add(populationList);
        infoPanel.add(populationPanel);
        infoPanel.add(populationList);
        //Add the different componenets to the main GUI window
        contents.add(infoPanel, BorderLayout.WEST);
        contents.add(fieldView, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
    
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text)
    {
        infoLabel.setText(text);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     * @param time The current time
     * @param weather the current weather
     * @param disease The disease
     */
    public void showStatus(int step, Field field, int time, Weather weather, Disease disease)
    {
        if(!isVisible()) {
            setVisible(true);
        }
        timeLabel.setText(TIME_PREFIX + time + ":00");
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();

        diseaseLabel.setText(DISEASE_PREFIX + disease.getDiseaseDeaths());

        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        String [] list = stats.getPopulationDetails(field).toArray(new String[stats.getPopulationDetails(field).size()]);
        populationList.setListData(list);
        populationList.setBackground(Color.white);
        populationList.setForeground(Color.BLUE);
        populationList.setVisible(true);
        //infoPane.add(populationList);
        //population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        weatherLabel.setText(WEATHER_PREFIX + weather.getWeatherStatus());

        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
