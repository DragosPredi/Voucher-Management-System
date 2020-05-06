import java.util.Vector;

public class User {
    private Integer userID;
    private String name;
    private String password;
    Vector<Notification> notifications;
    private String email;
    private UserType userType;
    private UserVoucherMap voucherMap;

    public User(Integer userID, String name,
                String password, String email, UserType userType) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.userType = userType;
        notifications = new Vector<>();
    }

    public void getNotifications() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for(Notification not : notifications){
            str.append("[").append(not.getCampaignId()).
                    append(";[").append(userID).append("];").
                    append(not.getDateOfNotification().toString()).
                    append(";").append(not.getNotificationType())
                    .append("]");
        }
        str.append("]");
        System.out.println(str.toString());
    }

    public void addNotification(Notification notification){
        if(!notifications.contains(notification))
            notifications.add(notification);
    }
    public Integer getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "[" +userID + ";"
                + name + ";"
                + email + ";"
                + userType + "]";
    }
}
