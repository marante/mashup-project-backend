package statistics;

import java.util.HashMap;

/**
 * Class som håller reda på vilket nummer som tillhör till vilken region.
 */
public class StatisticRegions {

    private HashMap regionMap = new HashMap();

    /**
     * Constructor som sätter in alla värden som behövs för alla regioner i Sverige.
     */
    public StatisticRegions() {
        regionMap.put("01", "stockholm");
        regionMap.put("03", "uppsala");
        regionMap.put("04", "södermanland");
        regionMap.put("05", "östergötland");
        regionMap.put("06", "jönköping");
        regionMap.put("07", "kronoberg");
        regionMap.put("08", "kalmar");
        regionMap.put("09", "gotland");
        regionMap.put("10", "blekinge");
        regionMap.put("12", "skåne");
        regionMap.put("13", "halland");
        regionMap.put("14", "västra götaland");
        regionMap.put("17", "värmland");
        regionMap.put("18", "örebro");
        regionMap.put("19", "västmanland");
        regionMap.put("20", "dalarna");
        regionMap.put("21", "gävleborg");
        regionMap.put("22", "västernorrland");
        regionMap.put("23", "jämtland");
        regionMap.put("24", "västerbotten");
        regionMap.put("25", "norrbotten");
    }


    /**
     * Metod som skickar tillbaka hashmapen med alla regioner och deras nummer.
     * @return skickar tillbaka hashmapen regionMap.
     */
    public HashMap getStatisticMap() {
        return this.regionMap;
    }
}
