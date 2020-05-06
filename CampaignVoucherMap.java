import Vouchers.Voucher;
import java.util.Vector;

public class CampaignVoucherMap extends ArrayMap<String, Vector<Voucher>> {

    public void addVoucher(Voucher v) {
        if (getCollection().size() == 0) {
            Vector<Voucher> newVector = new Vector<>();
            newVector.add(v);
            put(v.getUserEmail(), newVector);

        } else {
            for (ArrayMapEntry e : getCollection()) {
                if (e.getKey().equals(v.getUserEmail())) {
                    Vector<Voucher> voucherCollection = e.getValue();
                    voucherCollection.add(v);
                    put(v.getUserEmail(), voucherCollection);
                    return;
                }
            }

            Vector<Voucher> newVector = new Vector<>();
            newVector.add(v);
            put(v.getUserEmail(), newVector);
        }
    }


}
