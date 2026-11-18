import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class MessageTest {

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }


    @Test
    public void testCheckRecipientCellSuccess() {
        Message msg = new Message(1, "+27718693020", "Hi Keegan, did you receive the payment?");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testCheckRecipientCellFailure() {        
        Message msg = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        String result = msg.checkRecipientCell();
        assertTrue(result.contains("incorrectly"), "Cell phone number is incorrectly formatted or does not contain international code.");
    }

    

    @Test
    public void testCheckMessageID() {
        Message msg = new Message(1, "+2771869302", "Hi Keegan");
        Boolean isValid = msg.checkMessageID();
        assertTrue(isValid, "The auto-generated Message ID should be valid.");
    }


    @Test
    public void testCreateMessageHash() {
        Message msg = new Message(0, "00", "Hi Mike, can you join us for dinner tonight?");
        String generatedHash = msg.createMessageHash();
        assertNotNull(generatedHash, "The message hash should be successfully generated.");
    }
    
  
    @Test
     public void testsentMessageSuccess() {   
    String input = "1\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    
    try {
        Message msg = new Message(1, "+2771869302", "Hi Keegan");
        String result = msg.SentMessage();
        assertEquals("Message successfully sent.", result);
    } finally {
        
        System.setIn(originalIn);
    }
}

    @Test
    public void testSentMessageDisregard() {
    String input = "2\n";
    InputStream originalIn = System.in;
    String simulatedInput = "2\n0\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
    
    try {
        Message msg = new Message(1, "+2771869302", "Hi Keegan");
        String result = msg.SentMessage();
        assertEquals("Message disregarded.", result);
    } finally {
        System.setIn(originalIn);
    }
}

    @Test
    public void testSentMessageStore() {
    String input = "3\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    
    try {
        Message msg = new Message(1, "+2771869302", "Hi Keegan");
        String result = msg.SentMessage();
        assertEquals("Message successfully stored.", result);
    } finally {
        System.setIn(originalIn);
    }
}

    
    
    
    
    /**
     * Test of displaySenderAndRecipient method, of class Message.
     */
    @Test
    public void testDisplaySenderAndRecipient() {
        System.out.println("displaySenderAndRecipient");
        Message.storedMessages[0] = "Did you get the cake?";
        Message.recipients[0] = "+27838884567";
        Message.storedCount = 1;
        String result = Message.displaySenderAndRecipient();
        assertFalse(result.isEmpty());
        assertTrue(result.contains("+27838884567"));
    }

    /**
     * Test of displayLongestMessage method, of class Message.
     */
    @Test
    public void testDisplayLongestMessage() {
        System.out.println("displayLongestMessage");
        Message.sentMessages[0] = "Where are you? You are late! I have asked you to be on time.";
        Message.sentCount = 1;
        String expResult = "Where are you? You are late! I have asked you to be on time.";
        String result = Message.displayLongestMessage();
        assertEquals(expResult, result);
    }

    /**
     * Test of searchByMessageID method, of class Message.
     */
    @Test
    public void testSearchByMessageID() {
        System.out.println("searchByMessageID");
        String searchID = "ID004";
        Message.messageIDs[0] = "ID004";
        Message.recipients[0] = "0838884567";
        Message.sentMessages[0] = "It is dinner time!";
        Message.sentCount = 1;
        Message.totalIDs = 1;
        
        String expResult = "0838884567, It is dinner time!";
        String result = Message.searchByMessageID(searchID);
        assertEquals(expResult, result);
    }

    /**
     * Test of searchByRecipient method, of class Message.
     */
    @Test
    public void testSearchByRecipient() {
        System.out.println("searchByRecipient");
        String searchRec = "+27838884567";
        Message.sentMessages = new String[100];
        Message.storedMessages = new String[100];
        Message.recipients = new String[100];
        
        Message.recipients[0] = "+27838884567";
        Message.storedMessages[0]  = "Where are you? You are late! I have asked you to be on time.";
        Message.recipients[1] = "+27838884567";
        Message.storedMessages[1] = "Ok, I am leaving without you.";
        Message.storedCount = 2;
        Message.totalIDs = 2;
        
        String expResult = "\"Where are you? You are late! I have asked you to be on time.\", \"Ok, I am leaving without you.\"";
        String result = Message.searchByRecipient(searchRec);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteByHash method, of class Message.
     */
    @Test
    public void testDeleteByHash() {
        System.out.println("deleteByHash");
        String hash = "01:1:WHEREONTIME";
        
        Message.messageHashes = new String[100];
        Message.storedMessages = new String[100];
        Message.sentMessages = new String[100];
        Message.recipients = new String[100];
        Message.messageIDs = new String[100];
        Message.messageHashes[0] = hash;
        Message.storedMessages[0] = "Where are you? You are late! I have asked you to be on time.";
        Message.storedCount =1;
        Message.totalIDs = 1;
        
        String expResult = "Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        String result = Message.deleteByHash(hash);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of sentMessages array populated
     */
    @Test
    public void testSentMessagesArrayPopulated() {
        Message.sentMessages = new String[]{ "Did you get the cake?", "It is dinner time!"};
        Message.sentCount = 2;
        String[] expResult = {"Did you get the cake?", "It is dinner time!"};
        assertArrayEquals(expResult, Message.sentMessages);
    }
}