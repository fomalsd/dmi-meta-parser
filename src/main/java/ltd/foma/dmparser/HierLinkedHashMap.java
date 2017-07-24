package ltd.foma.dmparser;

import com.google.gson.internal.LinkedHashTreeMap;

import java.util.LinkedHashMap;

/**
 * Created by Foma.ltd on 26.05.2017.
 */
public class HierLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Comparable {
    @Override
    public int compareTo(Object o) {
        HierLinkedHashMap om = (HierLinkedHashMap)o;
        String key = "hierarchy";
        String o1 = (String) this.get(key);
        String o2 = (String) om.get(key);
        return o1.compareTo(o2);
    }

}
