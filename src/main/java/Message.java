import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    String uniqueMessageId;
    int numMessagesSent;
    String recipient;
    String messageText;
    String messageHash;

    private static Message[] globalHistoryLog = new Message[100];
    private static int totalAccumulatedMessages = 0;

    
    public Message() {}

    public Message(int messageNumber, String recipient, String messageText) {
        this.numMessagesSent = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.uniqueMessageId = generateRandomId();
        this.messageHash = createMessageHash();
    }

    
    public void startQuickChatApp(Scanner sc) throws IOException {
        System.out.println("\nWelcome to QuickChat.");
        
        System.out.print("Enter how many messages you wish to enter: ");
        int maxMessages = sc.nextInt();
        sc.nextLine();
        
        int messagesInputted = 0;
        boolean keepRunning = true;
        
        while (keepRunning) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            
            int mainChoice = sc.nextInt();
            sc.nextLine();

            switch(mainChoice) {
                case 1:
                    if (messagesInputted >= maxMessages) {
                        System.out.println("You have reached maximum number of " + maxMessages + "messages.");
                        break;
                    }
                    messagesInputted++;
                    System.out.print("Enter recipient cell  ");
                    String recipient = sc.nextLine();
                    
                    System.out.print("Enter message (max 250 chars): ");
                    String text = sc.nextLine();
                    
                    if (text.length() > 250) {
                        System.out.println("Message exceeds 250 characters by " + (text.length() - 250));
                        messagesInputted--;
                        break;
                    }
                    
                    Message messageInstance = new Message(messagesInputted, recipient, text);                   
                    System.out.println(messageInstance.checkRecipientCell());
                    if (messageInstance.checkRecipientCell().contains("incorrectly")) {
                        messagesInputted--;
                        break;
                    }
                    
                    messageInstance.displayMessageDetails();
                    
                    if (!messageInstance.checkMessageID()){
                        System.out.println("Error: Internal Message ID validation issue.");
                    }else{
                        String choiceResult = messageInstance.SentMessage();
                        if (choiceResult.equals("Send")){
                            System.out.println("Message successfully sent.");
                            System.out.println("Total messages sent: " + Message.returnTotalMessagess());    
                        }else if (choiceResult.equals("Store")){
                            
                        }else if (choiceResult.equals("Disregard")){
                            System.out.println("Message disregarded.");
                            messagesInputted--;
                        }
                        
                        if (messagesInputted == maxMessages) {
                    System.out.println("\n=============================================");
                    System.out.println("Total number of accumulated messages sent: " + Message.returnTotalMessagess());
                    System.out.println("=============================================");
                    }
                  }
                    break;
                    
                case 2:
                    System.out.println(Message.printMessages());
                    break;
                    
                case 3:
                    keepRunning = false;
                    System.out.println("Total messages sent: " + Message.returnTotalMessagess());
                    System.out.println("Quitting application. Good bye");
                    break;
                    
                default:
                    System.out.println("Invalid option. please select a valid option");
                        }
        }
    }

    private String generateRandomId() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) sb.append(rand.nextInt(10));
        return sb.toString();
    }

    public Boolean checkMessageID() {
        return this.uniqueMessageId != null && this.uniqueMessageId.length() <= 10;
    }

    public String checkRecipientCell() {
        if (this.recipient != null && this.recipient.length() <= 12 && this.recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain international code.";
    }

    public String createMessageHash() {
        if (uniqueMessageId == null || uniqueMessageId.length() < 2) return "00:0:ERROR";
        String firstTwoDigits = this.uniqueMessageId.substring(0, 2);
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";
        String textSegment = (firstWord + lastWord).replaceAll("[^a-zA-Z]", "").toUpperCase();
        return firstTwoDigits + ":" + this.numMessagesSent + ":" + textSegment;
    }

    public String SentMessage() {
        Scanner menuScanner = new Scanner(System.in);
        System.out.println("\nSelect an action for this message:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Selection: ");
        
        int userSelection = menuScanner.nextInt();
        menuScanner.nextLine();
        
        if (userSelection == 1) {
            logMessageInstance(this);
            return "Message successfully sent.";
            
        } else if (userSelection == 2) {
            System.out.println("Press 0 to delete the message");
            int confirmDelete = menuScanner.nextInt();
            if (confirmDelete == 0) return "Press 0 to delete the message.";
        } else if (userSelection == 3) {
            logMessageInstance(this);
            try (FileWriter fw = new FileWriter("storedMessages.json", true)) {
                fw.write(this.storeMessage() + ",\n");
            } catch (IOException e) {
                System.out.println("Error storing: " + e.getMessage());
            }
            return "Message successfully stored.";
        }
        return "Invalid selection.";
    }

    private static void logMessageInstance(Message msg) {
        if (totalAccumulatedMessages < globalHistoryLog.length) {
            globalHistoryLog[totalAccumulatedMessages] = msg;
            totalAccumulatedMessages++;
        }
    }

    public static String printMessages() {
        return "Coming soon";
    }

    public static int returnTotalMessagess() {
        return totalAccumulatedMessages;
    }

    public String storeMessage() {
        return "{\n  \"messageId\": \"" + this.uniqueMessageId + "\",\n  \"numSent\": " + this.numMessagesSent + ",\n  \"recipient\": \"" + this.recipient + "\",\n  \"text\": \"" + this.messageText + "\",\n  \"hash\": \"" + this.messageHash + "\"\n}";
    }

    public void displayMessageDetails() {
        System.out.println("\n--- Message Details ---");
        System.out.println("Message ID: " + uniqueMessageId);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
        System.out.println("-----------------------");
    }

}
