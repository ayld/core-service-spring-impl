package net.ayld.core.service.impl.factory.support;

import java.util.ArrayList;
import java.util.LinkedList;

public enum SupportedLists {
	
	ARRAY(ArrayList.class),
	LINKED(LinkedList.class);
	
	private final Class<?> instanceClass;

	private SupportedLists(Class<?> instanceClass) {
		this.instanceClass = instanceClass;
	}
	
	public static boolean isSupported(Class<?> toCheck) {
		for (SupportedLists val : values()) {
			
			if (val.instanceClass.equals(toCheck)) {
				
				return true;
			}
		}
		return false;
	}
}
