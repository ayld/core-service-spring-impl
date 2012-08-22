package net.ayld.core.domain;

public class TestEntity extends BaseEntity<Integer>{

	private String name;
	private Integer id;
	
	
	public void setName(String name) {
		this.name = name;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	
	public Integer getId() {
		return id;
	}


	private static final long serialVersionUID = 1L;
}
