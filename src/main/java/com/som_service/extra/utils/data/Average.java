/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

/**
 *
 * @author Asuka
 */
public class Average {
	private double count = 0.0, average = 0.0;
	
	public double add_number(double number){
		
		average = (count * average + number)/(++count);
		
		return average;
	}
	
	public int get_count(){
		return (int)count;
	}
	
	public double get_average(){
		return average;
	}
	
	public String toString(){
		return average+"";
	}
}
