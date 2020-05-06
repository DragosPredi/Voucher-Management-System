import java.util.Date;
import java.util.Vector;

public class VMS {
    private Vector<Campaign> campaigns;
    private Vector<User> users;
    private Date appDate;

    private static VMS singleton = null;
    private VMS() {
        campaigns = new Vector<>();
        users = new Vector<>();
    }
    public static VMS getInstance(){
        if(singleton == null){
            singleton = new VMS();
        }
        return singleton;
    }
    public void addCampaigns(Campaign campaign){
        campaigns.add(campaign);
    }
    public Vector<Campaign> getCampaigns() {
        return campaigns;
    }
    public Campaign getCampaign(Integer id){
        for(Campaign e : campaigns){
            if(e.getCampaignId().equals(id))
                return e;
        }
        return null;
    }
    public void updateCampaign(Integer campaignId, Campaign campaign){
        Campaign originalCampaign = getCampaign(campaignId);
        originalCampaign.notifyAllObservers(new Notification
                (NotificationType.EDIT, appDate, campaignId));
        if(originalCampaign.getCampaignStatusType().equals(CampaignStatusType.NEW)){
            originalCampaign.setName(campaign.getName());
            originalCampaign.setDescription(campaign.getName());
            originalCampaign.setStartDate(campaign.getStartDate());
            originalCampaign.setEndDate(campaign.getEndDate());
            originalCampaign.setTotalNrVouchers(campaign.getTotalNrVouchers());
        }
        if(originalCampaign.getCampaignStatusType().equals(CampaignStatusType.STARTED)){
            Integer distributedVouchers = originalCampaign.getTotalNrVouchers() - originalCampaign.getAvailableVouchers();
            if(campaign.getTotalNrVouchers() - distributedVouchers > 0){
                originalCampaign.setTotalNrVouchers(campaign.getTotalNrVouchers());
                campaign.setAvailableVouchers(campaign.getTotalNrVouchers() - distributedVouchers);
            }
            campaign.setEndDate(campaign.getEndDate());
        }


    }
    public void cancelCampaign(Integer id){
        for(Campaign e : campaigns){
            if(e.getCampaignId().equals(id)){
                if(e.getCampaignStatusType().equals(CampaignStatusType.NEW)
                || e.getCampaignStatusType().equals(CampaignStatusType.STARTED))
                    e.notifyAllObservers(new Notification
                            (NotificationType.CANCEL, appDate, e.getCampaignId()));
                    e.setCampaignStatusType(CampaignStatusType.CANCELLED);
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
    }
    public Vector<User> getUsers() {
        return users;
    }
    public User getUser(Integer userId){
        for(User user : users){
            if(user.getUserID().equals(userId))
                return user;
        }
        return null;
    }
    public User getUser(String email){
        for(User user : users){
            if(user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    public Date getAppDate() {
        return appDate;
    }
    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }
}
