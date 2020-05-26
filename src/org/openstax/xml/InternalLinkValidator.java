package org.openstax.xml;

import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class InternalLinkValidator extends DefaultHandler {

    Set<String> ids = new HashSet<String>();
    Set<String> linksToCheck = new HashSet<String>();

    @Override
    public void startElement(String uri, 
    String localName, String qName, Attributes attributes) throws SAXException {

        String id = attributes.getValue("id");
        String href = attributes.getValue("href");

        if (id != null) {
            ids.add(id);
        }

        if (href != null && href.charAt(0) == '#') {
            String hrefId = href.substring(1);
            linksToCheck.add(hrefId);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        int nowhereCount = 0;
        for (String s : linksToCheck) {
            if (!ids.contains(s)) {
                nowhereCount++;
                System.err.println(String.format("Link points to nowhere: href='#%s'", s));
            }
        }
        if (nowhereCount != 0) {
            throw new SAXException(String.format("Links that point to nowhere: %d", nowhereCount));
        }
    }

}
