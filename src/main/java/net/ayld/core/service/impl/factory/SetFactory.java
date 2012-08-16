package net.ayld.core.service.impl.factory;

import java.io.Serializable;
import java.util.Set;

public interface SetFactory<CT extends Set<CC>, CC extends Serializable> {

	public CT newInstance();
	
	@SuppressWarnings("rawtypes")
	public Class getInstanceType();
}
