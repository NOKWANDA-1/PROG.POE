import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MessageTest {


    @Test
    public void testCheckRecipientCellSuccess() {
        Message msg = new Message(1, "+2771869302", "Hi Keegan, did you receive the payment?");
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
     public void testSentMessageSuccess() {   
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
        assertEquals("Press 0 to delete the message.", result);
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
}
