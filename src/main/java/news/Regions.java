package news;

import java.util.HashMap;

/**
 * Class som håller reda på alla regioner och deras länkar till polisens RSS-feed.
 */
public class Regions {

    private HashMap regionMap = new HashMap();

    /**
     * Constructor som sätter in alla värdena som behövs i regionMap.
     */
    public Regions() {

        regionMap.put("hela-sverige", "https://polisen.se/Aktuellt/Handelser/Handelser-i-hela-landet/?feed=rss");
        regionMap.put("blekinge", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Blekinge/?feed=rss");
        regionMap.put("dalarna", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Dalarna/?feed=rss");
        regionMap.put("gotland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Gotland/?feed=rss");
        regionMap.put("gavleborg", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Gavleborg/?feed=rss");
        regionMap.put("halland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Halland/?feed=rss");
        regionMap.put("jamtland", "https://polisen.se/Jamtland/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Jamtland/?feed=rss");
        regionMap.put("jonkoping", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Jonkoping/?feed=rss");
        regionMap.put("kalmar", "https://polisen.se/Kalmar_lan/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Kalmar-lan/?feed=rss");
        regionMap.put("kronoberg", "https://polisen.se/Kronoberg/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Kronoberg/?feed=rss");
        regionMap.put("norrbotten", "https://polisen.se/Norrbotten/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Norrbotten/?feed=rss");
        regionMap.put("skane", "https://polisen.se/Skane/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Skane/?feed=rss");
        regionMap.put("stockholm", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Stockholms-lan/?feed=rss");
        regionMap.put("sodermanland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Sodermanland/?feed=rss");
        regionMap.put("uppsala", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Uppsala-lan/?feed=rss");
        regionMap.put("varmland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Varmland/?feed=rss");
        regionMap.put("vasterbotten", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Vasterbotten/?feed=rss");
        regionMap.put("vasternorrland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Vasternorrland/?feed=rss");
        regionMap.put("vastmanland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Vastmanland/?feed=rss");
        regionMap.put("vastra gotaland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Vastra-Gotaland/?feed=rss");
        regionMap.put("orebro", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Orebro-lan/?feed=rss");
        regionMap.put("ostergotland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Handelser/Lokala-RSS-listor1/Handelser-RSS---Ostergotland/?feed=rss");
    }

    /**
     * Metod som skickar tillbaka regionmapen.
     * @return regionmapen som skickas tillbaka.
     */
    public HashMap getRegionMap() {
        return this.regionMap;
    }
}
