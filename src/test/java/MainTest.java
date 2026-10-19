/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of main method, of class Main.
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        
        
        String simulatedInput = "Keegan\n" +      // For First Name
                                "keegan_user\n" +  // For Username
                                "Password123!\n" + // For Password
                                "0718693022\n" +    // For Phone Number
                                "1\n" +            // Selects "1) Send Message" from the menu
                                "0\n";             
        
        java.io.InputStream savedIn = System.in;
        java.io.ByteArrayInputStream mockIn = new java.io.ByteArrayInputStream(simulatedInput.getBytes());
        
        try {
            System.setIn(mockIn);
            String[] args = null;
            Main.main(args);
        } finally {
            System.setIn(savedIn);
        }
    }
   
    
}
