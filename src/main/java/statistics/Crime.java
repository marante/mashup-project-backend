package statistics;

/**
 * Created by Victor on 2016-10-18.
 */
public class Crime {

    private String region;
    private String crimePerCapita;
    private double population;

    public String getCrimePerCapita() {
        return crimePerCapita;
    }

    public void setCrimePerCapita(String crimePerCapita) {
        this.crimePerCapita = crimePerCapita;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }
}
