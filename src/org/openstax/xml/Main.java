package org.openstax.xml;

import java.io.File;
import java.util.Arrays;
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

        String[] availableChecks = {"all", "duplicate-id", "broken-link", "link-to-duplicate-id"};
        String[] desiredChecks = args.length == 1 ? new String[]{"all"} : Arrays.copyOfRange(args, 1, args.length);
        boolean errorsFound = false;

        for (String check: desiredChecks) {
            System.err.println(String.format("Running %s", check));
            switch (check) {
                case "all":
                    errorsFound |= visitor.linksToDuplicateIds();
                    errorsFound |= visitor.brokenLinks();
                    errorsFound |= visitor.duplicateIds();
                    break;
                case "duplicate-id":
                    errorsFound |= visitor.duplicateIds();
                    break;
                case "broken-link":
                    errorsFound |= visitor.brokenLinks();
                    break;
                case "link-to-duplicate-id":
                    errorsFound |= visitor.linksToDuplicateIds();
                    break;
                default:
                    throw new RuntimeException(String.format("Invalid check name: %s. Valid names are: %s", check, Arrays.toString(availableChecks)));
            }
        }

        if (errorsFound) {
            throw new RuntimeException(String.format("%s failed validation", inputFile));
        }
    }
}
