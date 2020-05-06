package Vouchers;

public class LoyaltyVoucher extends Voucher{
    private Float discount;

    public LoyaltyVoucher(Integer voucherId, Integer campaignId, String userEmail, float discount) {
        super(voucherId, campaignId, userEmail);
        this.discount = Float.parseFloat(String.valueOf(discount));
    }
    @Override
    public String toString() {
        return "[" + getVoucherCode() + ";"
                + getVoucherStatusType() + ";"
                + getUserEmail() + ";"
                + discount + ";"
                + getDateOfUtilization();
    }
}
