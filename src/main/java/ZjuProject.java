import java.text.DecimalFormat;
import java.util.*; //Use the system's own data structure
import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import matrix.db.*;
import matrix.util.*;

//public class ${CLASSNAME} extends ${CLASS:emxDomainObject}{
public class ZjuProject extends DomainObject {
    
    // public ${CLASSNAME}(Context context,String[] args) throws Exception{
    // super(context,args);
    // }
    
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
                    case "ZjuPartUnitPrice":
                        quantity = attribute.getValue();
                        break;
                    case "ZjuPartQuantity":
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
}