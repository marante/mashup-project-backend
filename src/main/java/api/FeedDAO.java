package api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

import news.Feed;
import news.Regions;
import statistics.Crime;
import statistics.JsonPost;
import statistics.StatisticRegions;

/**
 * Created by kemko on 2016-10-24.
 */
public class FeedDAO implements Runnable {
    private Thread thread;
    private HashMap hashList;
    private HashMap statisticHashList;

    @Override
    public void run() {
        try {
            //Reset the hash map and give it the new values.
            resetHashMaps();
            getFeedElements();
            getCrimeElements();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Sleep for 30 minutes.
        try {
            Thread.sleep(180000); // 1800000 = 30 minutes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void getFeedElements() throws Exception {

        Regions regions = new Regions();
        HashMap regionMap = regions.getRegionMap();
        LinkedList<Feed> feedList;
        HashMap hashList = new HashMap();

        Iterator it = regionMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().parse(new URL((String) pair.getValue()).openStream());
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");
            feedList = new LinkedList<>();

            for (int n = 0; n < nList.getLength(); n++) {

                Node nNode = nList.item(n);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement  = (Element) nNode;


                    Feed feed = new Feed();

                    feed.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
                    feed.setLink(eElement.getElementsByTagName("link").item(0).getTextContent());
                    feed.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                    feed.setGuid(eElement.getElementsByTagName("guid").item(0).getTextContent());
                    feed.setPubDate(eElement.getElementsByTagName("pubDate").item(0).getTextContent());

                    feedList.add(feed);
                }
            }

            //Creates a HashMap, puts the regions name as its key and the ArrayList with all the feed information as the value.
            //This is then added to the Main feedRegionList which will later be sent over to the storage class.
            hashList.put(pair.getKey(), feedList);

            it.remove(); //To avoid ConcurrentModificationException - Dunno om detta är nödvändigt, lösning från stackoverflow.
        }

        this.hashList = hashList;
    }

    public void getCrimeElements() {

        StatisticRegions allRegions = new StatisticRegions();
        HashMap statisticMap = allRegions.getStatisticMap();

        HashMap hashList = new HashMap();





            URL url = null;
            LinkedList<Crime> crimeList;
            String responseJson = "";
            try {
                url = new URL("http://api.scb.se/OV0104/v1/doris/sv/ssd/START/BE/BE0101/BE0101A/BefolkningNy");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            JsonPost jsonPost = new JsonPost();
            String jsonText = jsonPost.getJsonText();

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
                double crimePerCapita;
                String formattedCPC;
                String region;
                Crime crime;

                for (int i = 0; i < 21; i++) {

                    crimeList = new LinkedList<>();

                    parts = obj.getAsJsonArray("data").get(i).toString().split(Pattern.quote("\""));
                    region = parts[3];
                    population = Double.parseDouble(parts[9]);

                    region = (String) statisticMap.get(region); // Get the current region.

                    //Add crime per capita
                    crimePerCapita = getFeedSize(region);
                    crimePerCapita = (crimePerCapita / population) * 100;
                    DecimalFormat df = new DecimalFormat("#.#####");
                    formattedCPC = df.format(crimePerCapita);

                    crime = new Crime();
                    crime.setRegion(region);
                    crime.setPopulation(population);
                    crime.setCrimePerCapita(formattedCPC);


                    crimeList.add(crime);
                    hashList.put(region, crimeList);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            this.statisticHashList = hashList;


    }

    public void resetHashMaps() {
        this.hashList = null;
        this.statisticHashList = null;
    }

    public HashMap getHashList() {
        return this.hashList;
    }

    public LinkedList<Feed> getRegionFeed(String region) {
        return (LinkedList<Feed>) this.hashList.get(region);
    }

    public LinkedList<Crime> getRegionStatistics(String region) {

        if(region.equals("hela-sverige")) {
            Iterator it = statisticHashList.entrySet().iterator();
            LinkedList<Crime> crimeList = new LinkedList<>();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                LinkedList<Crime> crime = new LinkedList<>();
                crime = (LinkedList<Crime>) pair.getValue();
                crimeList.add(crime.get(0));
            }

            return crimeList;
        }

        return (LinkedList<Crime>) this.statisticHashList.get(region);
    }

    private double getFeedSize(String region) {
        LinkedList<Feed> feedList;
        feedList = (LinkedList<Feed>) this.hashList.get(region);
        return feedList.size();
    }
}
