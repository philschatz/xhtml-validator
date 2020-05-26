package org.openstax.xml;

import java.util.Set;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IdValidator extends DefaultHandler {

    Set<String> ids = new HashSet<String>();
    HashMap<String, Integer> duplicates = new HashMap<String, Integer>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        String id = attributes.getValue("id");
        if (id != null) {
            if (ids.contains(id)) {
                int count = 0;
                if (duplicates.containsKey(id)) {
                    count = duplicates.get(id);
                }
                count++;
                duplicates.put(id, count);
            } else {
                ids.add(id);
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        int total = 0;
        for (Entry<String, Integer> d : duplicates.entrySet()) {
            total += d.getValue();
            System.err.println(String.format("Found %d duplicate(s) with id='%s'", d.getValue(), d.getKey()));
        }
        if (!duplicates.isEmpty()) {
            throw new SAXException(String.format("Number of duplicate-id elements: %d", total));
        }
    }
}
