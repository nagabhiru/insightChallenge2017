

import java.util.Comparator;

public class RecipientWithDateComp implements Comparator<RecipientWithDate> {
	
    @Override
    public int compare(RecipientWithDate e1, RecipientWithDate e2) {
    	if (e1.getName().equals(e2.getName())) {
    		return e1.getDate().compareTo(e2.getDate());
    	} else 
    		return e1.getName().compareTo(e2.getName());
    }
}
