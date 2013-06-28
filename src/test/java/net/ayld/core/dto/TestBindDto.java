package net.ayld.core.dto;

import net.ayld.core.annotation.BindField;

public class TestBindDto extends BaseDto<Integer>{

	@BindField(name = "name")
	private String username;
	
	@BindField(name = "id")
	private Integer num;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	private static final long serialVersionUID = 1954852092422781684L;
}
