import Vouchers.Voucher;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Test {
    public static VMS vmsObject = VMS.getInstance();

    public static CampaignStatusType getCampaignStatus(Date start, Date end){
        if(end.compareTo(vmsObject.getAppDate()) < 0)
            return CampaignStatusType.EXPIRED;
        if(start.compareTo(vmsObject.getAppDate()) > 0)
            return CampaignStatusType.NEW;
        if(start.compareTo(vmsObject.getAppDate()) < 0)
            return CampaignStatusType.STARTED;
        return null;
    }
    public static void readUsers() throws IOException {
        File file = new File("test00/input/users.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st = br.readLine();
        String[] elements;
        for(int i = 0 ; i < Integer.parseInt(st) ; i++){
            elements = br.readLine().split(";");
            if(elements[4].equals("GUEST"))
                vmsObject.addUser(new User(Integer.parseInt(elements[0]), elements[1],
                    elements[2], elements[3], UserType.GUEST));
            if(elements[4].equals("ADMIN"))
                vmsObject.addUser(new User(Integer.parseInt(elements[0]), elements[1],
                        elements[2], elements[3], UserType.ADMIN));
        }
    }
    public static void readCampaigns() throws IOException, ParseException {
        File file = new File("test00/input/campaigns.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st = br.readLine();
        int nrCampaigns = Integer.parseInt(st);
        st = br.readLine();
        Date appDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(st);
        vmsObject.setAppDate(appDate);
        String[] elements;
        for(int i = 0 ; i < nrCampaigns ; i++){
            elements = br.readLine().split(";");
            Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(elements[3]);
            Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(elements[4]);

            CampaignStatusType status = getCampaignStatus(start, end);

            vmsObject.addCampaigns(new Campaign(Integer.parseInt(elements[0]), elements[1],
                    elements[2], start, end, Integer.parseInt(elements[5]),
                    Integer.parseInt(elements[5]), status, elements[6]));
        }
    }
    public static void readAndExecuteEvents() throws IOException, ParseException {
        File file = new File("test00/input/events.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        vmsObject.setAppDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(br.readLine()));
        int nrOfEvent = Integer.parseInt(br.readLine());
        String[] elements;
        for(int i = 0 ; i < nrOfEvent ; i++){
            elements = br.readLine().split(";");
            User user = vmsObject.getUser(Integer.parseInt(elements[0]));
            String action = elements[1];

            switch (action){
                case "addCampaign":
                    if(user.getUserType().equals(UserType.ADMIN)) {
                        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(elements[5]);
                        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(elements[6]);
                        Integer nrOfVouchers = Integer.parseInt(elements[7]);
                        CampaignStatusType status = getCampaignStatus(start, end);
                        vmsObject.addCampaigns(new Campaign(Integer.parseInt(elements[2]), elements[3],
                                elements[4], start, end, nrOfVouchers, nrOfVouchers, status, elements[8]));
                    }
                    break;
                case "editCampaign":
                    if(user.getUserType().equals(UserType.ADMIN)) {
                        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(elements[5]);
                        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(elements[6]);
                        Integer nrOfVouchers = Integer.parseInt(elements[7]);
                        Campaign campaign = new Campaign(null,elements[3], elements[4],
                                start, end,nrOfVouchers, null, null, null);
                        vmsObject.updateCampaign(Integer.parseInt(elements[2]), campaign);
                    }
                    break;
                case "cancelCampaign":
                    if(user.getUserType().equals(UserType.ADMIN)){
                        Integer campaignID = Integer.parseInt(elements[2]);
                        vmsObject.cancelCampaign(vmsObject.getCampaign(campaignID).getCampaignId());
                    }
                    break;
                case "generateVoucher":
                    if(user.getUserType().equals(UserType.ADMIN)){
                        Campaign campaign = vmsObject.getCampaign(Integer.parseInt(elements[2]));
                        campaign.generateVoucher(elements[3], elements[4], Float.parseFloat(elements[5]));
                    }
                    break;
                case "redeemVoucher":
                    if(user.getUserType().equals(UserType.ADMIN)){
                        Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(elements[4]);
                        vmsObject.getCampaign(Integer.parseInt(elements[2])).redeemVoucher(elements[3], localDate);
                    }
                    break;
                case "getVouchers":
                    if(user.getUserType().equals(UserType.GUEST)){
                        Vector<Voucher> allVouchers = new Vector<>();
                        for(Campaign campaign : vmsObject.getCampaigns()){
                            allVouchers.addAll(campaign.getAllVouchersOfUser(user));
                        }
                        System.out.println(allVouchers);
                    }
                    break;
                case "getObservers":
                    if(user.getUserType().equals(UserType.ADMIN)){
                        System.out.println(vmsObject.getCampaign(Integer.parseInt(elements[2])).getObservers());
                    }
                    break;
                case "getNotifications":
                    if(user.getUserType().equals(UserType.GUEST)) {
                        user.getNotifications();
                    }
                    break;
            }
        }

    }
    public static void main(String[] args) throws ParseException, IOException {
        readUsers();
        readCampaigns();
        readAndExecuteEvents();
    }
}
