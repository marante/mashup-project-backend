package statistics;

/**
 * Class som håller JSON-post informationen som används för att få tillbaka information från
 * statistiska centralbyrån.
 */
public class JsonPost {

    private String jsonText = "{\n" +
            "  \"query\": [\n" +
            "    {\n" +
            "      \"code\": \"Region\",\n" +
            "      \"selection\": {\n" +
            "        \"filter\": \"vs:RegionLän07\",\n" +
            "        \"values\": [\n" +
            "          \"01\",\n" +
            "          \"03\",\n" +
            "          \"04\",\n" +
            "          \"05\",\n" +
            "          \"06\",\n" +
            "          \"07\",\n" +
            "          \"08\",\n" +
            "          \"09\",\n" +
            "          \"10\",\n" +
            "          \"12\",\n" +
            "          \"13\",\n" +
            "          \"14\",\n" +
            "          \"17\",\n" +
            "          \"18\",\n" +
            "          \"19\",\n" +
            "          \"20\",\n" +
            "          \"21\",\n" +
            "          \"22\",\n" +
            "          \"23\",\n" +
            "          \"24\",\n" +
            "          \"25\"\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"code\": \"Alder\",\n" +
            "      \"selection\": {\n" +
            "        \"filter\": \"vs:ÅlderTotA\",\n" +
            "        \"values\": []\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"code\": \"ContentsCode\",\n" +
            "      \"selection\": {\n" +
            "        \"filter\": \"item\",\n" +
            "        \"values\": [\n" +
            "          \"BE0101N1\"\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"code\": \"Tid\",\n" +
            "      \"selection\": {\n" +
            "        \"filter\": \"item\",\n" +
            "        \"values\": [\n" +
            "          \"2015\"\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"response\": {\n" +
            "    \"format\": \"json\"\n" +
            "  }\n" +
            "}";


    /**
     * Metod som skickar tillbaka JSON-POST texten som ska användas.
     * @return skickar tillbaka JSON-POST strängen.
     */
    public String getJsonText() {
        return this.jsonText;
    }
}
