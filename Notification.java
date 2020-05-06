import java.util.Date;
import java.util.LinkedList;

public class Notification {

    private NotificationType notificationType;
    private Date dateOfNotification;
    private Integer campaignId;
    private LinkedList<Integer> voucherCodes;

    public Notification(NotificationType notificationType, Date dateOfNotification, Integer campaignId) {
        this.notificationType = notificationType;
        this.dateOfNotification = dateOfNotification;
        this.campaignId = campaignId;
        voucherCodes = new LinkedList<>();
    }
    public void addVoucherCode(Integer integer){
        if(!voucherCodes.contains(integer))
            voucherCodes.add(integer);
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public Date getDateOfNotification() {
        return dateOfNotification;
    }

    public Integer getCampaignId() {
        return campaignId;
    }
}
