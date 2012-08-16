package net.ayld.core.service.impl.factory.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.ayld.core.domain.BaseEntity;
import net.ayld.core.dto.BaseDto;
import net.ayld.core.service.impl.factory.ArrayListFactory;
import net.ayld.core.service.impl.factory.HashSetFactory;
import net.ayld.core.service.impl.factory.LinkedListFactory;
import net.ayld.core.service.impl.factory.ListFactory;
import net.ayld.core.service.impl.factory.SetFactory;
import net.ayld.core.service.impl.factory.support.SupportedLists;
import net.ayld.core.service.impl.factory.support.SupportedSets;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings("rawtypes")
public class CollectionFactoryManager<D extends BaseDto<I>, E extends BaseEntity<I>, I extends Serializable> {

	private final ImmutableMap<Class, ListFactory<? extends List<D>, D>> dtoListFactories;

	private final ImmutableMap<Class, ListFactory<? extends List<E>, E>> entityListFactories;
	
	private final ImmutableMap<Class, SetFactory<? extends Set<D>, D>> dtoSetFactories;
	
	private final ImmutableMap<Class, SetFactory<? extends Set<E>, E>> entitySetFactories;

	public CollectionFactoryManager() {

		// list factories
		final ListFactory<ArrayList<D>, D> dtoArrayListFactory = new ArrayListFactory<D>();
		final ListFactory<LinkedList<D>, D> dtoLinkedListFactory = new LinkedListFactory<D>();
		
		dtoListFactories = new ImmutableMap.Builder<Class, ListFactory<? extends List<D>, D>>()
				.put(dtoArrayListFactory.getInstanceType(), dtoArrayListFactory)
				.put(dtoLinkedListFactory.getInstanceType(), dtoLinkedListFactory)
				.build();

		final ListFactory<ArrayList<E>, E> entityArrayListFactory = new ArrayListFactory<E>();
		final ListFactory<LinkedList<E>, E> entityLinkedListFactory = new LinkedListFactory<E>();

		entityListFactories = new ImmutableMap.Builder<Class, ListFactory<? extends List<E>, E>>()
				.put(entityArrayListFactory.getInstanceType(), entityArrayListFactory)
				.put(entityLinkedListFactory.getInstanceType(), entityLinkedListFactory)
				.build();
		
		// set factories
		final SetFactory<HashSet<D>, D> dtoHashSetFactory = new HashSetFactory<D>();
		
		dtoSetFactories = new ImmutableMap.Builder<Class, SetFactory<? extends Set<D>, D>>()
				.put(dtoHashSetFactory.getInstanceType(), dtoHashSetFactory)
				.build();
		
		final SetFactory<HashSet<E>, E> entityHashSetFactory = new HashSetFactory<E>();
		
		entitySetFactories = new ImmutableMap.Builder<Class, SetFactory<? extends Set<E>, E>>()
				.put(entityHashSetFactory.getInstanceType(), entityHashSetFactory)
				.build();
		
		
	}

	public List<D> newDtoList(Class c) {
		
		if (!SupportedLists.isSupported(c)) {
			
			throw new IllegalArgumentException("Lists of type: " + c + " are currently not supported.");
		}
		return dtoListFactories.get(c).newInstance();
	}
	
	public List<E> newEntityList(Class c) {
		
		if (!SupportedLists.isSupported(c)) {
			
			throw new IllegalArgumentException("Lists of type: " + c + " are currently not supported.");
		}
		return entityListFactories.get(c).newInstance();
	}
	
	public Set<D> newDtoSet(Class c) {
		
		if (!SupportedSets.isSupported(c)) {
			
			throw new IllegalArgumentException("Sets of type: " + c + " are currently not supported.");
		}
		return dtoSetFactories.get(c).newInstance();
	}
	
	public Set<E> newEntitySet(Class c) {
		
		if (!SupportedSets.isSupported(c)) {
			
			throw new IllegalArgumentException("Sets of type: " + c + " are currently not supported.");
		}
		return entitySetFactories.get(c).newInstance();
	}
}
