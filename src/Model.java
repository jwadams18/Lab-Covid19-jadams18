//    James Adams
//    Lab-COVID-jadams18
//    Model.java

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        this.loadCounties();
        this.loadVirusData();
    }

    public HashMap<Integer, County> countiesList = new HashMap<>();


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

                //Splits the line of coordinate pairs by spaces then commas
                //This allows for each coordinate pair to be store
                String[] coordPairs = coordsLine.split(" ");
                coords = new Coord[coordPairs.length];
                int counter = 0;
                for (String coordPair : coordPairs) {
                    String[] coordinate = coordPair.split(",");
                    Coord newCordinate = new Coord((1900 + Double.parseDouble(coordinate[0]) * 15), 1250 - Double.parseDouble(coordinate[1]) * 25);
                    coords[counter] = newCordinate;
                    counter++;
                }
                //At this point we have an array of Coord type objects which can be used later to draw the outlines
                //Now using this we need to handle the FIPSID and population lines to then create a county object
//                countiesList.add(new County(Integer.parseInt(fipsId), Integer.parseInt(population), coords));

                //Used to generate random color each new state based the change in FIPSID
                if (fipsId - previousFipsID >= 650) {
                    Random rand = new Random();
                    color = new Color(rand.nextInt(255), rand.nextInt(255) + 1, rand.nextInt(255) + 1, rand.nextInt(255) + 1);
                    previousFipsID = fipsId;
                }

                countiesList.put(fipsId, new County(fipsId, population, coords, color));
//                fipsId = Integer.parseInt();
//                population = Integer.parseInt(br.readLine());


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

        System.out.println("[SUCCESS] Loaded " + countiesList.size() + " counties' virus data");

    }

    public void storeEntry(String lineOfData) {
        String[] entries = lineOfData.split(",");
        County county = null;
        //Handles New York City not having a FIPS ID in the data we used
        if (entries[1].equals("New York City")) {
            county = countiesList.get(36061);
        }
        //Removes bad data that doesnt have a FIPS id
        if (entries[3].length() == 0) {
            System.err.println("[SKIPPED - Unknown FIPS] " + lineOfData);
            return;
        }
        //Removes Hawaii/Alaska since we have not drawn them, or loaded from census data
        if (entries[3].substring(0, 2).equals("15") || entries[3].substring(0, 2).equals("02")) {
            System.err.println("[SKIPPED - Alaska/Hawaii] " + lineOfData);
            return;
        }

        //Should be all the cases?
        county = countiesList.get(Integer.parseInt(entries[3]));
        Map<String, Integer> countyMap = county.getCases();
        countyMap.put(entries[1], Integer.parseInt(entries[4]));

        county.getDeaths().put(entries[1], Integer.parseInt(entries[5]));
    }
}


