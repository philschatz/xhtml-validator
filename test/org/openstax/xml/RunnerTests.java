package org.openstax.xml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

class RunnerTests {
    private final ByteArrayOutputStream capturedErr = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setup() {
        System.setErr(new PrintStream(capturedErr));
    }

    @AfterEach
    public void restore() {
        System.setErr(originalErr);
    }

    @Test
    void validateRunChecksAll() {
        InputStream inputFileStream = getClass().getClassLoader().getResourceAsStream("fail-all.xhtml");
        assertThrows(
            RuntimeException.class,
            () -> Main.runChecks(inputFileStream, new String[]{"all"})
        );
        assertTrue(capturedErr.toString().contains("Links that point to nowhere"));
        assertTrue(capturedErr.toString().contains("Number of duplicate-id elements"));
        assertTrue(capturedErr.toString().contains("Found at least one link that points to multiple elements"));
    }

    @Test
    void validateRunChecksSpecified() {
        InputStream inputFileStream = getClass().getClassLoader().getResourceAsStream("fail-all.xhtml");
        assertThrows(
            RuntimeException.class,
            () -> Main.runChecks(inputFileStream, new String[]{"duplicate-id", "broken-link", "link-to-duplicate-id"})
        );
        assertTrue(capturedErr.toString().contains("Links that point to nowhere"));
        assertTrue(capturedErr.toString().contains("Number of duplicate-id elements"));
        assertTrue(capturedErr.toString().contains("Found at least one link that points to multiple elements"));
    }

    @Test
    void validateRunChecksAllPass() {
        InputStream inputFileStream = getClass().getClassLoader().getResourceAsStream("pass.xhtml");
        assertDoesNotThrow(() -> Main.runChecks(inputFileStream, new String[]{"all"}));
        assertFalse(capturedErr.toString().contains("Links that point to nowhere"));
        assertFalse(capturedErr.toString().contains("Number of duplicate-id elements"));
        assertFalse(capturedErr.toString().contains("Found at least one link that points to multiple elements"));
    }
}
