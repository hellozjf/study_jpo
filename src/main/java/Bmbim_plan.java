import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.util.MapList;

import matrix.db.Context;
import matrix.db.JPO;
import matrix.util.StringList;

// public class ${CLASSNAME} extends ${CLASS:emxDomainObject} {
public class Bmbim_plan {

    // public ${CLASSNAME}(Context context, String[] args) throws Exception {
    // super(context, args);
    // }

    public Vector getOIDs(Context context, String[] args) throws Exception {
        // Get an object list which based on args.
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        // Create an object array. The value of the object array will be
        // displayed in the column.
        Vector oids = new Vector(objectList.size());
        // Look all the objects in the object list.
        for (Iterator iter = objectList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // Get object's id, and add it into the object array.
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            oids.addElement(oid);
        }
        // Return the object array.
        return oids;
    }

    public Vector getActualTotalAmount(Context context, String[] args) throws Exception {
        // Get an object list which based on args.
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        // Create an object array. The value of the object array will be
        // displayed in the column.
        Vector actualTotalAmount = new Vector(objectList.size());
        // Look all the objects in the object list.
        for (Iterator iter = objectList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // Get object's id, and add it into the object array.
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            DomainObject domainObject = DomainObject.newInstance(context, oid);
            String year = domainObject.getInfo(context, "attribute[Bmbim_plan_year]");

            // Search All Receipts
            double rec = 0;
            StringList objectSelects = new StringList();
            objectSelects.add(DomainConstants.SELECT_ID);
            MapList contracts = DomainObject.findObjects(context, "Bmbim_contract", "*", "", objectSelects);
            for (Iterator iter2 = contracts.iterator(); iter2.hasNext();) {
                Map map2 = (Map) iter2.next();
                String oid2 = (String) map2.get(DomainConstants.SELECT_ID);
                DomainObject domainObject2 = DomainObject.newInstance(context, oid2);
                String contract_totalamount = domainObject2.getInfo(context, "attribute[Bmbim_contract_totalamount]");
                String contract_clientsign = domainObject2.getInfo(context, "attribute[Bmbim_contract_clientsign]");
                if (contract_clientsign.contains(year)) {
                    rec += Double.parseDouble(contract_totalamount);
                }
            }

            actualTotalAmount.addElement(String.valueOf(rec));
        }
        // Return the object array.
        return actualTotalAmount;
    }

    public Vector getActualTotalPayment(Context context, String[] args) throws Exception {
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        // Create an object array. The value of the object array will be
        // displayed in the column.
        Vector actualTotalPayment = new Vector(objectList.size());
        // Look all the objects in the object list.
        for (Iterator iter = objectList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // Get object's id, and add it into the object array.
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            DomainObject domainObject = DomainObject.newInstance(context, oid);
            String year = domainObject.getInfo(context, "attribute[Bmbim_plan_year]");
            double rec = 0;

            // Search All Receipts
            StringList objectSelects = new StringList();
            objectSelects.add(DomainConstants.SELECT_ID);
            MapList contracts = DomainObject.findObjects(context, "Bmbim_contract", "*", "", objectSelects);
            for (Iterator iter2 = contracts.iterator(); iter2.hasNext();) {
                Map map2 = (Map) iter2.next();
                String oid2 = (String) map2.get(DomainConstants.SELECT_ID);
                DomainObject domainObject2 = DomainObject.newInstance(context, oid2);
                String contract_clientsign = domainObject2.getInfo(context, "attribute[Bmbim_contract_clientsign]");

                if (contract_clientsign.contains(year)) {
                    StringList objectSelects3 = new StringList();
                    objectSelects3.add(DomainConstants.SELECT_ID);
                    MapList receipts = DomainObject.findObjects(context, "Bmbim_receipt", "*",
                            "attribute[Bmbim_poid]==" + oid2, objectSelects3);
                    for (Iterator iter3 = receipts.iterator(); iter3.hasNext();) {
                        Map map3 = (Map) iter3.next();
                        String oid3 = (String) map3.get(DomainConstants.SELECT_ID);
                        DomainObject domainObject3 = DomainObject.newInstance(context, oid3);
                        String name3 = domainObject3.getInfo(context, "name");
                        String poid = domainObject3.getInfo(context, "attribute[Bmbim_poid]");
                        String actualmoney = domainObject3.getInfo(context, "attribute[Bmbim_receipt_actualmoney]");
                        System.out.println("hellozjf: " + " name3:" + name3 + " poid:" + poid + " oid2:" + oid2
                                + " actualmoney:" + actualmoney);
                        rec += Double
                                .parseDouble(domainObject3.getInfo(context, "attribute[Bmbim_receipt_actualmoney]"));
                    }
                }
            }

            actualTotalPayment.addElement(String.valueOf(rec));
        }
        // Return the object array.
        return actualTotalPayment;
    }

    public Vector getTotalAmountPecentage(Context context, String[] args) throws Exception {
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        // Create an object array. The value of the object array will be
        // displayed in the column.
        Vector actualTotalAmountPecentage = new Vector(objectList.size());
        Vector actualTotalAmount = getActualTotalAmount(context, args);
        // Look all the objects in the object list.
        for (Iterator iter = objectList.iterator(), iter2 = actualTotalAmount.iterator(); iter.hasNext()
                && iter2.hasNext();) {
            Map map = (Map) iter.next();
            String actual = (String) iter2.next();
            // Get object's id, and add it into the object array.
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            DomainObject domainObject = DomainObject.newInstance(context, oid);
            String plan = domainObject.getInfo(context, "attribute[Bmbim_plan_totalamount]");

            // System.out.println("hellozjf: " + "actual:" + actual + " plan:" + plan + "
            // oid:" + oid);
            actualTotalAmountPecentage
                    .addElement(String.valueOf(Double.parseDouble(actual) / Double.parseDouble(plan) * 100) + "%");
        }
        return actualTotalAmountPecentage;
    }

    public Vector getTotalPaymentPecentage(Context context, String[] args) throws Exception {
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        // Create an object array. The value of the object array will be
        // displayed in the column.
        Vector actualTotalPaymentPecentage = new Vector(objectList.size());
        Vector actualTotalPayment = getActualTotalPayment(context, args);
        // Look all the objects in the object list.
        for (Iterator iter = objectList.iterator(), iter2 = actualTotalPayment.iterator(); iter.hasNext()
                && iter2.hasNext();) {
            Map map = (Map) iter.next();
            String actual = (String) iter2.next();
            // Get object's id, and add it into the object array.
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            DomainObject domainObject = DomainObject.newInstance(context, oid);
            String plan = domainObject.getInfo(context, "attribute[Bmbim_plan_totalpayment]");

            // System.out.println("hellozjf: " + "actual:" + actual + " plan:" + plan + "
            // oid:" + oid);
            actualTotalPaymentPecentage
                    .addElement(String.valueOf(Double.parseDouble(actual) / Double.parseDouble(plan) * 100) + "%");
        }
        return actualTotalPaymentPecentage;
    }
}
