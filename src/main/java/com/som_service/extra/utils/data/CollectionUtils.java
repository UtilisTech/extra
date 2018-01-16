/*
 * MIT License
 * 
 * Copyright (c) 2017 Eugene Dementiev
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.som_service.extra.utils.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.som_service.extra.utils.callback.Callback;

/**
 *
 * @author Eugene Dementiev
 */

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
