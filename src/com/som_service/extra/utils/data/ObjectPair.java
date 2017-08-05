/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

/**
 *
 * @author Eugene
 */
public class ObjectPair<K, V> {

	public final K key;
	public final V value;

	public String separator = "=";

	public ObjectPair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public String toString() {
		return key + separator + value;
	}
}
