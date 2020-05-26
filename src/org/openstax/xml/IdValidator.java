package org.openstax.xml;

import java.util.Set;
import java.util.HashSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IdValidator extends DefaultHandler {

    Set<String> ids = new HashSet<String>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        String id = attributes.getValue("id");
        if (id != null) {
            if (ids.contains(id)) {
                throw new SAXException(String.format("Found duplicate id '%s'", id));
            } else {
                ids.add(id);
            }
        }
    }

}
