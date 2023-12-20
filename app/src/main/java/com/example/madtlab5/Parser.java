package com.example.madtlab5;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
public class Parser
{
    public ArrayList<String> parseXML(String xmlData)
    {
        ArrayList<String> currencyList = new ArrayList<>();
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("item"))
                {
                    String currencyCode = "";
                    String exchangeRate = "";
                    while (eventType != XmlPullParser.END_TAG || !parser.getName().equals("item"))
                    {
                        if (eventType == XmlPullParser.START_TAG && parser.getName().equals("targetCurrency"))
                        {
                            parser.next();
                            currencyCode = parser.getText();
                        }
                        else if (eventType == XmlPullParser.START_TAG && parser.getName().equals("exchangeRate"))
                        {
                            parser.next();
                            exchangeRate = parser.getText();
                        }
                        eventType = parser.next();
                    }
                    currencyList.add(currencyCode + " - " + exchangeRate);
                }
                eventType = parser.next();
            }
        }
        catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        return currencyList;
    }
}