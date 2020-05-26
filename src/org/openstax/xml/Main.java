package org.openstax.xml;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {

    public static void main(String[] args) {

        try {
            if (args.length == 0) {
                throw new RuntimeException("Requires 2 arguments: the path to a file to validate and which checks to run ('all-checks', 'duplicate-id', 'broken-link')");
            }
            File inputFile = new File(args[0]);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            IdValidator idValidator = new IdValidator();
            InternalLinkValidator internalValidator = new InternalLinkValidator();

            String whichCheck = "all-checks";
            if (args.length > 1) {
                whichCheck = args[1];
            }

            switch (whichCheck) {
                case "all-checks":
                    saxParser.parse(inputFile, internalValidator);
                    saxParser.parse(inputFile, idValidator);
                    break;
                case "duplicate-id":
                    saxParser.parse(inputFile, idValidator);
                    break;
                case "broken-link":
                    saxParser.parse(inputFile, internalValidator);
                    break;
                default:
                    throw new RuntimeException("Invalid check name. Valid names are: 'all-checks', 'duplicate-id', 'broken-link'");
            }
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }   
}