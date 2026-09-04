import java.util.Scanner;

    class Login{
        String username;
        String firstname;
        String lastname;
        String password;
        String cellPhoneNumber;
        
        
        public static Boolean CheckUserName(String username){
            return username !=null && username.contains("_") && username.length()<=5;
            
        }
        
        public static boolean CheckPasswordComplexity(String password){
           if (password.length()<8){
               return false;
           }
            boolean hasCapitalLetter = false;
            boolean hasNumber = false;
            boolean hasSpecialCharacter = false;
            
             for (int i = 0; i < password.length(); i++){
             
                 char ch = password.charAt(i);
                 
                 if (Character.isUpperCase(ch)) {
                     hasCapitalLetter = true;
                 }else if (Character.isDigit(ch)) {
                       hasNumber = true;
                       }else if (!Character.isLetterOrDigit(ch)){
                         hasSpecialCharacter = true; 
               }
             }
                  return hasCapitalLetter && hasNumber && hasSpecialCharacter; 
        }
        
        public boolean CheckCellPhoneNumber(String cellphoneNumber){
            return cellphoneNumber.startsWith( "+27") && cellphoneNumber.length() == 12;
        }
        public String registerUser(String username, String password, String cellPhoneNumber, String firstname){
            if (!CheckUserName(username)){
                return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
            }
            if (!CheckPasswordComplexity(password)){
                return"Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
            }
            if (!CheckCellPhoneNumber(cellPhoneNumber)){
                return"Cell phone number is incorrectly formatted or does not contain international code.";
            }
            this.firstname = firstname;
            this.username  =username;
            this.password = password;
            return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfuly added.";
        } 
 
public boolean loginUser(String enteredusername, String enteredPassword){
    return enteredusername.equals(this.username) && enteredPassword.equals(this.password);
}

public  String returnLoginStatus (boolean isLoggedIn){
    if (isLoggedIn){
        return "Welcome " + firstname + " it is great to see you again.";
    }else{
        return "User or password incorrect, please try again.";
    }
}
    }
public class Main{
    
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        Login user = new Login();
            
         
         System.out.println("Enter first name:");
         String firstname = sc.nextLine();
         
         System.out.println("Enter username:");
         String username = sc.nextLine();
         
         System.out.println("Enter password:");
          String password = sc.nextLine();
         
         System.out.println("Enter cell phone number:");
         String cellPhoneNumber = sc.nextLine();
         
         String regMessage = user.registerUser(username, password , cellPhoneNumber, firstname);
         System.out.println(regMessage);
         
         
        if (user.CheckUserName(username) && user.CheckPasswordComplexity(password) && user.CheckCellPhoneNumber(cellPhoneNumber)){
            System.out.println("\n---Login ---");
            System.out.println("Enter username:");
            String loginUser = sc.nextLine();
            
            System.out.println("Enter password");
            String loginPass = sc.nextLine();
            
            boolean isSuccess = user.loginUser(loginUser, loginPass);
            System.out.println(user.returnLoginStatus(isSuccess));
          
        
        }
    }
    

}



