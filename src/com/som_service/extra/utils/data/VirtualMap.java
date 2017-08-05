/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;

/**
 *
 * @author Asuka
 */
public class VirtualMap<K,V extends Object> {
	
	public final V default_value;
	public final TreeMap<K, V> map;
	
	public VirtualMap(V default_value){
		this.default_value = default_value;
		map = new TreeMap<>();
		
	}
	
	public VirtualMap(V default_value, TreeMap<K, V> map){
		this.default_value = default_value;
		this.map = map;
	}
	
	public V get(K key){
		if (!map.containsKey(key)){
			try {
				map.put(key, (V)default_value.getClass().getMethod("clone").invoke(default_value));
			}
			catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
				throw new RuntimeException(e);
			}
		}
		return map.get(key);
	}

}
