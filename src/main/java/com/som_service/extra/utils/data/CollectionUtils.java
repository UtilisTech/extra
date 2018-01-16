/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.som_service.extra.utils.callback.Callback;

public class CollectionUtils {
	
	public static <T> List<T> list_add_sort_trim(List list, T element, int max_size){
		list.add(element);
		Collections.sort(list);
		list = list.subList(0, Math.min(max_size, list.size()));
		return list;
	}
	
	
	public static <K extends Comparable,V> String map_to_string_sorted(Map<K, V> map){
		return map_to_string_sorted(map, null);
	}
	
	public static <K extends Comparable,V> String map_to_string_sorted(Map<K, V> map, Callback callback){
		String string = "";
		
		ArrayList<K> keys = new ArrayList(map.keySet());
		Collections.sort(keys);
		
		if (callback == null){
			boolean first = true;
			for(K key: keys){
				String prefix = ", ";
				if (first){
					prefix = "";
					first = false;
				}

				string += prefix+key+"="+map.get(key);
			}

			return "{"+string+"}";
		}
		
		ArrayList<ObjectPair> ops = new ArrayList();
		for(K key: keys){
			ops.add(new ObjectPair(key, map.get(key)));
		}
		
		return (String)callback.callback(ops);
	}
}
