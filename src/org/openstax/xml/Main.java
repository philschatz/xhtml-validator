package org.openstax.xml;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {

    public static void main(String[] args) throws Throwable {

        if (args.length == 0) {
            throw new RuntimeException("Requires 2 arguments: the path to a file to validate and which checks to run ('all-checks', 'duplicate-id', 'broken-link')");
        }
        File inputFile = new File(args[0]);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XMLVisitor visitor = new XMLVisitor();

        saxParser.parse(inputFile, visitor);

        String whichCheck = "all-checks";
        if (args.length > 1) {
            whichCheck = args[1];
        }

        switch (whichCheck) {
            case "all-checks":
                visitor.checkLinksToDuplicateIds();
                visitor.checkLinks();
                visitor.checkDuplicateIds();
                break;
            case "duplicate-id":
                visitor.checkDuplicateIds();
                break;
            case "broken-link":
                visitor.checkLinks();
                break;
            case "link-to-duplicate-id":
                visitor.checkLinksToDuplicateIds();
                break;
            default:
                throw new RuntimeException("Invalid check name. Valid names are: 'all-checks', 'duplicate-id', 'broken-link'");
        }
    }   
}