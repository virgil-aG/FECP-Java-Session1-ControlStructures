package org.example;

import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    private final String REPORT_FILE = "test-results.csv";
    private PrintWriter writer;

    @BeforeAll
    public void setupReportFile() throws IOException {
        writer = new PrintWriter(new FileWriter(REPORT_FILE, false)); // overwrite
        writer.println("Test Name,Input,Expected Output,Actual Output,Status,Timestamp");
    }

    @AfterAll
    public void closeReportFile() {
        if (writer != null) {
            writer.close();
        }
    }

    private void runTestWithCSV(String testName, String input, String expectedOutput) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

        String actualOutput;
        String status;

        try {
            Main.main(null);
            actualOutput = out.toString().trim();

            boolean passed = actualOutput.contains(expectedOutput);
            status = passed ? "PASS" : "FAIL";

        } catch (Exception e) {
            actualOutput = "Exception: " + e.getMessage();
            status = "FAIL";
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        // Log to CSV
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                testName,
                input.replace("\n", "\\n"),
                expectedOutput,
                actualOutput.replace("\n", "\\n").replace("\r", ""),
                status,
                timestamp);
    }

    // 🔽 Membership pricing tests

    @Test
    public void testRegularAdult() {
        runTestWithCSV("testRegularAdult", "Regular\n30\n", "Price: $100.00");
    }

    @Test
    public void testVipLowerCase() {
        runTestWithCSV("testVipLowerCase", "vip\n45\n", "Price: $150.00");
    }

    @Test
    public void testPremiumUpperCase() {
        runTestWithCSV("testPremiumUpperCase", "PREMIUM\n70\n", "Price: $150.00");
    }

    @Test
    public void testMixedCaseVIPSenior() {
        runTestWithCSV("testMixedCaseVIPSenior", "vIp\n66\n", "Price: $112.50");
    }

    @Test
    public void testInvalidStatus() {
        runTestWithCSV("testInvalidStatus", "Gold\n30\n", "Invalid membership status entered.");
    }
}
