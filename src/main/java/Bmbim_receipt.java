import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.DomainRelationship;
import com.matrixone.apps.domain.util.MapList;

import matrix.db.Context;
import matrix.db.JPO;

// public class ${CLASSNAME} extends ${CLASS:emxDomainObject} {
public class Bmbim_receipt extends DomainObject {

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

    public Object createAndConnect(Context context, String[] args) throws Exception {
        HashMap programMap = (HashMap) JPO.unpackArgs(args);
        HashMap paramMap = (HashMap) programMap.get("paramMap");
        String objectId = (String) paramMap.get("objectId"); // Part ID
        String newRDOId = (String) paramMap.get("New OID"); // RDO Id
        String strContractToReceiptRelationship = "Bmbim_contract_to_receipt";
        // Connect the part to RDO
        if (!newRDOId.equals("")) {
            setId(newRDOId);
            DomainObject domainObjectToType = newInstance(context, objectId);
            // Part Object
            DomainObject domainObjectFromType = newInstance(context, newRDOId); // RDO Object
            DomainRelationship.connect(context, domainObjectFromType, strContractToReceiptRelationship,
                    domainObjectToType);
        }
        return new Boolean(true);
    }
}
