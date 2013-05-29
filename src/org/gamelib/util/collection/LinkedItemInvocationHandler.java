/**
 * 
 */
package org.gamelib.util.collection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.gamelib.util.collection.LinkedRow.LinkedItem;

/**
 * @author Axel
 */
public class LinkedItemInvocationHandler implements InvocationHandler {

	public LinkedItem object;

	/**
	 * 
	 */
	public LinkedItemInvocationHandler(LinkedItem object) {
		this.object = object;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
