package net.ayld.core.service.impl.factory;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.common.collect.Lists;

public class ArrayListFactory<CC extends Serializable> implements ListFactory<ArrayList<CC>, CC> {

	
	@Override
	public ArrayList<CC> newInstance() {
		return Lists.<CC>newArrayList();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Class getInstanceType() {
		return ArrayList.class;
	}
}
