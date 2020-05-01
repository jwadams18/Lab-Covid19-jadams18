//    James Adams
//    Lab-COVID-jadams18
//    Model.java

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Model {

    public static void main(String[] args) {
        Model m = new Model();
        m.loadCounties();
        m.loadVirusData();
    }

    public Model() {
    }

    public boolean printDebug = true;
    public int currentDisplay = 0; // 0 - Cases, 1 - Deaths
    public HashMap<Integer, County> countiesList = new HashMap<>();
    public String firstDate = "2020-01-21";
    public String recentDate;
    public String relativeDate;
    public Date mostRecentDate, currentDate;
    public int numDays, skippedUnknown = 0, skippedHIAL = 0;
    public JPanel viewPanel;


    //These methods can be private since the latest date in the data should only be changed when loading in data, not by guiControls
    private void setRecentDate(String rd) {
        this.recentDate = rd;
    }

    private void setMostRecentDate(Date mrd) {
        this.mostRecentDate = mrd;
    }

    /**
     * Uses the dates store when loading data to calculate the number of days, which will be used to set the max value of the slider on the GUI
     */
    public void calcNumDays() {
        try {
            numDays = (int) ChronoUnit.DAYS.between(new SimpleDateFormat("yyyy-MM-dd").parse(firstDate).toInstant(), new SimpleDateFormat("yyyy-MM-dd").parse(recentDate).toInstant()) + 1;
        } catch (ParseException e) {
            System.err.println("[ERROR] The number of days could not be calculated.");
        }

    }

    /**
     * Using the census data this will load the counties into a county object and draw
     */
    public void loadCounties() {
        Path filePath = Paths.get("countyCoords.txt");
        File data = filePath.toFile();

        FileReader fr;
        BufferedReader br;
        Coord[] coords;

        try {
            fr = new FileReader(data);
            br = new BufferedReader(fr);

            String fipsIdString = br.readLine();
            String populationString = br.readLine();
            Integer fipsId;
            Integer population;
            String coordsLine = br.readLine();
            Integer previousFipsID = 0;
            Color color = Color.red;

            //Used for Testing
//            fw = new FileWriter("countiesPrint.txt");
//            bw = new BufferedWriter(fw);
//            pw = new PrintWriter(bw);
            while (fipsIdString != null && populationString != null && coordsLine != null) {

                fipsId = Integer.parseInt(fipsIdString);
                population = Integer.parseInt(populationString);
                Double firstLat = null, firstLong = null;

                //Splits the line of coordinate pairs by spaces then commas
                //This allows for each coordinate pair to be store
                String[] coordPairs = coordsLine.split(" ");
                coords = new Coord[coordPairs.length];
                int counter = 0;
                double currentLat, currentLong;
                double deltaLong = 0;
                double deltaLat = 0;

                for (String coordPair : coordPairs) {
                    String[] coordinate = coordPair.split(",");
                    currentLat = Double.parseDouble(coordinate[0]);
                    currentLong = Double.parseDouble(coordinate[1]);
                    //Longitude, Latitude
                    //Finds the first lat/long and the last to then find the mid point of the county to draw the circle
                    if (firstLat == null) {
                        firstLat = currentLat;
                    }
                    if (firstLong == null) {
                        firstLong = currentLong;
                    }

                    double newDeltaLat = Math.abs(firstLat) - Math.abs(currentLat);
//                    System.out.println("(delta, newDelta) "+deltaLat+","+newDeltaLat+" "+(newDeltaLat > deltaLat));
                    if (newDeltaLat > deltaLat) {
                        deltaLat = newDeltaLat;
                    }


                    double newDeltaLong = Math.abs(firstLong) - Math.abs(currentLong);
                    if (newDeltaLong > deltaLong) {
                        deltaLong = newDeltaLong;
                    }

                    Coord newCordinate = new Coord((1900 + currentLat * 15), 1400 - currentLong * 25);
                    coords[counter] = newCordinate;
                    counter++;
                }
                Coord midpoint = new Coord(firstLong + (deltaLong / 2), firstLat + (deltaLat / 2));
//               System.out.println(midpoint.toString());
                //At this point we have an array of Coord type objects which can be used later to draw the outlines
                //Now using this we need to handle the FIPSID and population lines to then create a county object
//                countiesList.add(new County(Integer.parseInt(fipsId), Integer.parseInt(population), coords));

                //Used to generate random color each new state based the change in FIPSID
                if (fipsId - previousFipsID >= 650) {
                    Random rand = new Random();
                    color = new Color(rand.nextInt(255), rand.nextInt(255) + 1, rand.nextInt(255) + 1, rand.nextInt(255) + 1);
                    previousFipsID = fipsId;
                }

                countiesList.put(fipsId, new County(fipsId, population, coords, color, midpoint));

                fipsIdString = br.readLine();
                populationString = br.readLine();
                coordsLine = br.readLine();
            }

            System.out.println("[SUCCESS] Loaded " + countiesList.size() + " countries");
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Error: Issue reading file");
            System.exit(-1);
        }
    }

    /**
     * Using the NYTimes data from the github link, this will load the cases/deaths per county and assign these values to the corresponding county
     */
    public void loadVirusData() {

        String link = "https://raw.githubusercontent.com/nytimes/covid-19-data/master/us-counties.csv";
        URL githubUrl;
        HttpURLConnection githubHTTP;
        InputStream dataStream;
        BufferedReader br;
        County county = null;
        String lineOfData = null;
        String[] entries;
        int counter = 0;

        try {
            githubUrl = new URL(link);
            githubHTTP = (HttpURLConnection) githubUrl.openConnection();
            dataStream = githubHTTP.getInputStream();
            br = new BufferedReader(new InputStreamReader(dataStream, StandardCharsets.UTF_8));

            br.readLine(); //Absorbs the column headings, so the line of data will be data
            lineOfData = br.readLine();

            while (lineOfData != null) {

                storeEntry(lineOfData);
                lineOfData = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (printDebug) {
            System.err.println("[WARNING] Skipped " + skippedUnknown + " lines of data due to unknown FIPS");
            System.err.println("[WARNING] Skipped " + skippedHIAL + " lines of data containing entries from Hawaii/Alaska");
            System.out.println("[SUCCESS] Loaded " + countiesList.size() + " counties' virus data");
            System.out.println("[DEBUG] First date: " + firstDate);
            System.out.println("[DEBUG] Most recent date: " + mostRecentDate.toInstant());
        }

    }

    /**
     * This does the actual storage in the HashMaps for each county
     *
     * @param lineOfData the line read in from the github file
     */
    public void storeEntry(String lineOfData) {
        String[] entries = lineOfData.split(",");
        County county = null;
        //Handles New York City not having a FIPS ID in the data we used
        if (entries[1].equals("New York City")) {
            county = countiesList.get(36061);
            Map<String, Integer> countyMap = county.getCases();
            countyMap.put(entries[0], Integer.parseInt(entries[4]));

            county.getDeaths().put(entries[0], Integer.parseInt(entries[5]));
            return;
        }
        //Removes bad data that doesnt have a FIPS id
        if (entries[3].length() == 0) {
            skippedUnknown++;
            return;
        }
        //Removes Hawaii/Alaska since we have not drawn them, or loaded from census data
        if (entries[3].substring(0, 2).equals("15") || entries[3].substring(0, 2).equals("02")) {
            skippedHIAL++;
            return;
        }


        //Handled all special cases now loading as expected
        county = countiesList.get(Integer.parseInt(entries[3]));
        Map<String, Integer> countyMap = county.getCases();
        countyMap.put(entries[0], Integer.parseInt(entries[4]));

        county.getDeaths().put(entries[0], Integer.parseInt(entries[5]));
        setRecentDate(entries[0]);
        boolean isLater;
        //Tracking the most recent date
        try {
            mostRecentDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
            currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(recentDate);

            isLater = (int) (ChronoUnit.DAYS.between(mostRecentDate.toInstant(), currentDate.toInstant())) > 0;
            if (isLater) {
                setMostRecentDate(currentDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}


