package statistics;

import java.util.HashMap;

/**
 * Created by kemko on 2016-10-24.
 */
public class StatisticRegions {

    private HashMap regionMap = new HashMap();

    public StatisticRegions() {
        regionMap.put("01", "stockholm");
        regionMap.put("03", "uppsala");
        regionMap.put("04", "sodermanland");
        regionMap.put("05", "ostergotland");
        regionMap.put("06", "jonkoping");
        regionMap.put("07", "kronoberg");
        regionMap.put("08", "kalmar");
        regionMap.put("09", "gotland");
        regionMap.put("10", "blekinge");
        regionMap.put("12", "skane");
        regionMap.put("13", "halland");
        regionMap.put("14", "vastra gotaland");
        regionMap.put("17", "varmland");
        regionMap.put("18", "orebro");
        regionMap.put("19", "vastmanland");
        regionMap.put("20", "dalarna");
        regionMap.put("21", "gavleborg");
        regionMap.put("22", "vasternorrland");
        regionMap.put("23", "jamtland");
        regionMap.put("24", "vasterbotten");
        regionMap.put("25", "norrbotten");
    }

    public String fetchRegion (String region) {

        return (String) regionMap.get(region);
    }
}
