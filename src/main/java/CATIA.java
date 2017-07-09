import java.io.File;
import java.io.*;
import java.util.*;
import java.util.List;

import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import matrix.db.*;
import matrix.util.*;

// public class ${CLASSNAME} extends ${CLASS:emxDomainObject} {
public class CATIA extends DomainObject {

    // public ${CLASSNAME}(Context context, String[] args) throws Exception {
    // super(context, args);
    // }

    private List<DomainObject> getRelatedObjectList(Context context,
            DomainObject object) throws Exception {
        List<DomainObject> domainObjects = new ArrayList<DomainObject>();

        StringList objectSelects = new StringList();
        objectSelects.add(DomainConstants.SELECT_ID);
        MapList relatedMapList = object.getRelatedObjects(context, "*", "*",
                objectSelects, new StringList(), false, true, (short) 0, null,
                null, 0);
        for (Iterator iter = relatedMapList.iterator(); iter.hasNext();) {
            Map relatedMap = (Map) iter.next();
            String relatedOid = (String) relatedMap
                    .get(DomainConstants.SELECT_ID);
            DomainObject relatedObject = DomainObject.newInstance(context,
                    relatedOid);
            domainObjects.add(relatedObject);
        }
        return domainObjects;
    }
    
    private boolean isInExistObjects(Context context, List<DomainObject> existObjects, DomainObject object) {
        for (DomainObject existObject : existObjects) {
            if (existObject.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public void savePropertiesToFiles(Context context, String[] args)
            throws Exception {

        // Select id
        StringList busSelects = new StringList();
        busSelects.add(DomainConstants.SELECT_ID);

        // Find all Bridge
        MapList bridgeMapList = DomainObject.findObjects(context, "Bridge", "*",
                "", busSelects);

        File folder = new File("/home/webapp/data/catia");
        folder.mkdirs();

        // Return all Bridge objects
        for (Iterator iter = bridgeMapList.iterator(); iter.hasNext();) {
            Map bridgeMap = (Map) iter.next();
            String oid = (String) bridgeMap.get(DomainConstants.SELECT_ID);
//            String type = (String) bridgeMap.get(DomainConstants.SELECT_TYPE);
//            String name = (String) bridgeMap.get(DomainConstants.SELECT_NAME);
//            String revision = (String) bridgeMap
//                    .get(DomainConstants.SELECT_REVISION);
            File file = new File(
                    folder.getAbsolutePath() + File.separatorChar + oid);

            DomainObject bridgeObject = DomainObject.newInstance(context, oid);
            AttributeList attributeList = bridgeObject
                    .getAttributeValues(context);

            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
                writer.println("oid:" + bridgeObject.getInfo(context, DomainObject.SELECT_ID));
                writer.println("type:" + bridgeObject.getInfo(context, DomainObject.SELECT_TYPE));
                writer.println("name:" + bridgeObject.getInfo(context, DomainObject.SELECT_NAME));
                writer.println("revision:" + bridgeObject.getInfo(context, DomainObject.SELECT_REVISION));
                for (Iterator iter2 = attributeList.iterator(); iter2
                        .hasNext();) {
                    Attribute attribute = (Attribute) iter2.next();
                    writer.println(
                            attribute.getName() + ":" + attribute.getValue());
                }
                writer.println();

                // Get Bridge Releated Objects
                List<DomainObject> relatedObjects = new ArrayList<DomainObject>();
                relatedObjects.add(bridgeObject);
                List<DomainObject> relatedObjectList = getRelatedObjectList(context, bridgeObject);
                for (DomainObject relatedObject : relatedObjectList) {
                    if (! isInExistObjects(context, relatedObjects, relatedObject)) {
                        relatedObjects.add(relatedObject);
                    }
                }
//                relatedObjects.addAll(getRelatedObjectList(context, bridgeObject));
                for (int i = 1; i < relatedObjects.size(); i++) {
                    DomainObject relatedObject = relatedObjects.get(i);
                    writer.println("oid:" + relatedObject.getInfo(context, DomainObject.SELECT_ID));
                    writer.println("type:" + relatedObject.getInfo(context, DomainObject.SELECT_TYPE));
                    writer.println("name:" + relatedObject.getInfo(context, DomainObject.SELECT_NAME));
                    writer.println("revision:" + relatedObject.getInfo(context, DomainObject.SELECT_REVISION));
                    AttributeList relatedAttributeList = bridgeObject
                            .getAttributeValues(context);
                    for (Iterator iter2 = relatedAttributeList.iterator(); iter2
                            .hasNext();) {
                        Attribute attribute = (Attribute) iter2.next();
                        writer.println(
                                attribute.getName() + ":" + attribute.getValue());
                    }
                    writer.println();
                    relatedObjectList = getRelatedObjectList(context, relatedObject);
                    for (DomainObject object : relatedObjectList) {
                        if (! isInExistObjects(context, relatedObjects, object)) {
                            relatedObjects.add(object);
                        }
                    }
//                    relatedObjects.addAll();
//                    relatedObjects.remove(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    // public Vector getEngineeringProject(Context context, String[] args)
    // throws Exception {
    // return getValueByKey(context, args, "EngineeringProject");
    // }
    //
    // public Vector getDetail1_Name(Context context, String[] args)
    // throws Exception {
    // return getValueByKey(context, args, "Detail1_Name");
    // }
    //
    // public Vector getDetail1_Quantity(Context context, String[] args)
    // throws Exception {
    // return getValueByKey(context, args, "Detail1_Quantity");
    // }
    //
    // public Vector getDetail1_UnitPrice(Context context, String[] args)
    // throws Exception {
    // return getValueByKey(context, args, "Detail1_UnitPrice");
    // }
    //
    // public Vector getValueByKey(Context context, String[] args, String key)
    // throws Exception {
    // // Get an object list which based on args.
    // HashMap paramMap = (HashMap) JPO.unpackArgs(args);
    // MapList objectList = (MapList) paramMap.get("objectList");
    //
    // // Create an object array. The value of the object array will be
    // // displayed in the column.
    // Vector oids = new Vector(objectList.size());
    // // Look all the objects in the object list.
    // for (Iterator iter = objectList.iterator(); iter.hasNext();) {
    // Map map = (Map) iter.next();
    // // Get object's id, and add it into the object array.
    // String oid = (String) map.get(DomainConstants.SELECT_ID);
    //
    // DomainObject domainObject = DomainObject.newInstance(context, oid);
    // AttributeList attributeList = domainObject
    // .getAttributeValues(context);
    //
    // String s = "";
    // for (Iterator iter2 = attributeList.iterator(); iter2.hasNext();) {
    // Attribute attribute = (Attribute) iter2.next();
    // if (attribute.getName().contains(key)) {
    // s = attribute.getValue();
    // }
    // }
    // oids.addElement(s);
    // }
    // // Return the object array.
    // return oids;
    // }

}
