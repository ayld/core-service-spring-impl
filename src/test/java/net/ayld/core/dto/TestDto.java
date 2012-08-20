package net.ayld.core.dto;

public class TestDto extends BaseDto<Integer>{
	
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

	@Override
	public Integer getId() {
		return id;
	}

	private static final long serialVersionUID = 1L;
}
