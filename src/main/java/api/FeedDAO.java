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
    private boolean numberAdded = false;

    @Override
    public void run() {
        while(true) {
            try {
                //Reset the hash map and give it the new values.
                System.out.println("Updating values...");
                numberAdded = false;
                getFeedElements();
                getCrimeElements();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Sleep for 30 minutes.
            try {
                Thread.sleep(60000); // 60 000 = 1 minute
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void getFeedElements() throws Exception {

        Regions regions = new Regions();
        HashMap regionMap = regions.getRegionMap();
        LinkedList<Feed> feedList;
        HashMap hashList = new HashMap();
        int number = 0;

        Iterator it = regionMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().parse(new URL((String) pair.getValue()).openStream());
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");
            feedList = new LinkedList<>();

            for (int n = 0; n < nList.getLength(); n++) {

                Node nNode = nList.item(n);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                    String parts[] = title.split(",");

                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();

                    //Om det inte är ett brott utan endast ett meddelande till användarna angående deras RSS flöde.
                    boolean junkFound = false;
                    for(int i = 0; i < parts.length; i++) {
                        if(parts[i].contains("Övrigt") || parts[i].contains("Sammanfattning") ) {
                            junkFound = true;
                        }
                    }

                    if(junkFound) {
                        continue;
                    }

                    Feed feed = new Feed();

                    if (parts.length == 4) {
                        feed.setLocation(parts[3].substring(1));
                        feed.setCrimeType(parts[1].substring(1) + " - " + parts[2].substring(1, 2).toUpperCase() + parts[2].substring(2));
                        feed.setCrimeDate(parts[0]);
                    } else if(parts.length == 2) {
                        String parts2[] = parts[0].split(" ");
                        String crimeDate = parts2[0] + " " + parts2[1];
                        title = "";
                        for(int i = 2; i < parts2.length; i++) {
                            title += parts2[i] + " ";
                        }
                        feed.setCrimeType(title.substring(1));
                        feed.setLocation(parts[1].substring(1));
                        feed.setCrimeDate(crimeDate);
                    } else {
                        feed.setCrimeType(parts[1].substring(1));
                        feed.setLocation(parts[2].substring(1));
                        feed.setCrimeDate(parts[0]);
                    }

                    //Dela upp description i description och address för olika regioner.
                    if(pair.getKey().equals("stockholm")) {
                        if(description.contains("\n") && !description.substring(Math.max(description.length() - 4, 0)).contains("\n")) {
                            String[] descriptionParts = description.split(".\n");
                            try {
                                String address = descriptionParts[0];
                                description = descriptionParts[1];
                                feed.setCrimeAddress(address);
                            } catch(ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if(pair.getKey().equals("skane")) {
                        if(description.contains(",")) {
                            String descriptionParts[] = description.split(", ");
                            String address = "";
                            for(int i = 0; i < descriptionParts.length; i++) {
                                if(i == 0) {
                                    description = descriptionParts[i];
                                } else {
                                    if(i + 1 == descriptionParts.length) {

                                    address += descriptionParts[i];
                                    } else {
                                        address += descriptionParts[i] + ", ";
                                    }
                                }
                            }
                            feed.setCrimeAddress(address);
                        }
                    }

                    feed.setLink(eElement.getElementsByTagName("link").item(0).getTextContent());
                    feed.setDescription(description);
                    feed.setGuid(eElement.getElementsByTagName("guid").item(0).getTextContent());
                    feed.setPubDate(eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                    feed.setCrimeNumber(number);
                    number++;

                    feedList.add(feed);
                }
            }

            //Creates a HashMap, puts the regions name as its key and the ArrayList with all the feed information as the value.
            //This is then added to the Main feedRegionList which will later be sent over to the storage class.
            hashList.put(pair.getKey(), feedList);

            it.remove(); //To avoid ConcurrentModificationException - Dunno om detta är nödvändigt, lösning från stackoverflow.
        }

        number = 0;
        resetHashList();
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
                crime.setRegion(region.substring(0, 1).toUpperCase() + region.substring(1));
                crime.setPopulation(population);
                crime.setCrimePerCapita(formattedCPC);


                crimeList.add(crime);
                hashList.put(region, crimeList);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        resetStatisticHashList();
        this.statisticHashList = hashList;


    }

    private void resetStatisticHashList() {
        this.statisticHashList = null;
    }

    private void resetHashList() {
        this.hashList = null;
    }

    public HashMap getHashList() {
        return this.hashList;
    }

    public LinkedList<Feed> getRegionFeed(String region) {
        return (LinkedList<Feed>) this.hashList.get(region);
    }

    public LinkedList<Crime> getRegionStatistics(String region) {

        if (region.equals("hela-sverige")) {
            Iterator it = statisticHashList.entrySet().iterator();
            LinkedList<Crime> crimeList = new LinkedList<>();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                LinkedList<Crime> crime = new LinkedList<>();
                crime = (LinkedList<Crime>) pair.getValue();
                crimeList.add(crime.get(0));
            }
            LinkedList<Crime> sortedCrimeList;
            sortedCrimeList = sortCrimes(crimeList);

            if(numberAdded == false) {
                numberAdded = true;
                for(int i = 0; i < sortedCrimeList.size(); i++) {
                    sortedCrimeList.get(i).addNumberToRegion(i);
                }
            }

            return sortedCrimeList;
        }

        return (LinkedList<Crime>) this.statisticHashList.get(region);
    }

    private double getFeedSize(String region) {
        LinkedList<Feed> feedList;
        feedList = (LinkedList<Feed>) this.hashList.get(region);
        return feedList.size();
    }

    private LinkedList<Crime> sortCrimes(LinkedList<Crime> crimeList) {
        Collections.sort(crimeList, new Comparator<Crime>(){
            @Override
            public int compare(Crime o1, Crime o2){
                if(Double.parseDouble(o1.getCrimePerCapita()) > Double.parseDouble(o2.getCrimePerCapita())){
                    return -1;
                }
                if(Double.parseDouble(o1.getCrimePerCapita()) < Double.parseDouble(o2.getCrimePerCapita())){
                    return 1;
                }
                return 0;
            }
        });

        return crimeList;
    }
}
