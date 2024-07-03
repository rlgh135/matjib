package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import crawling.KakaoMap;
import model.DBConnection;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class RestaurantDAO {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public RestaurantDAO() {
		conn = DBConnection.getConnection();
	}

	public boolean insertRestaurant(RestaurantDTO rest) {
		if (checkRestaurant(rest))
			return false;
		String sql = "insert into restaurant (name, category, addr, phone) " + "values(?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rest.getName());
			ps.setString(2, rest.getCategory());
			ps.setString(3, rest.getAddr());
			ps.setString(4, rest.getPhone());

			// 데이터베이스에 삽입
			int result = ps.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - insertRestaurant 에러");
		}
		return false;
	}

	public RestaurantDTO findRestaurantById(int rest_id) {
		// 검색
		String sql = "select * from restaurant where restaurant_id = " + rest_id;
		try {
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			if (rs.next()) {
				RestaurantDTO res = new RestaurantDTO();
				res.setRestaurant_id(rs.getInt("restaurant_id"));
				res.setName(rs.getString("name"));
				res.setCategory(rs.getString("category"));
				res.setAddr(rs.getString("addr"));
				res.setPhone(rs.getString("phone"));
				return res;
			}
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - findRestaurantById 에러");
		}
		return null;
	}

	public boolean updateRestaurant(String rest_id, int choice, String newData) {
		// choice : 1(식당이름 수정), 2(카테고리 수정), 3(주소 수정), 4(전화번호 수정)
		String[] cols = { "", "name", "category", "addr", "phone" };
		String sql = "update restaurant set " + cols[choice] + " = ? where id = " + rest_id;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, newData);

			int result = ps.executeUpdate();

			return result == 1;
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - updataRestaurant 에러");
		}

		return false;
	}

	public void deleteRestaurant(int rest_id) {
		String sql = "delete from restaurant where id = " + rest_id;
		try {
			ps = conn.prepareStatement(sql);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - deleteRestaurant 에러");
		}
	}

	// 식당이 있으면 true 없으면 false 리턴
	public boolean checkRestaurant(RestaurantDTO rdto) {
		String sql = "select * from restaurant where name = ? and addr = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rdto.getName());
			ps.setString(2, rdto.getAddr());

			rs = ps.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			System.out.println("RestaurantDAO - checkRestaurant 에러");
		}
		return false;

	}

	public int getIdByRDTO(RestaurantDTO rdto) {
		// TODO Auto-generated method stub
		String sql = "select * from restaurant where name = ? and addr = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rdto.getName());
			ps.setString(2, rdto.getAddr());

			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt("restaurant_id");
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - getIdByRDTO 에러");
		}
		return -1;
	}

	// ============================================
	// 추가부분
	// 가게 이름으로 크롤링
	public ArrayList<RestaurantDTO> crawlingListByName(String rest_name) {
		ArrayList<RestaurantDTO> rests = new ArrayList<RestaurantDTO>();
		RestaurantDTO rdto;
		try {
			ArrayList<String[]> list = new KakaoMap(rest_name).getList();
			int k = 1;

			for (String[] info : list) {
				rdto = new RestaurantDTO(info, k++);
				rests.add(rdto);
			}

			return rests;
		} catch (Exception e) {
			System.out.println("RestaurantDAO - crawlingListByName 에러");
			e.printStackTrace();
		}
		return null;
	}

	// 이름으로 DB에서 찾기
	public boolean checkRestaurantByName(String rest_name) {
		String sql = "select * from restaurant where name = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rest_name);

			rs = ps.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			System.out.println("RestaurantDAO - checkRestaurant 에러");
		}
		return false;

	}

	// 이름으로 DB에서 불러오기
	public ArrayList<RestaurantDTO> getListByName(String rest_name) {
		String sql = "select * from restaurant where name = ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rest_name);

			rs = ps.executeQuery();
			ArrayList<RestaurantDTO> rests = new ArrayList<RestaurantDTO>();
			while (rs.next()) {
				RestaurantDTO restaurant = new RestaurantDTO();
				restaurant.setRestaurant_id(rs.getInt("restaurant_id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setCategory(rs.getString("category"));
				restaurant.setAddr(rs.getString("addr"));
				restaurant.setPhone(rs.getString("phone"));

				rests.add(restaurant);
			}
			return rests;
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - getListByName 에러");
		}
		return null;
	}

	public RestaurantDTO getDTOById(int restaurant_id) {

		String sql = "select * from restaurant where restaurant_id = ?";
		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, restaurant_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				RestaurantDTO restaurant = new RestaurantDTO();
				restaurant.setRestaurant_id(rs.getInt("restaurant_id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setCategory(rs.getString("category"));
				restaurant.setAddr(rs.getString("addr"));
				restaurant.setPhone(rs.getString("phone"));
				restaurant.setFirst_id(rs.getString("first_id"));
				restaurant.setLikecnt(rs.getInt("likecnt"));

				return restaurant;

			}

		} catch (SQLException e) {
			System.out.println("RestaurantDAO - getDTOById 에러");

		}

		return null;
	}

	public void deleteRestaurantByRestId(int rest_id) {
		String sql = "delete from restaurant where restaurant_id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, rest_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("RestaurantDAO - deleteRestaurant 에러");
		}

	}

	public void resetFirstId(String user_id, int rest_id) {
		String sql = "update restaurant set first_id = 'manager' WHERE first_id = ? and restaurant_id = " + rest_id;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("RestaurantDAO - updateFirst_idToManager 에러 ");
		}

	}
	
	
	
	public void setFirstIdByUDTO(UserDTO user, RestaurantDTO rdto) {
		String sql = "select * from restaurant where name = ? and addr = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rdto.getName());
			ps.setString(2, rdto.getAddr());

			rs = ps.executeQuery();
			
			int rest_id = 0;
			while(rs.next()) {
				rest_id = rs.getInt("restaurant_id");
			}
			
			if(rest_id!=0) {
				String update = "update restaurant set first_id = ? WHERE restaurant_id = " + rest_id;
				
				ps = conn.prepareStatement(update);
				ps.setString(1, user.getUser_id());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - setFirstIdByUDTO 에러");
		}
		
	}
	
	public RestaurantDTO getDTOByRDTO(RestaurantDTO rdto) {

		String sql = "select * from restaurant where name = ? and addr = ?";
		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, rdto.getName());
			ps.setString(2, rdto.getAddr());
			rs = ps.executeQuery();

			while (rs.next()) {
				RestaurantDTO restaurant = new RestaurantDTO();
				restaurant.setRestaurant_id(rs.getInt("restaurant_id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setCategory(rs.getString("category"));
				restaurant.setAddr(rs.getString("addr"));
				restaurant.setPhone(rs.getString("phone"));
				restaurant.setFirst_id(rs.getString("first_id"));
				restaurant.setLikecnt(rs.getInt("likecnt"));
				return restaurant;
			}

		} catch (SQLException e) {
			System.out.println("RestaurantDAO - getDTOById 에러");

		}

		return null;
	}
	
}