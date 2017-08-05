/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.callback;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import com.som_service.extra.utils.data.ObjectPair;

/**
 *
 * @author Asuka
 */
public class CallbackFactory {
	public static final Callback ARRAYLIST_OBJECTPAIR_TAB_NEWLINE = new Callback() {

			@Override
			public Object callback(Object args) {
				ArrayList<ObjectPair> ops = (ArrayList<ObjectPair>)args;
				
				String string = "";
				
				for(ObjectPair op: ops){
					op.separator = "\t";
				}
				
				return StringUtils.join(ops,"\n");
			}
		};
}
