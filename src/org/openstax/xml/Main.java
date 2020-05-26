package org.openstax.xml;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {

    public static void main(String[] args) {

        try {
            if (args.length == 0) {
                throw new RuntimeException("Requires 1 argument: the path to a file to validate");
            }
            File inputFile = new File(args[0]);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            IdValidator idValidator = new IdValidator();
            InternalLinkValidator internalValidator = new InternalLinkValidator();

            saxParser.parse(inputFile, internalValidator);
            saxParser.parse(inputFile, idValidator);
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }   
}