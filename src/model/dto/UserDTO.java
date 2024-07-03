package model.dto;

public class UserDTO {
	private String user_id;
	private String user_pw;
	private String user_name;
	private String user_phone;
	private String user_addr;
	private int user_point;

	public UserDTO() {
	}
	
	public UserDTO(String user_id, String user_pw, String user_name, String user_phone, String user_addr) {
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.user_phone = user_phone;
		this.user_addr = user_addr;
		this.user_point = 0;
	}

	public UserDTO(String[] datas) {
		this.user_id = datas[0];
		this.user_pw = datas[1];
		this.user_name = datas[2];
		this.user_phone = datas[3];
		this.user_addr = datas[4];
		this.user_point = 0;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_addr() {
		return user_addr;
	}

	public void setUser_addr(String user_addr) {
		this.user_addr = user_addr;
	}

	public int getUser_point() {
		return user_point;
	}

	public void setUser_point(int user_point) {
		this.user_point = user_point;
	}
}