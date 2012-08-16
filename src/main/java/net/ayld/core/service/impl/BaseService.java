package net.ayld.core.service.impl;

import java.io.Serializable;

import net.ayld.core.dto.BaseDto;
import net.ayld.core.service.CrudService;

public abstract class BaseService<D extends BaseDto<I>, I extends Serializable> implements CrudService<D, I>, Serializable{

	@Override
	public D read(I id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implmented yet.");
	}

	@Override
	public D find(I id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implmented yet.");
	}

	@Override
	public D update(D dto) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implmented yet.");
	}

	@Override
	public D create(D dto) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implmented yet.");
	}

	@Override
	public D delete(I id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implmented yet.");
	}
	
	private static final long serialVersionUID = 1L;
}
