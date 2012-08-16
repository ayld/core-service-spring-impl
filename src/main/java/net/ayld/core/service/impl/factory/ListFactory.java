package net.ayld.core.service.impl.factory;

import java.io.Serializable;
import java.util.List;

public interface ListFactory<CT extends List<CC>, CC extends Serializable> {

	public CT newInstance();
	
	@SuppressWarnings("rawtypes")
	public Class getInstanceType();
}
