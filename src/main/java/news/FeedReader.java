package news;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

/**
 * Created by Victor on 2016-10-14.
 */
public class FeedReader {


    public Document getFeedElements(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().parse(new URL(url).openStream());
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("item");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement  = (Element) nNode;

                System.out.println("Title: " + eElement.getElementsByTagName("title").item(0).getTextContent());
                System.out.println("Link: " + eElement.getElementsByTagName("link").item(0).getTextContent());
                System.out.println("Description: " + eElement.getElementsByTagName("description").item(0).getTextContent());
                System.out.println("GUID: " + eElement.getElementsByTagName("guid").item(0).getTextContent());
                System.out.println("Permalink: " + eElement.getAttribute("isPermalink"));
                System.out.println("Pubdate: " + eElement.getElementsByTagName("pubDate").item(0).getTextContent());
            }
        }
        return doc;
    }

    public static void main(String[] args) throws Exception {
        FeedReader fr = new FeedReader();
        fr.getFeedElements("https://polisen.se/Aktuellt/RSS/Lokal-RSS---Nyheter/Lokala-RSS-listor1/Nyheter-RSS---Sodermanland/?feed=rss");
    }
}
