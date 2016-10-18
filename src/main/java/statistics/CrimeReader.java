package statistics;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kemkoi on 10/18/16.
 */
public class CrimeReader {

    public void getCrimeElements() {
        URL url = null;
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
                "        \"values\": [\n" +
                "          \"tot\"\n" +
                "        ]\n" +
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
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            OutputStream os = httpConnection.getOutputStream();
            os.write(jsonText.getBytes());
            os.flush();
            os.close();

            int responseCode = httpConnection.getResponseCode();
            System.out.println(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                String input;
                StringBuffer response = new StringBuffer();

                while ((input = br.readLine()) != null) {
                    response.append(input);
                }
                br.close();

                Crime crime = new Crime();

                String responseJson = response.toString();
                JsonObject jObject = new JsonObject();


                /*
                String responseJson = response.toString();
                JsonElement jelement = new JsonParser().parse(responseJson);
                JsonObject jobject = jelement.getAsJsonObject();
                jobject = jobject.getAsJsonObject("data");
                JsonArray jarray = jobject.getAsJsonArray("key");
                JsonArray jarrayValue = jobject.getAsJsonArray("values");
                jobject = jarray.get(0).getAsJsonObject();
                crime.setRegion(jobject.get(""));
                jobject = jarrayValue.get(0).getAsJsonObject();
                String result = jobject.get("values").toString();
                */


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CrimeReader cr = new CrimeReader();
        cr.getCrimeElements();
    }
}
