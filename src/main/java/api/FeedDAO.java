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
 * Klass som hämtar data från svenska polisens RSS feed och Statistiska Centralbyråns api.
 * Beroende på vilken metod användaren kallar så returneras ett svar med informationen
 * utarbetad på det vis som användaren vill.
 */
public class FeedDAO implements Runnable {
    private Thread thread;
    private HashMap hashList;
    private HashMap statisticHashList;
    private HashMap crimePerCapitaMap;
    private LinkedList<Crime> sortedCrimes;
    private boolean numberAdded = false;
    private boolean sorted = false;
    private boolean crimesGotten = false;

    /**
     * Metod som körs varje gång en tråd startas. Anledningen till att vi har implementerat
     * en tråd i denna class är för att vi vill hämta information var femte minut och inte varje
     * gång en användare kallar på en metod. Detta för att requestsen blir mycket snabbare.
     */
    @Override
    public void run() {
        while(true) {
            try {
                //Återställ informationen som är lagrad och ersätt den med den nya information som fås ifrån apierna.
                System.out.println("Updating values...");
                getFeedElements();

                //Vi vill endast hämta information från statistiska centralbyrån en gång eftersom att den informationen
                //aldrig kommer att ändras.
                if(crimesGotten == false) {
                    getCrimeElements();
                    crimesGotten = true;
                }

                getCrimePerCapita();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Sätt en sleep på tråden i fem minuter för att vi inte ska kalla på apierna miljontals gånger per sekund.
            try {
                Thread.sleep(300000); // 300 000 = 5 minutes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metod som startar tråden.
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Metod som hämtar alla händelser i landet från polisens rss feed och lagrar dessa i hashmapen som kallas
     * hashlist där nyckeln är regionen och value är listan med alla Feed objekt för den specifika regionen.
     * @throws Exception
     */
    private void getFeedElements() throws Exception {

        Regions regions = new Regions();
        HashMap regionMap = regions.getRegionMap();
        LinkedList<Feed> feedList;
        HashMap hashList = new HashMap();
        int number = 0;

        //För att kunna få tag på value utan att kalla på nyckeln i hashmapen så gör vi en iterator.
        //I while loopen går vi sedan igenom alla värden i hashmapen för att få ut alla regioners brott.
        //Värdet i detta fallet är RSS-Feed länken till den specifika regionen.
        Iterator it = regionMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().parse(new URL((String) pair.getValue()).openStream());
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");

            //Denna listan används för att spara alla Feed objekt i en lista för den specifika regionen.
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

                    //Om ett skräpmeddelande har hittats så hoppar vi över detta objekt och går till nästa iteration i
                    //for-loopen.
                    if(junkFound) {
                        continue;
                    }

                    //Här skapar vi ett nytt feed objekt som representerar en händelse.
                    //Feed objektet innehåller många olika instansvariabler som titel, beskrivning osv.
                    //som vi får hämta ut ifrån polisens svar och sedan sätta in i vårt feed objekt.
                    Feed feed = new Feed();

                    //Eftersom att titeln kan se annorlunda ut i polisens RSS feed så måste vi ta hand om alla
                    //tre fall så vi kan få ut rätt plats, titel och brottstid.
                    if (parts.length == 4) {
                        feed.setLocation(parts[3].substring(1));
                        feed.setCrimeType(parts[1].substring(1) + " - " + parts[2].substring(1, 2).toUpperCase()
                                + parts[2].substring(2));

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

                    //Dela upp description i description och address för olika regioner för att poliser från olika
                    //regioner lägger till information till RSS Feeden på olika sätt.
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

            //Efter att for loopen är klar så är vi klara med en region, då sätter vi in regionen som nyckeln i
            //hash mapen och listan som value.
            hashList.put(pair.getKey(), feedList);

            it.remove(); //To avoid ConcurrentModificationException - Dunno om detta är nödvändigt, lösning från stackoverflow.
        }

        //Varje gång vi hämtar ny information så måste vi ta bort den gamla informationen och lägga till den nya.
        resetHashList();
        this.hashList = hashList;
    }

    /**
     * Metod som hämtar data från statistiska centralbyrån om specifika regioners invånarantal. Detta görs
     * endast en gång då denna information inte kommer att ändras.
     */
    private void getCrimeElements() {

        StatisticRegions allRegions = new StatisticRegions();
        HashMap statisticMap = allRegions.getStatisticMap();
        HashMap hashList = new HashMap();

        //Förbereder den URL json POST meddelandet ska skickas till.
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
            //POST meddelandet skickas och vi får ett svar tillbaka.
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
            Crime crime;

            for (int i = 0; i < 21; i++) {

                //Skapar en lista där statistiken för ett specifikt län ska befinna sig. Anledningen till att vi har
                //en lista här för ett objekt är för att vi vill få ut ett likadant JSON svar för hela Sverige som
                //en specifik region.
                crimeList = new LinkedList<>();

                parts = obj.getAsJsonArray("data").get(i).toString().split(Pattern.quote("\""));
                region = parts[3]; //Får numret som representerar en region.
                population = Double.parseDouble(parts[9]);
                region = (String) statisticMap.get(region); // Gör om numret till regionen

                //Gör en ny lista av crimes men lägger inte till crimePerCapita ännu.
                crime = new Crime();
                crime.setRegion(region.substring(0, 1).toUpperCase() + region.substring(1));
                crime.setPopulation(population);

                //Sätter in ett crime i listan och sedan sätts listan in i hashmapen som sedan används för att
                //få ut alla regioners invånarantal.
                crimeList.add(crime);
                hashList.put(toEnglish(region), crimeList);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        this.statisticHashList = hashList;

    }

    /**
     * Metod som beräknar crimePerCapita för varje region och använder sig av statisticHashList för att
     * få fram regionen och invånarantalet för regionen.
     */
    private void getCrimePerCapita() {

        HashMap crimePerCapitaMap = new HashMap();
        double crimePerCapita;
        double population;
        String formattedCPC;
        String region;

        //Går igenom hashmapen som innehåller alla regioner och deras invånarantal.
        Iterator it = statisticHashList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            //Får listan ifrån hashmapen som innehåller regioner och invånarantal.
            LinkedList<Crime> crimePerCapitaList = new LinkedList<>();
            LinkedList<Crime> crimeList = new LinkedList<>();
            crimeList = (LinkedList<Crime>) pair.getValue();

            population = crimeList.get(0).getPopulation();
            region = crimeList.get(0).getRegion();

            //Add crime per capita
            crimePerCapita = getFeedSize(toEnglish((String) pair.getKey()));
            crimePerCapita = (crimePerCapita / population) * 100;
            DecimalFormat df = new DecimalFormat("#.#####");
            formattedCPC = df.format(crimePerCapita);

            Crime crime = new Crime();
            crime.setCrimePerCapita(formattedCPC);
            crime.setPopulation(population);
            crime.setRegion(region);

            crimePerCapitaList.add(crime);

            //Lägg till den nya listan med crimePerCapita tillagt i en ny hash map.
            crimePerCapitaMap.put(toEnglish(region.toLowerCase()), crimePerCapitaList);
        }

        //Töm hashmappen från gamla värden och lägg till de nya samt sätt boolean värdena till false eftersom att
        //allt har blivit återställt och de också ska återgå till deras grundvärde.
        resetCrimePerCapitaMap();
        this.crimePerCapitaMap = crimePerCapitaMap;
        numberAdded = false;
        sorted = false;
    }

    /**
     * Metod som sätter crimePerCapitaMap hashmapen till null
     */
    private void resetCrimePerCapitaMap() {
        this.crimePerCapitaMap = null;
    }

    /**
     * Metod som sätter hashList hashmapen till null
     */
    private void resetHashList() {
        this.hashList = null;
    }

    /**
     * Metod som skickar tillbaka brottshändelser för en specifik region eller hela Sverige.
     * @param region Den region som har valts av användaren där hela Sverige är 'hela-sverige'.
     * @return Listan med alla brott som skickas tillbaka.
     */
    public LinkedList<Feed> getRegionFeed(String region) {
        return (LinkedList<Feed>) this.hashList.get(region);
    }

    /**
     * Metod som skickar tillbaka statistik för en specifik region eller hela Sverige.
     * @param region Den region som användaren har valt där hela Sverige är 'hela-sverige'.
     * @return Skickar tillbaka statistik för specifika regioner.
     */
    public LinkedList<Crime> getRegionStatistics(String region) {

        if (region.equals("hela-sverige")) {

            //För att vi inte ska behöva sortera listan varje gång en användare kallar
            //på denna metod så kollar vi om den redan har blivit sorterad en gång.
            //Detta gjorde vi för att vi ansåg att det skulle förbättra prestandan på servern.
            if(sorted == false) {
                sortedCrimes = getAllRegionStatistics();
                sortedCrimes = sortCrimes(sortCrimes(sortedCrimes));
                sorted = true;
            }

            //Här lägger vi till nummer innan regionerna som rangordnar dem från 1 till 21.
            //Om detta redan har gjorts en gång och någon kallar på 'hela-sverige' igen så ska
            //numrena inte läggas till igen.
            if(numberAdded == false) {
                numberAdded = true;
                for(int i = 0; i < sortedCrimes.size(); i++) {
                    sortedCrimes.get(i).addNumberToRegion(i);
                }
            }

            return sortedCrimes;
        }

        //Om vi inte har valt 'hela-sverige' så vill vi inte ha nummer framför regionerna, därför tar vi bort dem.
        if(numberAdded) {
            for(int i = 0; i < sortedCrimes.size(); i++) {
                sortedCrimes.get(i).removeNumberFromRegion(i);
            }
            numberAdded = false;
        }


        return (LinkedList<Crime>) this.crimePerCapitaMap.get(region);
    }

    /**
     * Metod som skickar tillbaka antalet brott som har hänt i en specifik region.
     * @param region Regionen som användaren har valt.
     * @return antalet brott som har hänt i den specifika regionen skickas tillbaka.
     */
    private double getFeedSize(String region) {
        LinkedList<Feed> feedList;
        feedList = (LinkedList<Feed>) this.hashList.get(region);
        return feedList.size();
    }

    /**
     * Metod som sorterar regioner beroende på hur hög crimePerCapita de har.
     * @param crimeList listan man vill sortera.
     * @return man skickar tillbaka den sorterade listan.
     */
    private LinkedList<Crime> sortCrimes(LinkedList<Crime> crimeList) {
        //A någon anledning så kan detta bugga i Windows, anledningen ligger p.g.a. att crimePerCapita
        //är en sträng. Det är endast i Windows vi har haft problem med detta dock.
        //Anledningen till att crimePerCapita ens är en sträng är för att vi formaterar den innan vi
        //sätter värdet till instansvariabeln vilket gör om en double till en sträng.
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

    /**
     * Metod som skickar tillbaka alla listor i crimePerCapitaMap hashmapen.
     * @return skickar tillbaka en lista med alla crimes.
     */
    private LinkedList<Crime> getAllRegionStatistics() {
        Iterator it = crimePerCapitaMap.entrySet().iterator();
        LinkedList<Crime> crimeList = new LinkedList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            LinkedList<Crime> crime = new LinkedList<>();
            crime = (LinkedList<Crime>) pair.getValue();
            crimeList.add(crime.get(0));
        }

        return crimeList;
    }

    /**
     * Metod som gör om en sträng innehållande 'å', 'ä' eller 'ö' till 'a' eller 'o'.
     * @param region strängen du vill förändra.
     * @return Den förändrade strängen som skickas tillbaka.
     */
    private String toEnglish(String region) {
        region = region.replace("ö", "o").replace("ä", "a").replace("å", "a");
        return region;
    }
}
