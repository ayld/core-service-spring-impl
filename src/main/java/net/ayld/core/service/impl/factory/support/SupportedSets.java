package net.ayld.core.service.impl.factory.support;

import java.util.HashSet;

public enum SupportedSets {
	
	HASH(HashSet.class);
	
	private final Class<?> instanceClass;

	private SupportedSets(Class<?> instanceClass) {
		this.instanceClass = instanceClass;
	}
	
	public static boolean isSupported(Class<?> toCheck) {
		for (SupportedSets val : values()) {
			
			if (val.instanceClass.equals(toCheck)) {
				
				return true;
			}
		}
		return false;
	}
}
