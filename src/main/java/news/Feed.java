package news;

/**
 * Created by Victor on 2016-10-14.
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
    private boolean isPermalink;

    public boolean isPermalink() {
        return isPermalink;
    }

    public void setPermalink(boolean permalink) {
        isPermalink = permalink;
    }

    public String getCrimeAddress() {
        return crimeAddress;
    }

    public int getCrimeNumber() {
        return crimeNumber;
    }

    public void setCrimeNumber(int crimeNumber) {
        this.crimeNumber = crimeNumber;
    }

    public void setCrimeAddress(String crimeAddress) {
        this.crimeAddress = crimeAddress;
    }

    public String getLocation() {
        return this.location;
    }

    public String getCrimeDate() {
        return crimeDate;
    }

    public void setCrimeDate(String crimeDate) {
        this.crimeDate = crimeDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String toString() {

        return "Titel: " + getCrimeType() + "\n" +
                "Link: " + getLink() + "\n" +
                "Description: " + getDescription() + "\n" +
                "GUID: " + getGuid() + "\n" +
                "Pubdate: " + getPubDate() + "\n" +
                "\n" + "------------------" + "\n";
    }
}