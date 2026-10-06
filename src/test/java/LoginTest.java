/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    public LoginTest() {
    }    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }


 

    /**
     * Test of CheckUserName method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testCheckUserName() {
        System.out.println("CheckUserName");
        String username = "Lani_m";
        Boolean expResult = false;
        Boolean result = Login.CheckUserName(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    @org.junit.jupiter.api.Test
    public void testCheckUserNameTRUE() {
        System.out.println("CheckUserName");
        String username = "Lani_";
        Boolean expResult = true;
        Boolean result = Login.CheckUserName(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    /**
     * Test of CheckPasswordComplexity method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testCheckPasswordComplexity() {
        System.out.println("CheckPasswordComplexity");
        String password = "Passwo@1";
        boolean expResult = true;
        boolean result = Login.CheckPasswordComplexity(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of CheckCellPhoneNumber method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testCheckCellPhoneNumber() {
        System.out.println("CheckCellPhoneNumber");
        String cellphoneNumber = "+27 98 765 4321";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.CheckCellPhoneNumber(cellphoneNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of registerUser method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String username = "Lani_";
        String password = "Passwo@1";
        String cellPhoneNumber = "+27987654321";
        String firstname = "Lani";
        Login instance = new Login();
        String expResult = "Username successfully captured.\nPassword successfully captured.\nCell phone number successfuly added.";
        String result = instance.registerUser(username, password, cellPhoneNumber, firstname);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of loginUser method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String enteredusername = "";
        String enteredPassword = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.loginUser(enteredusername, enteredPassword);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
      
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean isLoggedIn = false;
        Login instance = new Login();
        String expResult = "User or password incorrect, please try again.";
        String result = instance.returnLoginStatus(isLoggedIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }
    
}
