import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Message {
    String uniqueMessageId;
    int numMessagesSent;
    String recipient;
    String messageText;
    String messageHash;

    
    public static String[] sentMessages = new String[100];
    public static String[] disregardedMessages = new String[100];
    public static String[] storedMessages = new String[100];
    public static String[] messageHashes = new String[100];
    public static String[] messageIDs = new String[100];
    public static String[] recipients = new String[100];
    public static int sentCount = 0;
    public static int disregardedCount = 0;
    public static int storedCount = 0;
    public static int totalIDs = 0;

    private static Message[] globalHistoryLog = new Message[100];
    private static int totalAccumulatedMessages = 0;

    public Message() {}
    public Message(int num, String rec, String text) {
        this.numMessagesSent = num;
        this.recipient = rec;
        this.messageText = text;
        this.uniqueMessageId = generateRandomId();
        this.messageHash = createMessageHash();
    }

    public void startQuickChatApp(Scanner sc) {
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("Enter how many messages you wish to enter: ");
        int maxMessages = sc.nextInt(); sc.nextLine();
        int messagesInputted = 0;
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages Features");
            System.out.print("Choose: ");
            int mainChoice = sc.nextInt(); sc.nextLine();

            switch (mainChoice) {
                case 1:
                    if (messagesInputted >= maxMessages) {
                        System.out.println("You have reached max of " + maxMessages);
                        break;
                    }
                    messagesInputted++;
                    System.out.print("Enter recipient cell: ");
                    String rec = sc.nextLine();
                    System.out.print("Enter message (max 250): ");
                    String txt = sc.nextLine();

                    if (txt.length() > 250) {
                        System.out.println("Exceeds by " + (txt.length()-250));
                        messagesInputted--; break;
                    }

                    Message m = new Message(messagesInputted, rec, txt);
                    System.out.println(m.checkRecipientCell());
                    if (m.checkRecipientCell().contains("incorrectly")) { messagesInputted--; break; }

                    m.displayMessageDetails();

                    if (!m.checkMessageID()) {
                        System.out.println("Error: ID validation issue.");
                    } else {
                        String result = m.sentMessage(sc);
                        System.out.println(result);

                        if (result.equals("Message successfully sent.")) {
                            sentMessages[sentCount] = m.messageText;
                            recipients[totalIDs] = m.recipient;
                            messageHashes[totalIDs] = m.messageHash;
                            messageIDs[totalIDs] = m.uniqueMessageId;
                            sentCount++; totalIDs++;
                            logMessageInstance(m);

                        } else if (result.equals("Message disregarded.")) {
                            disregardedMessages[disregardedCount++] = m.messageText;
                            messageHashes[totalIDs] = m.messageHash;
                            messageIDs[totalIDs] = m.uniqueMessageId;
                            recipients[totalIDs] = m.recipient;
                            totalIDs++;
                            messagesInputted--;

                        } else if (result.equals("Message successfully stored.")) {
                            storedMessages[storedCount++] = m.messageText;
                            messageHashes[totalIDs] = m.messageHash;
                            messageIDs[totalIDs] = m.uniqueMessageId;
                            recipients[totalIDs] = m.recipient;
                            totalIDs++;
                        }
                    }
                    break;

                case 2:
                    System.out.println(printMessages());
                    break;

                case 3:
                    keepRunning = false;
                    System.out.println("Total: " + returnTotalMessagess());
                    System.out.println("Quitting application. Good bye.");
                    break;

                case 4:
                    handleStoredMessagesMenu(sc);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void handleStoredMessagesMenu(Scanner sc) {
        boolean inSubMenu = true;
        while (inSubMenu) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for message ID");
            System.out.println("d) Search messages for a particular recipient");
            System.out.println("e) Delete a message using message hash");
            System.out.println("f) Display report of all stored messages");
            System.out.println("g) Back to main menu");
            System.out.print("Choose: ");
            String choice = sc.nextLine().toLowerCase();

            switch (choice) {
                case "a": System.out.println(displaySenderAndRecipient()); break;
                case "b": System.out.println(displayLongestMessage()); break;
                case "c": 
                    System.out.print("Enter Message ID: ");
                    System.out.println(searchByMessageID(sc.nextLine()));
                    break;
                case "d":
                    System.out.print("Enter recipient: ");
                    System.out.println(searchByRecipient(sc.nextLine()));
                    break;
                case "e":
                    System.out.print("Enter hash: ");
                    System.out.println(deleteByHash(sc.nextLine()));
                    break;
                case "f": System.out.println(displayFullReport()); break;
                case "g": inSubMenu = false; break;
                default: System.out.println("Invalid choice");
            }
        }
    }

    public static String displaySenderAndRecipient() {
        if (storedCount == 0) return "No stored messages.";
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<storedCount;i++) sb.append("Message: ").append(storedMessages[i]).append(" | Recipient: ").append(recipients[i]).append("\n");
        return sb.toString();
    }

    public static String displayLongestMessage() {
        String longest = "";
        for (int i=0;i<storedCount;i++) if (storedMessages[i].length() > longest.length()) longest = storedMessages[i];
        for (int i=0;i<sentCount;i++) if (sentMessages[i].length() > longest.length()) longest = sentMessages[i];
        return longest.isEmpty() ? "No messages." : longest;
    }

    public static String searchByMessageID(String searchID) {
        for (int i=0;i<totalIDs;i++) {
            if (messageIDs[i].equals(searchID)) {
                String msg = "";
                for(int k=0;k<storedCount;k++) if(recipients[k]!=null && recipients[k].equals(recipients[i])) msg = storedMessages[k];
                for(int k=0;k<sentCount;k++) if(recipients[k]!=null && recipients[k].equals(recipients[i])) msg = sentMessages[k];
                return  recipients[i] + ", " + msg;
            }
        }
        return "Message ID not found.";
    }

    public static String searchByRecipient(String searchRec) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<totalIDs;i++) {
            if (recipients[i] != null && recipients[i].equals(searchRec)) {
                String msg = i < storedMessages.length ? storedMessages[i] : "";
                if(msg== null){
                    msg = (i < sentMessages.length && sentMessages[i] != null) ? sentMessages[i] : "";
                }
                if(sb.length() > 0){
                    sb.append(", ");
                }
            sb.append("\"").append(msg).append("\"");
                
            }
        }
        return sb.length()==0 ? "No messages for recipient." : sb.toString();
    }

    public static String deleteByHash(String hashToDelete) {
        for (int i=0;i<totalIDs;i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(hashToDelete)) {
                String deletedMsg = storedMessages[i] != null ? storedMessages[i] : sentMessages[i];
                for (int j=i;j<totalIDs-1;j++) {
                    messageIDs[j]=messageIDs[j+1];
                    messageHashes[j]=messageHashes[j+1];
                    recipients[j]=recipients[j+1];
                    if(j<storedCount) storedMessages[j]=storedMessages[j+1];
                }
                totalIDs--; storedCount--;
                return "Message \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    public static String displayFullReport() {
        if (totalIDs==0) return "No messages to report.";
        StringBuilder sb = new StringBuilder("\n--- FULL REPORT ---\n");
        for (int i=0;i<totalIDs;i++) {
            sb.append("ID: ").append(messageIDs[i])
              .append(" | Hash: ").append(messageHashes[i])
              .append(" | Recipient: ").append(recipients[i])
              .append(" | Message: ").append(sentMessages[i]!=null ? sentMessages[i] : storedMessages[i])
              .append("\n");
        }
        return sb.toString();
    }

    public static void loadStoredMessagesFromJSON() {
        try {
            File f = new File("storedMessages.json");
            if (!f.exists()) return;
            String content = new String(Files.readAllBytes(Paths.get("storedMessages.json")));
            String[] parts = content.split("\"text\":");
            for (String p : parts) {
                if (p.contains("\"")) {
                    String msg = p.split("\"")[1];
                    if (storedCount < storedMessages.length) storedMessages[storedCount++] = msg;
                }
            }
        } catch (Exception e) {}
    }

    private String generateRandomId() { Random r=new Random(); StringBuilder sb=new StringBuilder(); for(int i=0;i<10;i++) sb.append(r.nextInt(10)); return sb.toString(); }
    public Boolean checkMessageID() { return uniqueMessageId != null && uniqueMessageId.length() <= 10; }
    
    public String checkRecipientCell() {
        String regex = "^\\+27\\d{9,}$";
        if (this.recipient != null && Pattern.matches(regex, this.recipient.trim())) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain international code.";
    }

    public String createMessageHash() {
        if(uniqueMessageId==null||uniqueMessageId.length()<2) return "00:0:ERROR";
        String[] w = messageText.trim().split("\\s+");
        String f=w.length>0?w[0]:"", l=w.length>0?w[w.length-1]:"";
        return uniqueMessageId.substring(0,2)+":"+numMessagesSent+":"+(f+l).replaceAll("[^a-zA-Z]","").toUpperCase();
    }

    public String sentMessage(Scanner menuScanner) {
        System.out.println("\n1) Send Message\n2) Disregard Message\n3) Store Message");
        System.out.print("Selection: "); int sel = menuScanner.nextInt(); menuScanner.nextLine();
        if(sel==1){ return "Message successfully sent."; }
        else if(sel==2){ System.out.print("Press 0 to delete: "); int c=menuScanner.nextInt(); menuScanner.nextLine(); if(c==0) return "Message disregarded."; }
        else if(sel==3){
            try (FileWriter fw = new FileWriter("storedMessages.json", true)) { fw.write(this.storeMessage()+"\n"); }
            catch (IOException e){}
            return "Message successfully stored.";
        }
        return "Invalid selection.";
    }

    private static void logMessageInstance(Message msg){ if(totalAccumulatedMessages<100) globalHistoryLog[totalAccumulatedMessages++]=msg; }
    public static String printMessages() {
        if (sentCount==0) return "No messages sent yet.";
        StringBuilder sb=new StringBuilder("\n--- Recently Sent Messages ---\n");
        for(int i=0;i<sentCount;i++) sb.append((i+1)+") To: "+recipients[i]+" | Msg: "+sentMessages[i]+" | Hash: "+messageHashes[i]+"\n");
        return sb.toString();
    }
    public static int returnTotalMessagess(){ return sentCount; }
    public String storeMessage(){ return "{\"messageId\":\""+uniqueMessageId+"\",\"recipient\":\""+recipient+"\",\"text\":\""+messageText.replace("\"","\\\"")+"\",\"hash\":\""+messageHash+"\"}"; }
    public void displayMessageDetails(){ System.out.println("\n--- Details ---\nID: "+uniqueMessageId+"\nHash: "+messageHash+"\nTo: "+recipient+"\nText: "+messageText); }

    public static void populateTestData() {
        sentMessages = new String[]{"Did you get the cake?", "It is dinner time!"};
        storedMessages = new String[100];
        storedMessages[0] = "Where are you? You are late! I have asked you to be on time.";
        storedMessages[1] = "Ok, I am leaving without you.";
        messageIDs = new String[100];
        messageIDs[0] = "ID001"; messageIDs[1] = "ID002"; messageIDs[4] = "ID004";
        messageHashes = new String[100];
        messageHashes[1] = "01:1:WHEREONTIME"; messageHashes[4] = "03:3:ITTIME";
        recipients = new String[100];
        recipients[0] = "+27834557896"; recipients[1] = "+27838884567"; 
        recipients[2] = "+27838884567"; recipients[4] = "0838884567";
        sentCount = 2; storedCount = 2; totalIDs = 5;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Message app = new Message();
        loadStoredMessagesFromJSON();
        app.startQuickChatApp(sc);
        sc.close();
    }

    String SentMessage() {
        Scanner scanner = new Scanner(System.in);
        return sentMessage(scanner);
    }
}