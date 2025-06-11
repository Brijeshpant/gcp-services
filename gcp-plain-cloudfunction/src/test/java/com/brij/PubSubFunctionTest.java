//package com.brij;
//
//import com.google.common.testing.TestLogHandler;
//import io.cloudevents.CloudEvent;
//import io.cloudevents.core.builder.CloudEventBuilder;
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.Test;
//
//import java.net.URI;
//import java.util.Base64;
//import java.util.logging.Logger;
//
//import static com.google.common.truth.Truth.assertThat;
//
//
//public class PubSubFunctionTest {
//    private static final Logger logger = Logger.getLogger(PubSubFunctionTest.class.getName());
//
//    private static final TestLogHandler logHandler = new TestLogHandler();
//
//    @BeforeClass
//    public static void beforeClass() {
//        logger.addHandler(logHandler);
//    }
//
//    @Test
//    void functionsPubsubSubscribe_shouldPrintPubsubMessage() throws Exception {
//        String msg = "Hello World";
//        String encodedMessage = Base64.getEncoder().encodeToString(msg.getBytes());
//        String encodedData = new String("{\"message\": { \"data\": \"" + encodedMessage + "\"} }");
//
//        CloudEvent event =
//                CloudEventBuilder.v1()
//                        .withId("0")
//                        .withType("pubsub.message")
//                        .withSource(URI.create("https://example.com"))
//                        .withData(encodedData.getBytes())
//                        .build();
//
//        new PubSubFunction().accept(event);
//
//        assertThat("Hello, " + msg + "!").isEqualTo(
//                logHandler.getStoredLogRecords().get(0).getMessage());
//    }
//}