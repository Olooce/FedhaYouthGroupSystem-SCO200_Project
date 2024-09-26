package ac.ku.oloo.models;

import java.sql.Timestamp;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 26/09/2024. 23:36
 * Description:
 **/

public class User {

    private long userId;
    private String username;
    private String passwordHash;
    private String role;
    private long memberId;
    private Timestamp dateCreated;
    private Timestamp dateModified;

    // Constructors
    public User() {
    }

    public User(int userId, String username, String passwordHash, String role, int memberId, Timestamp dateCreated, Timestamp dateModified) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.memberId = memberId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    // Getters and Setters
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role='" + role + '\'' +
                ", memberId=" + memberId +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                '}';
    }
}