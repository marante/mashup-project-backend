package statistics;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import news.FeedReader;
import news.Regions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created by kemkoi on 10/18/16.
 */
public class CrimeReader {

    public LinkedList<Crime> getCrimeElements(String selectedRegion) {
        URL url = null;
        LinkedList<Crime> crimeList = new LinkedList();
        String responseJson = "";
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
            double population;
            String region;

            for (int i = 0; i < 21; i++) {
                parts = obj.getAsJsonArray("data").get(i).toString().split(Pattern.quote("\""));
                population = Double.parseDouble(parts[9]);
                region = parts[3];
                Crime crime = new Crime();
                crime.setPopulation(population);

                // Need to create method for showing all regions at same time.
                // One region works, if you add "crimeList.add(crime);" to the case".
                switch (region) {
                    case "01":
                        calculateCrimePerCapita(selectedRegion, "stockholm", population, crime);
                        crime.setRegion("stockholm");
                        break;

                    case "03":
                        calculateCrimePerCapita(selectedRegion, "uppsala", population, crime);
                        crime.setRegion("uppsala");
                        break;

                    case "04":
                        calculateCrimePerCapita(selectedRegion, "sodermanland", population, crime);
                        crime.setRegion("sodermanland");
                        break;

                    case "05":
                        calculateCrimePerCapita(selectedRegion, "ostergotland", population, crime);
                        crime.setRegion("ostergotland");
                        break;

                    case "06":
                        calculateCrimePerCapita(selectedRegion, "jonkoping", population, crime);
                        crime.setRegion("jonkoping");
                        break;

                    case "07":
                        calculateCrimePerCapita(selectedRegion, "kronoberg", population, crime);
                        crime.setRegion("kronoberg");
                        break;

                    case "08":
                        calculateCrimePerCapita(selectedRegion, "kalmar", population, crime);
                        crime.setRegion("kalmar");
                        break;

                    case "09":
                        calculateCrimePerCapita(selectedRegion, "gotland", population, crime);
                        crime.setRegion("gotland");
                        break;

                    case "10":
                        calculateCrimePerCapita(selectedRegion, "blekinge", population, crime);
                        crime.setRegion("blekinge");
                        break;

                    case "12":
                        calculateCrimePerCapita(selectedRegion, "skane", population, crime);
                        crime.setRegion("skane");
                        break;

                    case "13":
                        calculateCrimePerCapita(selectedRegion, "halland", population, crime);
                        crime.setRegion("halland");
                        break;

                    case "14":
                        calculateCrimePerCapita(selectedRegion, "vastra gotaland", population, crime);
                        crime.setRegion("vastra gotaland");
                        break;

                    case "17":
                        calculateCrimePerCapita(selectedRegion, "varmland", population, crime);
                        crime.setRegion("varmland");
                        break;

                    case "18":
                        calculateCrimePerCapita(selectedRegion, "orebro", population, crime);
                        crime.setRegion("orebro");
                        break;

                    case "19":
                        calculateCrimePerCapita(selectedRegion, "vastmanland", population, crime);
                        crime.setRegion("vastmanland");
                        break;

                    case "20":
                        calculateCrimePerCapita(selectedRegion, "dalarna", population, crime);
                        crime.setRegion("dalarna");
                        break;

                    case "21":
                        calculateCrimePerCapita(selectedRegion, "gavleborg", population, crime);
                        crime.setRegion("gavleborg");
                        break;

                    case "22":
                        calculateCrimePerCapita(selectedRegion, "vasternorrland", population, crime);
                        crime.setRegion("vasternorrland");
                        break;

                    case "23":
                        calculateCrimePerCapita(selectedRegion, "jamtland", population, crime);
                        crime.setRegion("jamtland");
                        break;

                    case "24":
                        calculateCrimePerCapita(selectedRegion, "vasterbotten", population, crime);
                        crime.setRegion("vasterbotten");
                        break;

                    case "25":
                        calculateCrimePerCapita(selectedRegion, "norrbotten", population, crime);
                        crime.setRegion("norrbotten");
                        break;

                }
                crimeList.add(crime);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return crimeList;
    }


    public Crime getCrimeByRegion(LinkedList<Crime> crimeList, String region) {


        for (int i = 0; i < crimeList.size(); i++) {
            if (crimeList.get(i).getRegion().equals(region)) {
                return crimeList.get(i);
            }
        }

        return null;
    }

    private void calculateCrimePerCapita(String selectedRegion, String region, double population, Crime crime) {
        if(selectedRegion.equals(region)) {
            FeedReader feedReader = new FeedReader();
            Regions regions = new Regions();
            try {
                String regionUrl = regions.fetchRegion(selectedRegion);
                double amountOfEvents = feedReader.getAmountOfEvents(regionUrl);
                DecimalFormat df = new DecimalFormat("#.#####");
                double crimePerCapita =  (amountOfEvents /  population) * 100;
                crime.setCrimePerCapita(df.format(crimePerCapita));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
