import java.util.*; //Use the system's own data structure
import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import matrix.db.*;
import matrix.util.*;

//public class ${CLASSNAME} extends ${CLASS:emxDomainObject}{
public class ZjuBridge extends DomainObject {
    
    // public ${CLASSNAME}(Context context,String[] args) throws Exception{
    // super(context,args);
    // }
    
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
}