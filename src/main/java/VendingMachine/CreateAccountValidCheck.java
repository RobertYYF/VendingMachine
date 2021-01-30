package VendingMachine;

import java.util.List;

public class CreateAccountValidCheck {

    public static boolean checkExistence(List<User> users, String username) {
        boolean result = false;
        for(User user: users) {
            if(user.getUsername().equals(username)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
