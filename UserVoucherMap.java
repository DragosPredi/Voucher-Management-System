import Vouchers.Voucher;
import java.util.Vector;

public class UserVoucherMap extends ArrayMap<Integer, Vector<Voucher>> {
    public boolean addVoucher(Voucher v){
        if (getCollection().size() == 0) {
            Vector<Voucher> newVector = new Vector<>();
            newVector.add(v);
            return put(v.getCampaignId(), newVector) == null;
        } else {
            for (ArrayMapEntry e : getCollection()) {
                if (e.getKey().equals(v.getCampaignId())) {
                    Vector<Voucher> voucherCollection = e.getValue();
                    voucherCollection.add(v);
                    return put(v.getCampaignId(), voucherCollection) == null;
                }
            }

            Vector<Voucher> newVector = new Vector<>();
            newVector.add(v);
            return put(v.getCampaignId(), newVector) == null;
        }
    }
}