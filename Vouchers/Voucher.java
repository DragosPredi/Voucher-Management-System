package Vouchers;

import java.util.Date;

public abstract class Voucher {
    private enum VoucherStatusType{
        USED,
        UNUSED
    }
    private Integer voucherId;
    private Integer voucherCode;
    private Date dateOfUtilization;
    private String userEmail;
    private Integer campaignId;
    private VoucherStatusType voucherStatusType;

    public Voucher(Integer voucherId, Integer campaignId, String userEmail) {
        this.voucherId = voucherId;
        this.voucherCode = Integer.parseInt(String.valueOf((int)(Math.random() * 100)));
        this.dateOfUtilization = null;
        this.userEmail = userEmail;
        this.campaignId = campaignId;
        this.voucherStatusType = VoucherStatusType.UNUSED;
    }

    public Integer getVoucherCode() {
        return voucherCode;
    }

    public Date getDateOfUtilization() {
        return dateOfUtilization;
    }

    public void setDateOfUtilization(Date dateOfUtilization) {
        this.dateOfUtilization = dateOfUtilization;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public boolean isUsed(){
        return voucherStatusType.equals(VoucherStatusType.USED);
    }
    public void using(){
        voucherStatusType = VoucherStatusType.USED;
    }

    public VoucherStatusType getVoucherStatusType() {
        return voucherStatusType;
    }
}
