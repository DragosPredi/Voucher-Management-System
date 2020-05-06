import Vouchers.GiftVoucher;
import Vouchers.LoyaltyVoucher;
import Vouchers.Voucher;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Campaign {
    private Integer campaignId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer totalNrVouchers;
    private Integer availableVouchers;
    private CampaignStatusType campaignStatusType;
    private CampaignVoucherMap campaignVoucherMap;
    private Vector<User> observers;
    private String strategy;
    private static Integer voucherIDbegin = 1;

    public Campaign(Integer campaignId, String name, String description, Date startDate,
                    Date endDate, Integer totalNrVouchers, Integer availableVouchers, CampaignStatusType campaignStatusType,
                    String strategy) {
        this.campaignId = campaignId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalNrVouchers = totalNrVouchers;
        this.availableVouchers = availableVouchers;
        this.campaignStatusType = campaignStatusType;
        this.strategy = strategy;
        campaignVoucherMap = new CampaignVoucherMap();
        observers = new Vector<>();
    }

    public void addObserver(User user){
        if(!observers.contains(user))
            observers.add(user);
    }

    public Vector<Voucher> getVouchers(){
        ArrayList<ArrayMap<String, Vector<Voucher>>.ArrayMapEntry> e = campaignVoucherMap.getCollection();
        Vector<Voucher> campaignVouchers = new Vector<>();
        for(User users : observers){
            for (ArrayMap<String, Vector<Voucher>>.ArrayMapEntry arrayMapEntry : e) {
                if (arrayMapEntry.getKey().equals(users.getEmail())) {
                    Vector<Voucher> userVoucher = arrayMapEntry.getValue();
                    for (Voucher voucher : userVoucher) {
                        if (voucher.getCampaignId().equals(campaignId))
                            campaignVouchers.add(voucher);
                    }
                }
            }
        }

        return campaignVouchers;
    }
    public void generateVoucher(String email, String voucherType, float value){
        if(voucherType.equals("GiftVoucher")){
            GiftVoucher giftVoucher = new GiftVoucher((voucherIDbegin++), campaignId, email, value);
            VMS vms = VMS.getInstance();
            User user = vms.getUser(email);
            addObserver(user);
            campaignVoucherMap.addVoucher(giftVoucher);
        }
        if(voucherType.equals("LoyaltyVoucher")){
            LoyaltyVoucher loyaltyVoucher = new LoyaltyVoucher((getVouchers().size() + 1), campaignId, email, value);
            VMS vms = VMS.getInstance();
            User user = vms.getUser(email);
            observers.add(user);
            campaignVoucherMap.addVoucher(loyaltyVoucher);
        }
    }
    public void redeemVoucher(String code, Date date){
        Voucher voucher = getVouchers().get(Integer.parseInt(code));
        if(date.compareTo(startDate) > 0 && date.compareTo(endDate) < 0
        && !(voucher.isUsed())){
            voucher.setDateOfUtilization(date);
            voucher.using();
        }
    }
    public Vector<Voucher> getAllVouchersOfUser(User user){
        Vector<Voucher> vector = new Vector<>();
        ArrayList<ArrayMap<String, Vector<Voucher>>.ArrayMapEntry> e = campaignVoucherMap.getCollection();
        for (ArrayMap<String, Vector<Voucher>>.ArrayMapEntry arrayMapEntry : e){
            if(arrayMapEntry.getKey().equals(user.getEmail()))
                vector = arrayMapEntry.getValue();
        }
        return vector;
    }
    public Integer getCampaignId() {
        return campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalNrVouchers() {
        return totalNrVouchers;
    }

    public void setTotalNrVouchers(Integer totalNrVouchers) {
        this.totalNrVouchers = totalNrVouchers;
    }

    public Integer getAvailableVouchers() {
        return availableVouchers;
    }

    public void setAvailableVouchers(Integer availableVouchers) {
        this.availableVouchers = availableVouchers;
    }

    public CampaignStatusType getCampaignStatusType() {
        return campaignStatusType;
    }

    public void setCampaignStatusType(CampaignStatusType campaignStatusType) {
        this.campaignStatusType = campaignStatusType;
    }

    public Vector<User> getObservers() {
        return observers;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "name='" + name + '\'' +
                ", campaignStatusType=" + campaignStatusType +
                '}';
    }
    public void notifyAllObservers(Notification notification){
        Vector<User> users = getObservers();
        for(User user : users){
            Vector<Voucher> vouchers = getVouchers();
            user.addNotification(notification);
            for(Voucher voucher : vouchers)
                notification.addVoucherCode(voucher.getVoucherCode());
        }
    }
}
