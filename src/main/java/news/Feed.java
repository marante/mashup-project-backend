package news;

/**
 * Class som fungerar som ett brott från polisens rss feed.
 */
public class Feed {

    private String crimeType;
    private String link;
    private String description;
    private String guid;
    private String pubDate;
    private String location;
    private String crimeDate;
    private String crimeAddress;
    private int crimeNumber;

    /**
     * Metod som sätter ett numret av brottet brottet till det specifierade värdet.
     * @param crimeNumber värdet som ska sättas till crimeNumber.
     */
    public void setCrimeNumber(int crimeNumber) {
        this.crimeNumber = crimeNumber;
    }

    /**
     * Metod som sätter addressen av brottet till det specifierade värdet.
     * @param crimeAddress värdet som ska sättas till crimeAddress.
     */
    public void setCrimeAddress(String crimeAddress) {
        this.crimeAddress = crimeAddress;
    }

    /**
     * Metod som sätter datumet för brottet till det specifierade värdet.
     * @param crimeDate värdet som ska sättas till crimeAddress.
     */
    public void setCrimeDate(String crimeDate) {
        this.crimeDate = crimeDate;
    }

    /**
     * Metod som sätter platsen för brottet till det specifierade värdet.
     * @param location värdet som ska sättas till location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Metod som returnerar brottstypen.
     * @return returnerar crimeType.
     */
    public String getCrimeType() {
        return crimeType;
    }

    /**
     * Metod som sätter brottstypen till det specifierade värdet.
     * @param crimeType värdet som ska sättas till crimeType.
     */
    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    /**
     * Metod som returnerar länken till ett brott.
     * @return länken som returneras.
     */
    public String getLink() {
        return link;
    }

    /**
     * Metod som sätter länken till ett brott.
     * @param link länken som brottet sätts till.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Metod som skickar tillbaka beskrivningen av ett brott.
     * @return Skickar tillbaka beskrivningen.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metod som sätter beskrivningen för ett brott.
     * @param description beskrivningen som sätts för brottet.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Metod som hämtar GUID för ett brott.
     * @return GUID som hämtas för brottet.
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Metod som sätter GUID för ett brott.
     * @param guid GUID som sätts för brottet.
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * Metod som hämtar publiceringsdatumet för brottsartikeln.
     * @return publiceringsdatumet som skickas tillbaka.
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * Metod som sätter publiceringsdatumet för ett specifikt brott.
     * @param pubDate publiceringsdatumet som sätts för brottet.
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * toString metod som skriver ut ett Feed objekt i textformat.
     * @return skickar tillbaka en sträng med alla värden objektet har.
     */
    public String toString() {

        return "Titel: " + getCrimeType() + "\n" +
                "Link: " + getLink() + "\n" +
                "Description: " + getDescription() + "\n" +
                "GUID: " + getGuid() + "\n" +
                "Pubdate: " + getPubDate() + "\n" +
                "Location:" + this.location + "\n" +
                "Crimedate: " + this.crimeDate + "\n" +
                "Crimeaddress: " + this.crimeAddress + "\n" +
                "Crimenumber " + this.crimeNumber + "\n" +
                "\n" + "------------------" + "\n";
    }
}