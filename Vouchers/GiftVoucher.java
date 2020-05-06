package Vouchers;


public class GiftVoucher extends Voucher {
    private Float moneyValue;


    public GiftVoucher(Integer voucherId, Integer campaignId, String userEmail, float moneyValue) {
        super(voucherId, campaignId, userEmail);
        this.moneyValue = Float.parseFloat(String.valueOf(moneyValue));
    }
    public String toString() {
        return "[" + getVoucherCode() + ";"
                + getVoucherStatusType() + ";"
                + getUserEmail() + ";"
                + moneyValue + ";"
                + getDateOfUtilization();
    }
}
