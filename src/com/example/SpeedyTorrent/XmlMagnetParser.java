package com.example.SpeedyTorrent;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Wouter
 * Date: 31/03/14
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class XmlMagnetParser extends AsyncTask<String, String, HashMap<String, List<Map<String, String>>>> {

    private final String tagName;
    Map<String, List<Map<String, String>>> torrents = null;
    final String xmlLocation;
    final String serverUrl = "http://wouter.no-ip.biz:5680//";

    public XmlMagnetParser(final String xmlLocation, String tagName) {
        this.xmlLocation = xmlLocation;
        this.tagName = tagName;
    }

    @Override
    protected HashMap<String, List<Map<String, String>>> doInBackground(String... url) {
        InputStream inputXml = null;
        try {
            //final String xmlAdres = "http://wouter.no-ip.biz:5680//results//kickassTorrents.xml";
            HttpGet uri;
            try {
             uri = new HttpGet(serverUrl + xmlLocation);
            } catch (Throwable t) {
                uri = new HttpGet("192.168.1.156:5680//" + xmlLocation);
            }
            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse resp = client.execute(uri);
            if (resp.getStatusLine().getStatusCode() == 200) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(resp.getEntity().getContent());

                torrents = new HashMap<String, List<Map<String, String>>>();
                NodeList shows = doc.getElementsByTagName(tagName);
                //NodeList shows = doc.getElementsByTagName("tv-show");
                for (int i = 0; i < shows.getLength(); i++) {
                    NodeList show = shows.item(i).getChildNodes();
                    Map<String, String> torrentInfo = new HashMap<String, String>();
                    for (int j = 0; j < show.getLength(); j++) {
                        if (show.item(j) != null && show.item(j).getNodeName() != null && show.item(j).getChildNodes() != null && show.item(j).getChildNodes().item(0) != null && show.item(j).getChildNodes().item(0).getNodeValue() != null) {
                            torrentInfo.put(show.item(j).getNodeName(), show.item(j).getChildNodes().item(0).getNodeValue());
                        }
                    }
                    String showName = shows.item(i).getAttributes().item(0).getNodeValue();
                    if (torrents.containsKey(showName)) {
                        torrents.get(showName).add(torrentInfo);
                    } else {
                        List<Map<String, String>> showNameList = new ArrayList<Map<String, String>>();
                        showNameList.add(torrentInfo);
                        torrents.put(shows.item(i).getAttributes().item(0).getNodeValue(), showNameList);
                    }
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return (HashMap<String, List<Map<String, String>>>) torrents;
    }


    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }


    protected void onPostExecute(Map<String, Map<String, String>> s) {

    }
}
