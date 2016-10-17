package news;

/**
 * Created by Victor on 2016-10-14.
 */
public class Feed {

    private String title;
    private String link;
    private String description;
    private String guid;
    private String pubDate;
    private boolean isPermalink;

    public boolean isPermalink() {
        return isPermalink;
    }

    public void setPermalink(boolean permalink) {
        isPermalink = permalink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

        return "Titel: " + getTitle() + "\n" +
                "Link: " + getLink() + "\n" +
                "Description: " + getDescription() + "\n" +
                "GUID: " + getGuid() + "\n" +
                "Pubdate: " + getPubDate() + "\n" +
                "\n" + "------------------" + "\n";
    }
}