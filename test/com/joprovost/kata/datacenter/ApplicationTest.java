package com.joprovost.kata.datacenter;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ApplicationTest {
   @Test
   public void runningTheApplicationWithJsonInputShowsExpectedJsonOutput() throws Exception {

      assertThat(
            runningTheApplicationWith("{" +
                  "    \"servers\": [{" +
                  "        \"id\": \"server1\"," +
                  "        \"capacity\": 4" +
                  "    }, {" +
                  "        \"id\": \"server2\"," +
                  "        \"capacity\": 6" +
                  "    }]," +
                  "    \"virtualMachines\": [{" +
                  "        \"id\": \"VM1\"," +
                  "        \"size\": 1" +
                  "    }, {\n" +
                  "        \"id\": \"VM2\"," +
                  "        \"size\": 4" +
                  "    }, {\n" +
                  "        \"id\": \"VM3\"," +
                  "        \"size\": 2" +
                  "    }]" +
                  "}"),

            shows("{\n" +
                  "  \"servers\" : [ {\n" +
                  "    \"id\" : \"server1\",\n" +
                  "    \"capacity\" : 4,\n" +
                  "    \"usePercentage\" : 75,\n" +
                  "    \"virtualMachines\" : [ {\n" +
                  "      \"id\" : \"VM1\",\n" +
                  "      \"size\" : 1\n" +
                  "    }, {\n" +
                  "      \"id\" : \"VM3\",\n" +
                  "      \"size\" : 2\n" +
                  "    } ]\n" +
                  "  }, {\n" +
                  "    \"id\" : \"server2\",\n" +
                  "    \"capacity\" : 6,\n" +
                  "    \"usePercentage\" : 66,\n" +
                  "    \"virtualMachines\" : [ {\n" +
                  "      \"id\" : \"VM2\",\n" +
                  "      \"size\" : 4\n" +
                  "    } ]\n" +
                  "  } ]\n" +
                  "}"));
   }



   private Matcher<String> shows(String output) {
      return equalTo(output);
   }

   private String runningTheApplicationWith(String input) throws IOException {
      InputStream in = System.in;
      PrintStream out = System.out;

      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      System.setOut(new PrintStream(baos));

      Application.main(new String[0]);

      System.setOut(out);
      System.setIn(in);

      return baos.toString();
   }
}
