/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

import java.util.Arrays;
import java.util.HashMap;
import com.som_service.extra.utils.callback.Callback;

/**
 *
 * @author Asuka
 */
public class VirtualDoubleMap {
	
	public final HashMap<String, Double> map;
	
	public VirtualDoubleMap(){
		map = new HashMap();
	}
	public VirtualDoubleMap(HashMap<String,Double> map){
		this.map = map;
	}
	
	public VirtualValue get(String key){
		return new VirtualValue(map, key);
	}
	
	public String toString(){
		String string = "";
		
		String[] keys = map.keySet().toArray(new String[map.size()]);
		Arrays.sort(keys);
		for(String key: keys){
			string += key+"\t"+map.get(key)+"\n";
		}
		
		return string;
	}
	
	public VirtualDoubleMap normalise(){
		this.normalise(1d);
		return this;
	}
	
	public VirtualDoubleMap normalise(double base){
		double total = 0d;
		for(double d: map.values()){
			total += d;
		}
		
		for (String key: map.keySet()){
			double new_value = map.get(key)*base/total;
			map.put(key, new_value);
		}
		
		return this;
	}
	
	public VirtualDoubleMap each(Callback callback){
		
		for (String key: map.keySet()){
			double new_value = (double)callback.callback(map.get(key));
			map.put(key, new_value);
		}
		
		return this;
	}
	
	public VirtualDoubleMap absorb(HashMap<String,Double> absorbent){
		return absorb(absorbent, 1);
	}
	
	public VirtualDoubleMap absorb(HashMap<String,Double> absorbent, double absorbent_multiplier){
		if (absorbent == null){
			return this;
		}
		for(String key: absorbent.keySet()){
			this.get(key).add(absorbent.get(key)*absorbent_multiplier);
		}
		return this;
	}
	
	public class VirtualValue{
		
		public final String key;
		private final HashMap<String,Double> map;
		
		public VirtualValue(HashMap map, String key){
			this.key = key;
			this.map = map;
			if (!map.containsKey(key)){
				map.put(key, 0.0);
			}
		}
		
		public VirtualValue add(double value){
			map.put(key, get_value() + value);
			return this;
		}
		
		public Double get_value(){
			return map.get(key);
		}
		
		public VirtualValue set_value(double value){
			map.put(key, value);
			return this;
		}
		
		public String toString(){
			return get_value() + "";
		}
	}
	
	public double sum(){
		double sum = 0d;
		for(double d: map.values()){
			sum += d;
		}
		return sum;
	}
	
	public double average(boolean non_zero_only){
		Average avg = new Average();
		for(double d: map.values()){
			if(non_zero_only && d > 0){
				avg.add_number(d);
			}
		}
		
		return avg.get_average();
	}
}
