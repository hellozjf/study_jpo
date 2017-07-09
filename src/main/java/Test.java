import java.util.*; //Use the system's own data structure
import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import matrix.db.*;
import matrix.util.*;

//public class ${CLASSNAME} extends ${CLASS:emxDomainObject}{
public class Test extends DomainObject {
    // public ${CLASSNAME}(Context context,String[] args) throws Exception{
    // super(context,args);
    // }
    public Vector getOIDs(Context context, String[] args) throws Exception {
        // get an object list which based on args
        HashMap paramMap = (HashMap) JPO.unpackArgs(args);
        MapList objectList = (MapList) paramMap.get("objectList");

        // Create an object array. The value of the object array will be
        // display in the column
        Vector oids = new Vector(objectList.size());
        // Look all the objects in the object list
        for (Iterator iter = objectList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // Get object's id ,and add it into the object array
            String oid = (String) map.get(DomainConstants.SELECT_ID);
            oids.addElement(oid);
        }
        // Return the object array
        return oids;
    }

    public MapList getAllParts(Context context, String[] args)
            throws Exception {
        // Create a select which equals to MQL's select id
        StringList objectSelects = new StringList();
        objectSelects.add(DomainConstants.SELECT_ID);

        // Find all the BOs whose Type is BmbimPart
        MapList mapList = DomainObject.findObjects(context, "BmbimPart", "*",
                "", objectSelects);

        // Create an objectList ,which add the map object from mapList
        MapList objectList = new MapList();
        for (Iterator iter = mapList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // String id=(Stirng) map.get(DomainConstants.SELECT_ID);
            // After getting onject'id ,you cna do more thing
            objectList.add(map);
        }
        return objectList;
    }
}