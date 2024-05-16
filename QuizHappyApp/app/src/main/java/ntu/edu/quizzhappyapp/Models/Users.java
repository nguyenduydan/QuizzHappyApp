package ntu.edu.quizzhappyapp.Models;

public class Users {
    public int userID;
    public String username;
    public String email;
    public String password;
    public String avatar;

    public Users() {
    }

    public Users(int userID, String username, String email, String password, String avatar) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
