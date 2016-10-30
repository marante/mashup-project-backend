package statistics;

/**
 * Class som fungerar som statistik för en specifik region från statistiska centralbyrån.
 */
public class Crime {

    private String region;
    private String crimePerCapita;
    private double population;

    /**
     * Metod som skickar tillbaka crimePerCapita.
     * @return crimePerCapita som skickas tillbaka.
     */
    public String getCrimePerCapita() {
        return crimePerCapita;
    }

    /**
     * Metod som sätter crimePerCapita till det värdet som väljs.
     * @param crimePerCapita värdet som väljs för att sätta crimePerCapita.
     */
    public void setCrimePerCapita(String crimePerCapita) {
        this.crimePerCapita = crimePerCapita;
    }

    /**
     * Metod som skickar tillbaka den region som statistiken motsvara för.
     * @return regionen som statistiken motsvarar för.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Metod som lägger till ett nummer innan regionens namn.
     * @param number numret som läggs till.
     */
    public void addNumberToRegion(int number) {
        this.region = (number + 1) + ": " + this.region;
    }

    /**
     * Metod som sätter regionen till ett specifikt värde som väljs.
     * @param region Det värde som väljs och sätts till regionen.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Metod som skickar tillbaka invånarantalet för en regions statistik.
     * @return invånarantalet som skickas tillbaka.
     */
    public double getPopulation() {
        return population;
    }

    /**
     * Metod som sätter invånarantalet till ett specifikt värde för en regions statistik.
     * @param population invånarantalet som sätts.
     */
    public void setPopulation(double population) {
        this.population = population;
    }

    /**
     * Metod som tar bort nummer innan regionens namn.
     * @param number numret som representerar hur man ska ta bort numret från strängen.
     */
    public void removeNumberFromRegion(int number) {
        //Om det är ett nummer med mer än två siffror så måste vi ta bort fyra första bokstäverna,
        //annars tar vi bara bort tre första bokstäverna.
        if((number + 1) > 9) {
            this.region = region.substring(4);
        } else {
            this.region = region.substring(3);
        }

    }
}
