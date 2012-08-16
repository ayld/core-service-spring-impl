package net.ayld.core.service.impl.factory;

import java.io.Serializable;
import java.util.HashSet;

import com.google.common.collect.Sets;

public class HashSetFactory<CC extends Serializable> implements SetFactory<HashSet<CC>, CC>{

	@Override
	public HashSet<CC> newInstance() {
		return Sets.<CC>newHashSet();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Class getInstanceType() {
		return HashSet.class;
	}

}
