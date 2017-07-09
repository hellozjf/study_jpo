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

public class ZjuJPOTest extends DomainObject {
	
	public Vector getOIDs(Context context, String[] args) throws Exception {
		// Find objectList
		HashMap paramMap = (HashMap) JPO.unpackArgs(args);
		MapList objectList = (MapList) paramMap.get("objectList");
		
		// Return oids
		Vector oids = new Vector(objectList.size());
		for (Iterator iter = objectList.iterator(); iter.hasNext();) {
			Map map = (Map) iter.next();
			String oid = (String) map.get(DomainConstants.SELECT_ID);
			oids.addElement(oid);
		}
		return oids;
	}
	
	public MapList getAllZjuProjects(Context context, String[] args) throws Exception {
        // Select id
        StringList busSelects = new StringList();
        busSelects.add(DomainConstants.SELECT_ID);
        
        // Find all ZjuParts
        MapList mapList = DomainObject.findObjects(context, "ZjuProject", "*", "", busSelects);
        
        // Return all ZjuPart objects
        MapList objectList = new MapList();
        for (Iterator iter = mapList.iterator(); iter.hasNext();) {
            Map map = (Map) iter.next();
            // String id = (String) map.get(DomainConstants.SELECT_ID);
            objectList.add(map);
        }
        return objectList;
    }

	public MapList getAllRelatedParts(Context context, String[] args) throws Exception {
		// Find domainObject by objectId
		HashMap paramMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) paramMap.get("objectId");
		DomainObject domainObject = DomainObject.newInstance(context, objectId);
		
		// Get domainObject relatedObjects
		StringList objectSelects = new StringList();
		objectSelects.add(DomainConstants.SELECT_ID);
		MapList mapList = domainObject.getRelatedObjects(context, "*", "*", objectSelects, new StringList(), false, true, (short) 0, null, null, 0);
		return mapList;
	}
	
	public MapList getAllParts(Context context, String[] args) throws Exception {
		// Select id
		StringList busSelects = new StringList();
		busSelects.add(DomainConstants.SELECT_ID);
		
		// Find all ZjuParts
		MapList mapList = DomainObject.findObjects(context, "ZjuPart", "*", "", busSelects);
		
		// Return all ZjuPart objects
		MapList objectList = new MapList();
		for (Iterator iter = mapList.iterator(); iter.hasNext();) {
			Map map = (Map) iter.next();
			// String id = (String) map.get(DomainConstants.SELECT_ID);
			objectList.add(map);
		}
		return objectList;
	}
}
