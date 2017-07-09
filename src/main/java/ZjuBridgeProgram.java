import java.util.*; // Use the system's own data structure
import java.util.List;
import java.text.*;
import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import matrix.db.*;
import matrix.util.*;

// public class ${CLASSNAME} extends ${CLASS:emxDomainObject} {
public class ZjuBridgeProgram extends DomainObject {

//    public ${CLASSNAME}(Context context, String[] args) throws Exception {
//        super(context, args);
//    }

    public Vector getTestString(Context context, String[] args)
            throws Exception {
        return getValueByKey(context, args, "TestString");
    }

    public Vector getValueByKey(Context context, String[] args, String key)
            throws Exception {
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

            String s = "";
            for (Iterator iter2 = attributeList.iterator(); iter2.hasNext();) {
                Attribute attribute = (Attribute) iter2.next();
                if (attribute.getName().contains(key)) {
                    s = attribute.getValue();
                }
            }
            oids.addElement(s);
        }
        // Return the object array.
        return oids;
    }

}
