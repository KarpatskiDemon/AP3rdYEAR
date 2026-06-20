import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private UserIdentifier identifier;
    private String password;
    private LocalDateTime lastLoginDate;
    private LocalDateTime registrationDate;
    private transient boolean isLoggedIn;

    public User(UserIdentifier identifier, String password) {
        this.identifier = identifier;
        this.password = password;
        this.lastLoginDate = null;
        this.registrationDate = LocalDateTime.now();
        this.isLoggedIn = false;
    }

    public UserIdentifier getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
