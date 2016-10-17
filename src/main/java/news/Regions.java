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

        regionMap.put("Hela landet", "https://polisen.se/Aktuellt/Handelser/Handelser-i-hela-landet/?feed=rss");
        regionMap.put("Blekinge", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Blekinge/?feed=rss");
        regionMap.put("Dalarna", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Dalarna/?feed=rss");
        regionMap.put("Gotland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Gotland/?feed=rss");
        regionMap.put("Gävleborg", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Gavleborg/?feed=rss");
        regionMap.put("Halland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Halland/?feed=rss");
        regionMap.put("Jämtland", "https://polisen.se/Jamtland/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Jamtland/?feed=rss");
        regionMap.put("Jönköping", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Jonkopings-lan/?feed=rss");
        regionMap.put("Kalmar", "https://polisen.se/Kalmar_lan/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Kalmar-lan/?feed=rss");
        regionMap.put("Kronoberg", "https://polisen.se/Kronoberg/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Kronoberg/?feed=rss");
        regionMap.put("Norrbotten", "https://polisen.se/Norrbotten/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Norrbotten/?feed=rss");
        regionMap.put("Skåne", "https://polisen.se/Skane/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Skane/?feed=rss");
        regionMap.put("Stockholm", "https://polisen.se/Stockholms_lan/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Stockholms-lan/?feed=rss");
        regionMap.put("Södermanland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Sodermanland/?feed=rss");
        regionMap.put("Uppsala", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Uppsala-lan/?feed=rss");
        regionMap.put("Värmland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Varmland/?feed=rss");
        regionMap.put("Västerbotten", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vasterbotten/?feed=rss");
        regionMap.put("Västernorrland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vasternorrland/?feed=rss");
        regionMap.put("Västmanland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vastmanland/?feed=rss");
        regionMap.put("Västra Götaland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Vastra-Gotaland/?feed=rss");
        regionMap.put("Örebro", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Orebro-lan/?feed=rss");
        regionMap.put("Östergötland", "https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Ostergotland/?feed=rss");
    }

    public String fetchRegion (String region) {

        return (String) regionMap.get(region);
    }
}
