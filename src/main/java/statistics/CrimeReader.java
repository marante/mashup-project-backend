package statistics;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created by kemkoi on 10/18/16.
 */
public class CrimeReader {

    public LinkedList<Crime> getCrimeElements(String crimesByRegion) {
        URL url = null;
        LinkedList<Crime> crimeList = new LinkedList<Crime>();
        String responseJson = "";
        Gson gson = new Gson();
        try {
            url = new URL("http://api.scb.se/OV0104/v1/doris/sv/ssd/START/BE/BE0101/BE0101A/BefolkningNy");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String jsonText = "{\n" +
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

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            OutputStream os = httpConnection.getOutputStream();
            os.write(jsonText.getBytes());
            os.flush();
            os.close();

            int responseCode = httpConnection.getResponseCode();
            System.out.println(responseCode);

            if (responseCode == httpConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
                String input;
                StringBuffer reponse = new StringBuffer();

                while ((input = br.readLine()) != null) {
                    reponse.append(input);
                }
                br.close();
                responseJson = reponse.toString();
            }

            JsonObject obj = new JsonParser().parse(responseJson).getAsJsonObject();


            String[] parts;
            int population;
            String region;

            for (int i = 0; i < 21; i++) {
                parts = obj.getAsJsonArray("data").get(i).toString().split(Pattern.quote("\""));
                System.out.println(parts);
                population = Integer.parseInt(parts[9]);
                region = parts[3];
                Crime crime = new Crime();
                crime.setPopulation(population);

                // Need to create method for showing all regions at same time.
                // One region works, if you add "crimeList.add(crime);" to the case".
                switch (region) {
                    case "01":
                        if(crimesByRegion.equals("Stockholm")) {
                            crime.setRegion("Stockholm");
                            crimeList.add(crime);
                            break;
                        }
                        break;

                    case "03":
                        if(crimesByRegion.equals("Uppsala")) {
                            crime.setRegion("Uppsala");
                            break;
                        }
                        break;

                    case "04":
                        if(crimesByRegion.equals("Södermanland")) {
                            crime.setRegion("Södermanland");
                            break;
                        }
                        break;

                    case "05":
                        if(crimesByRegion.equals("Östergötland")) {
                            crime.setRegion("Östergötland");
                            break;
                        }
                        break;

                    case "06":
                        if(crimesByRegion.equals("Jönköping")) {
                            crime.setRegion("Jönköping");
                            break;
                        }
                        break;

                    case "07":
                        if(crimesByRegion.equals("Kronoberg")) {
                            crime.setRegion("Kronoberg");
                            break;
                        }
                        break;

                    case "08":
                        if(crimesByRegion.equals("Kalmar")) {
                            crime.setRegion("Kalmar");
                            break;
                        }
                        break;
                    case "09":
                        if(crimesByRegion.equals("Gotland")) {
                            crime.setRegion("Gotland");
                            break;
                        }
                        break;

                    case "10":
                        if(crimesByRegion.equals("Blekinge")) {
                            crime.setRegion("Blekinge");
                            break;
                        }
                        break;

                    case "12":
                        if(crimesByRegion.equals("Skåne")) {
                            crime.setRegion("Skåne");
                            break;
                        }
                        break;

                    case "13":
                        if(crimesByRegion.equals("Halland")) {
                            crime.setRegion("Halland");
                            break;
                        }
                        break;

                    case "14":
                        if(crimesByRegion.equals("Västra Götaland")) {
                            crime.setRegion("Västra Götaland");
                            break;
                        }
                        break;

                    case "17":
                        if(crimesByRegion.equals("Värmland")) {
                            crime.setRegion("Värmland");
                            break;
                        }
                        break;

                    case "18":
                        if(crimesByRegion.equals("Örebro")) {
                            crime.setRegion("Örebro");
                            break;
                        }
                        break;

                    case "19":
                        if(crimesByRegion.equals("Västmanland")) {
                            crime.setRegion("Västmanland");
                            break;
                        }
                        break;

                    case "20":
                        if(crimesByRegion.equals("Dalarna")) {
                            crime.setRegion("Dalarna");
                            break;
                        }
                        break;

                    case "21":
                        if(crimesByRegion.equals("Gävleborg")) {
                            crime.setRegion("Gävleborg");
                            break;
                        }
                        break;

                    case "22":
                        if(crimesByRegion.equals("Västernorrland")) {
                            crime.setRegion("Västernorrland");
                            break;
                        }
                        break;

                    case "23":
                        if(crimesByRegion.equals("Jämtland")) {
                            crime.setRegion("Jämtland");
                            break;
                        }
                    case "24":
                        if(crimesByRegion.equals("Västerbotten")) {
                            crime.setRegion("Västerbotten");
                            break;
                        }
                        break;

                    case "25":
                        if(crimesByRegion.equals("Norrbotten")) {
                            crime.setRegion("Norrbotten");
                            break;
                        }
                        break;

                }
                crimeList.add(crime);
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
        return crimeList;
    }
}
