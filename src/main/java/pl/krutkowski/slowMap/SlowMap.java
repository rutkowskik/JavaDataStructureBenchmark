package org.example.slowMap;

import java.util.*;

public class SlowMap<K,V> extends AbstractMap<K,V> {

    private List<K> keys = new ArrayList<K>();
    private List<V> values = new ArrayList<V>();
    public V put(K key, V value) {
        V oldValue = get(key); //previous value or null
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else
            values.set(keys.indexOf(key), value);
        return oldValue;
    }
    public V get (Object key){
        if(!keys.contains(key))
            return null;
        return values.get(keys.indexOf(key));
    }
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<Map.Entry<K, V>>();
        Iterator<K> kIterator = keys.iterator();
        Iterator<V> vIterator = values.iterator();
        while(kIterator.hasNext())
            set.add(new MapEntry<K,V>(kIterator.next(), vIterator.next()));
        return set;
    }
}
