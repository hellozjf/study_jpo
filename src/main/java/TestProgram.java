import java.util.*; // Use the system's own data structure
import java.util.List;
import java.text.*;
import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import matrix.db.*;
import matrix.util.*;

public class TestProgram extends DomainObject {

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

    public Vector getMoneys(Context context, String[] args) throws Exception {
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        Vector moneys = new Vector(objectList.size());
        for (Iterator iter = objectList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            DomainObject domainObject = DomainObject.newInstance(context, oid);
            AttributeList attributeList = domainObject
                    .getAttributeValues(context);

            String unitPrice = null;
            String quantity = null;
            for (Iterator iter2 = attributeList.iterator(); iter2.hasNext();) {
                Attribute attribute = (Attribute) iter2.next();
                switch (attribute.getName()) {
                    case "TestPartQuantity":
                        quantity = attribute.getValue();
                        break;
                    case "TestPartUnitPrice":
                        unitPrice = attribute.getValue();
                        break;
                }
            }
            if (unitPrice != null && quantity != null) {
                double d1 = Double.parseDouble(unitPrice);
                double d2 = Double.parseDouble(quantity);
                double result = d1 * d2;
                moneys.addElement(new DecimalFormat("#.00").format(result));
            } else {
                moneys.addElement("");
            }
        }
        return moneys;
    }

    public MapList getAllRelatedParts(Context context, String[] args)
            throws Exception {
        // Find domainObject by objectId
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        String objectId = (String) paramMap.get("objectId");
        DomainObject domainObject = DomainObject.newInstance(context, objectId);

        // Get domainObject relatedObjects
        StringList objectSelects = new StringList();
        objectSelects.add(DomainConstants.SELECT_ID);
        MapList mapList = domainObject.getRelatedObjects(context, "*", "*",
                objectSelects, new StringList(), false, true, (short) 0, null,
                null, 0);
        return mapList;
    }

    public MapList getAllParts(Context context, String[] args)
            throws Exception {
        // Create a select which equals to MQL's select id.
        StringList objectSelects = new StringList();
        objectSelects.add(DomainConstants.SELECT_ID);

        // Find all the BOs whose Type is TestPart.
        MapList mapList = DomainObject.findObjects(context, "TestPart", "*", "",
                objectSelects);

        // Create an objectList, which add the map object from mapList.
        MapList objectList = new MapList();
        for (Iterator iter = mapList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // String id = (String) map.get(DomainConstants.SELECT_ID);
            // After getting object's id, you can do more things.
            objectList.add(map);
        }
        return objectList;
    }
    
    public Vector getEngineeringProject(Context context, String[] args) throws Exception {
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
            
            DomainObject domainObject = DomainObject.newInstance(context, oid);
            AttributeList attributeList = domainObject
                    .getAttributeValues(context);

            String engineeringProject = "";
            for (Iterator iter2 = attributeList.iterator(); iter2.hasNext();) {
                Attribute attribute = (Attribute) iter2.next();
                switch (attribute.getName()) {
                    case "EngineeringProject":
                        engineeringProject = attribute.getValue();
                        break;
                }
            }
            oids.addElement(engineeringProject);
        }
        // Return the object array.
        return oids;
    }
    
    public Vector getDetail1_Name(Context context, String[] args) throws Exception {
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
//            String oid = (String) map.get(DomainConstants.SELECT_ID);
//            oids.addElement(oid);
            String s = (String) map.get("IfcDetail1_Name");
            if (s == null) {
                s = "";
            }
            oids.addElement(s);
        }
        // Return the object array.
        return oids;
    }
    
    public Vector getDetail1_UnitPrice(Context context, String[] args) throws Exception {
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
//            String oid = (String) map.get(DomainConstants.SELECT_ID);
//            oids.addElement(oid);
            String s = (String) map.get("IfcDetail1_UnitPrice");
            if (s == null) {
                s = "";
            }
            oids.addElement(s);
        }
        // Return the object array.
        return oids;
    }
    
    public Vector getDetail1_Quantity(Context context, String[] args) throws Exception {
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
//            String oid = (String) map.get(DomainConstants.SELECT_ID);
//            oids.addElement(oid);
            String s = (String) map.get("IfcDetail1_Quantity");
            if (s == null) {
                s = "";
            }
            oids.addElement(s);
        }
        // Return the object array.
        return oids;
    }
}
