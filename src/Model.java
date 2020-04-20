//    James Adams
//    Lab-COVID-jadams18
//    Model.java

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Model {

    public static void main(String[] args) {
        Model m = new Model();
        m.loadCounties();
    }

    public ArrayList<County> countiesList = new ArrayList<>();
    public PrintWriter pw;
    public FileWriter fw;
    public BufferedWriter bw;


    public void loadCounties() {
        Path filePath = Paths.get("countyCoords.txt");
        File data = filePath.toFile();

        FileReader fr;
        BufferedReader br;
        Coord[] coords;

        try {
            fr = new FileReader(data);
            br = new BufferedReader(fr);

            String fipsIdLine = br.readLine();
            String populationLine = br.readLine();
            String coordsLine = br.readLine();

            //Used for Testing
//            fw = new FileWriter("countiesPrint.txt");
//            bw = new BufferedWriter(fw);
//            pw = new PrintWriter(bw);

            while (fipsIdLine != null && populationLine != null && coordsLine != null) {

                //Splits the line of coordinate pairs by spaces then commas
                //This allows for each coordinate pair to be store
                String[] coordPairs = coordsLine.split(" ");
                coords = new Coord[coordPairs.length];
                int counter = 0;
                for (String coordPair : coordPairs) {
                    String[] coordinate = coordPair.split(",");
                    Coord newCordinate = new Coord(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1]));
                    coords[counter] = newCordinate;
                    counter++;
                }
                //At this point we have an array of Coord type objects which can be used later to draw the outlines
                //Now using this we need to handle the FIPSID and population lines to then create a county object
                countiesList.add(new County(Integer.parseInt(fipsIdLine), Integer.parseInt(populationLine), coords));
                fipsIdLine = br.readLine();
                populationLine = br.readLine();
                coordsLine = br.readLine();

            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Error: Issue reading file");
            System.exit(-1);
        }
        System.out.println("Success! Loaded " + countiesList.size() + " counties into memory");
    }
}
