package net.ayld.core.service.impl.factory;

import java.io.Serializable;
import java.util.LinkedList;

import com.google.common.collect.Lists;

public class LinkedListFactory<CC extends Serializable> implements ListFactory<LinkedList<CC>, CC> {

	@Override
	public LinkedList<CC> newInstance() {
		return Lists.<CC>newLinkedList();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Class getInstanceType() {
		return LinkedList.class;
	}
}
