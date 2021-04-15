import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class DAOGenerator {
    private DAO dao;
    public DAOGenerator(DAO dao) {
        this.dao = dao;
    }
    public Docket GenDocket(Date date, boolean deleteIfFound) throws DAOGeneratorExceptionHandler {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.MONTH, 1);
            Date invoiceDate = new Date(cal.getTimeInMillis());
            if(dao.getinvoicesByDate(invoiceDate) == null) {
                dao.createInvoicesForDate(invoiceDate);
            }
            ArrayList<DB_Subscription> subList = dao.getSubscriptionsForDate(date, true, true);
            //  DELETE old set if found could use some sort of method identification
            if (dao.getDeliveriesByDate(date) != null && deleteIfFound) {
                dao.deleteDeliveriesByDate(date);
            }
            ArrayList<DB_Delivery> delList = dao.createDeliveriesForSubscriptionDate(subList, date);
            return new Docket(subList, delList, date);
        } catch (Exception e) {
            throw new DAOGeneratorExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DeliveryDocket> splitDocket(Docket master) throws DAOGeneratorExceptionHandler {
        try {
            if (master.getDeliveries() != null && master.getDeliveries().size() > 0 && master.getSubscriptions() != null && master.getSubscriptions().size() > 0) {
                ArrayList<DeliveryDocket> dockets = new ArrayList<>();
                int[] areaList = dao.getAreasForDeliveries(master.getDeliveries());
                for (int i : areaList) {
                    DeliveryDocket doc = new DeliveryDocket();
                    doc.setArea_code(i);
                    doc.setDate(master.getDate());
                    dockets.add(doc);
                }
                for (DB_Delivery del : master.getDeliveries()) {
                    int area = dao.getDeliveryArea(del);
                    for (DeliveryDocket doc : dockets) {
                        if (doc.getArea_code() == area){
                            doc.add(del);
                            doc.add(master.getMatch(del));
                        }
                    }
                }
                return dockets;
            } else {
                throw new DAOGeneratorExceptionHandler("Invalid docket presented");
            }
        } catch (DAOExceptionHandler e) {
            throw new DAOGeneratorExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<String> genStats(DeliveryDocket docket) throws DAOGeneratorExceptionHandler {
        try {
            if (docket.getDeliveries() != null && docket.getDeliveries().size() > 0) {
                if (docket.getDeliveries().size() == docket.getSubscriptions().size()) {
                    ArrayList<DeliveryStat> deliveryStats = new ArrayList<>();
                    //  Compile set of delivery stats by grouping to addresses
                    for (DB_Delivery del : docket.getDeliveries()) {
                        boolean inserted = false;
                        for (DeliveryStat stat : deliveryStats) {
                            if (stat.getAddress() != null && stat.getAddress().getAddress_id() == dao.getAddressByDelivery(del).getAddress_id()) {
                                inserted = true;
                                stat.add(docket.getMatch(del));
                                stat.add(dao.getPublication((int) del.getProd_id()));
                                break;
                            }
                        }
                        if (!inserted) {
                            DeliveryStat stat = new DeliveryStat(dao.getAddressByDelivery(del));
                            deliveryStats.add(stat);
                            stat.add(docket.getMatch(del));
                            stat.add(dao.getPublication((int) del.getProd_id()));
                        }
                    }
                    //  Loop through each
                    ArrayList<String> stats = new ArrayList<>();
                    for(DeliveryStat stat : deliveryStats) {
                        stats.add(stat.toString());
                    }
                    return stats;
                } else {
                    throw new DAOGeneratorExceptionHandler("Internal error, subscription and delivery mismatch");
                }
            } else {
                throw new DAOGeneratorExceptionHandler("No details found in the Delivery Docket");
            }
        } catch (DAOExceptionHandler e) {
            throw new DAOGeneratorExceptionHandler( e.getMessage());
        }
    }
}

class DAOGeneratorExceptionHandler extends Exception {
    String message;
    public DAOGeneratorExceptionHandler(String errMessage) { message = errMessage; }
    public String getMessage() { return message; }
}