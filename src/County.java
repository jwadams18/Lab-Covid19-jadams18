//    James Adams
//    Lab-COVID-jadams18
//    County.java

public class County {

    private Integer FIPSID, population;
    private Coord[] coords;

    public County(Integer FIPSID, Integer population, Coord[] coords) {

        this.FIPSID = FIPSID;
        this.population = population;
        this.coords = coords;

    }

}
