package news;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Victor on 2016-10-14.
 */
public class FeedReader {


    public LinkedList getFeedElements(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().parse(new URL(url).openStream());
        doc.getDocumentElement().normalize();
        LinkedList<Feed> feedList = new LinkedList<>();

        NodeList nList = doc.getElementsByTagName("item");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

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
        return feedList;
    }

    public double getAmountOfEvents(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().parse(new URL(url).openStream());
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");

        return nList.getLength();
    }
}
