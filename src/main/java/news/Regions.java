package news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Victor on 2016-10-14.
 */
public class Regions {

    private HashMap regionMap = new HashMap();

    public Regions() {

        regionMap.put("hela landet", "https://polisen.se/Aktuellt/Handelser/Handelser-i-hela-landet/?feed=rss");
        regionMap.put("blekinge", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Blekinge/?feed=rss");
        regionMap.put("dalarna", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Dalarna/?feed=rss");
        regionMap.put("gotland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Gotland/?feed=rss");
        regionMap.put("gavleborg", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Gavleborg/?feed=rss");
        regionMap.put("halland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Halland/?feed=rss");
        regionMap.put("jamtland", "https://polisen.se/Jamtland/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Jamtland/?feed=rss");
        regionMap.put("jonkoping", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Jonkopings-lan/?feed=rss");
        regionMap.put("kalmar", "https://polisen.se/Kalmar_lan/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Kalmar-lan/?feed=rss");
        regionMap.put("kronoberg", "https://polisen.se/Kronoberg/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Kronoberg/?feed=rss");
        regionMap.put("norrbotten", "https://polisen.se/Norrbotten/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Norrbotten/?feed=rss");
        regionMap.put("skane", "https://polisen.se/Skane/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Skane/?feed=rss");
        regionMap.put("stockholm", "https://polisen.se/Stockholms_lan/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Stockholms-lan/?feed=rss");
        regionMap.put("sodermanland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Sodermanland/?feed=rss");
        regionMap.put("uppsala", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Uppsala-lan/?feed=rss");
        regionMap.put("varmland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Varmland/?feed=rss");
        regionMap.put("vasterbotten", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vasterbotten/?feed=rss");
        regionMap.put("vasternorrland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vasternorrland/?feed=rss");
        regionMap.put("vastmanland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vastmanland/?feed=rss");
        regionMap.put("vastra gotaland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vastra-Gotaland/?feed=rss");
        regionMap.put("orebro", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Orebro-lan/?feed=rss");
        regionMap.put("ostergotland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Ostergotland/?feed=rss");
    }

    public String fetchRegion (String region) {

        return (String) regionMap.get(region);
    }
}
