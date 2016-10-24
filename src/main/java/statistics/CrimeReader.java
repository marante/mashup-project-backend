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
            StatisticRegions allRegions = new StatisticRegions();
            double population;
            String crimePerCapita;
            String region;
            Crime crime;
            FeedReader feedReader = new FeedReader();
            Regions regions = new Regions();

            for (int i = 0; i < 21; i++) {

                parts = obj.getAsJsonArray("data").get(i).toString().split(Pattern.quote("\""));
                region = parts[3];

                if (selectedRegion.equals("hela-sverige")) {
                    crime = new Crime();
                    population = Double.parseDouble(parts[9]);
                    region = allRegions.fetchRegion(region);
                    crimePerCapita = getCrimePerCapita(selectedRegion, population, regions, feedReader);
                    crime.setPopulation(population);
                    crime.setRegion(region);
                    crime.setCrimePerCapita(crimePerCapita);
                    crimeList.add(crime);
                } else {
                    if (allRegions.fetchRegion(region).equals(selectedRegion)) {
                        crime = new Crime();
                        population = Double.parseDouble(parts[9]);
                        region = selectedRegion;
                        crimePerCapita = getCrimePerCapita(selectedRegion, population, regions, feedReader);
                        crime.setPopulation(population);
                        crime.setRegion(region);
                        crime.setCrimePerCapita(crimePerCapita);
                        crimeList.add(crime);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return crimeList;
    }

    private String getCrimePerCapita(String selectedRegion, double population, Regions regions, FeedReader feedReader) {

        double amountOfEvents;
        String crimePerCapita = "";

        try {
            String regionUrl = regions.fetchRegion(selectedRegion);
            amountOfEvents = feedReader.getAmountOfEvents(regionUrl);
            DecimalFormat df = new DecimalFormat("#.#####");
            crimePerCapita = df.format((amountOfEvents / population) * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return crimePerCapita;
    }
}
