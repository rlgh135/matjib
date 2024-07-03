package model.dto;

public class RestaurantDTO {

	private int restaurant_id;
	private String name;
	private String category;
	private String addr;
	private String phone;
	private String first_id = "manager";
	private int likecnt;

	public RestaurantDTO() {
	}

	public RestaurantDTO(String name, String category, String addr, String phone, String first_id, int likecnt) {
		this.name = name;
		this.category = category;
		this.addr = addr;
		this.phone = phone;
		this.first_id = first_id;
		this.likecnt = 0;
	}

	public RestaurantDTO(int restaurant_id, String name, String category, String addr, String phone) {

		this.restaurant_id = restaurant_id;
		this.name = name;
		this.category = category;
		this.addr = addr;
		this.phone = phone;
		this.likecnt = 0;
	}

	public RestaurantDTO(String[] datas) {
		this.restaurant_id = Integer.parseInt(datas[0]);
		this.name = datas[1];
		this.category = datas[2];
		this.addr = datas[3];
		this.phone = datas[4];
		this.likecnt = 0;
	}

	public RestaurantDTO(String[] datas, int x) {
		this.name = datas[0];
		this.category = datas[1];
		this.addr = datas[2];
		this.phone = datas[3];
		this.likecnt = 0;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirst_id() {
		return first_id;
	}

	public void setFirst_id(String first_id) {
		this.first_id = first_id;
	}

	public int getLikecnt() {
		return likecnt;
	}

	public void setLikecnt(int likecnt) {
		this.likecnt = likecnt;
	}
}