/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

import java.util.HashMap;

/**
 *
 * @author Asuka
 */
public class VirtualAverageMap {
	
	public final HashMap<String, Average> map = new HashMap<>();
	
	public VirtualAverageMap(){
	}
	
	public Average get(String key){
		
		if (!map.containsKey(key)){
			map.put(key, new Average());
		}
		
		return map.get(key);
	}
	
	public String toString(){
		return map.toString();
	}
}
