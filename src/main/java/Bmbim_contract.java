import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.util.MapList;

import matrix.db.Context;
import matrix.db.JPO;
import matrix.util.StringList;

// public class ${CLASSNAME} extends ${CLASS:emxDomainObject} {
public class Bmbim_contract extends DomainObject {

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
    
    public Vector getReceipts(Context context, String[] args) throws Exception {
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
            oids.addElement("Manage");
        }
        // Return the object array.
        return oids;
    }
    
    public Vector getProjects(Context context, String[] args) throws Exception {
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
            oids.addElement("Manage");
        }
        // Return the object array.
        return oids;
    }
    
    public Vector getStates(Context context, String[] args) throws Exception {
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
            oids.addElement("Manage");
        }
        // Return the object array.
        return oids;
    }

    public Vector getReceived(Context context, String[] args) throws Exception {
        // Get an object list which based on args.
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        // Create an object array. The value of the object array will be
        // displayed in the column.
        Vector received = new Vector(objectList.size());
        // Look all the objects in the object list.
        for (Iterator iter = objectList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // Get object's id, and add it into the object array.
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            
            // Search All Receipts
            double rec = 0;
            StringList objectSelects = new StringList();
            objectSelects.add(DomainConstants.SELECT_ID);
            MapList receipts = DomainObject.findObjects(context, "Bmbim_receipt", "*", "attribute[Bmbim_poid]==" + oid, objectSelects);
            for (Iterator iter2 = receipts.iterator(); iter2.hasNext(); ) {
                Map map2 = (Map) iter2.next();
                String oid2 = (String) map2.get(DomainConstants.SELECT_ID);
                DomainObject domainObject = DomainObject.newInstance(context, oid2);
                String name2 = domainObject.getInfo(context, "name");
                String poid = domainObject.getInfo(context, "attribute[Bmbim_poid]");
                String actualmoney = domainObject.getInfo(context, "attribute[Bmbim_receipt_actualmoney]");
                rec += Double.parseDouble(domainObject.getInfo(context, "attribute[Bmbim_receipt_actualmoney]"));
            }
            
            received.addElement(String.valueOf(rec));
        }
        // Return the object array.
        return received;
    }

    public MapList getAllRelatedReceipts(Context context, String[] args) throws Exception {
        // Find domainObject by objectId
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        Iterator iter = paramMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println("hellozjf: " + key + ":" + val);
        }
        String objectId = (String) paramMap.get("objectId");
        DomainObject domainObject = DomainObject.newInstance(context, objectId);
        // Get domainObject relatedObjects
        StringList objectSelects = new StringList();
        objectSelects.add(DomainConstants.SELECT_ID);
        MapList mapList = domainObject.getRelatedObjects(context, "*", "*", objectSelects, new StringList(), false,
                true, (short) 0, null, null, 0);
        return mapList;
    }
}