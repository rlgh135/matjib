package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class UserDAO {
	PreparedStatement ps;
	Connection conn;
	ResultSet rs;

	public UserDAO() {
		conn = DBConnection.getConnection();
	}

	public boolean insertUser(UserDTO user) {
		String sql = "insert into user (user_id, user_pw, user_name, user_phone, user_addr)" + "values(?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_id());
			ps.setString(2, user.getUser_pw());
			ps.setString(3, user.getUser_name());
			ps.setString(4, user.getUser_phone());
			ps.setString(5, user.getUser_addr());

			int result = ps.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
		}
		return false;
	}

	public UserDTO findUserById(String user_id) {
		// 데이터 로직은 순수하게 데이터에 관련된 로직만 구성되어 있다.
		// 뭔진 몰라도 넘겨진 userid로 User객체 찾아서 돌려주기만 하면 끝
		// API 이용해서 데이터베이스에 접근 후 객체로 리

		// 검색
		String sql = "select * from user where user_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);

			rs = ps.executeQuery();

			if (rs.next()) {
				UserDTO user = new UserDTO();
				user.setUser_id(rs.getString("user_id"));
				user.setUser_pw(rs.getString("user_pw"));
				user.setUser_name(rs.getString("user_name"));
				user.setUser_phone(rs.getString("user_phone"));
				user.setUser_addr(rs.getString("user_addr"));

				return user;
			}

		} catch (SQLException e) {
		}
		return null;
		// 결과가 있다면
	}

	public boolean updateUser(String user_id, int choice, String newData) {
//		//choice : 1(비밀번호 수정 - 1열), 2(핸드폰번호 수정 - 4열), 3(주소 수정 - 5열)
		String[] cols = { "", "user_pw", "user_phone", "user_addr" };
		String sql = "update user set " + cols[choice] + "= ? where user_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, newData);
			ps.setString(2, user_id);

			int result = ps.executeUpdate();

			return result == 1;
		} catch (SQLException e) {

		}
		return false;
	}

	public void deleteUser(String user_id) {
		String sql = "delete from user where user_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);

			ps.executeUpdate();
		} catch (SQLException e) {

		}
	}

	public ArrayList<RestaurantDTO> getListByUserId(String user_id) {
		String sql = "select * from user_restaurant where user_id = ? ";
		RestaurantDAO rdao = new RestaurantDAO();
		RestaurantDTO rdto;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);

			rs = ps.executeQuery();

			ArrayList<RestaurantDTO> list = new ArrayList<RestaurantDTO>();
			while (rs.next()) {
				rdto = rdao.getDTOById(rs.getInt("restaurant_id"));
				list.add(rdto);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("RestaurantDAO - getListByUserid 에러");
		}
		return null;
	}

	public void deleteUListByRDTO(String user_id, RestaurantDTO rdto) {
		// TODO Auto-generated method stub
		String sql = "delete from user_restaurant where user_id = ? and restaurant_id = " + rdto.getRestaurant_id();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);

			ps.executeUpdate();
		} catch (SQLException e) {

		}
	}

	// 유저_레스토랑 테이블에 맛집을 등록한다.
	public boolean addRestaurantToMyList(UserDTO user, RestaurantDTO rdto) {

		// DB에 내가 등록하려는 맛집이 없을때
		if (!isRestaurantInDB(rdto)) {
			RestaurantDAO rdao = new RestaurantDAO();
			rdao.insertRestaurant(rdto);
			rdao.setFirstIdByUDTO(user, rdto);
			rdto = rdao.getDTOByRDTO(rdto);
		}
		else {
			RestaurantDAO rdao = new RestaurantDAO();
			rdto = rdao.getDTOByRDTO(rdto);
			upUserPoint(rdto.getFirst_id());
		}

		String sql = "insert into user_restaurant (user_id, restaurant_id) values (?, " + rdto.getRestaurant_id()
				+ " )";
		try {

			// 내 맛집 리스트에 내가 등록하려는 맛집이 있는지 없는지 확인 (내 맛집 리스트에 같은 식당이 들어가지 않게 - 중복방지)
			// 없으면 등록할수있게
			if (!isRestaurantInMyList(user.getUser_id(), rdto.getRestaurant_id())) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, user.getUser_id());
				ps.executeUpdate();
				
				return true;

			} else {
				System.out.println("이미 내 맛집 리스트에 있는 맛집입니다.");
			}

		} catch (SQLException e) {
			System.out.println("UserDAO - addRestaurantToMyList 에러");
		}

		return false;
	}

	// DB에 내가 등록하려는 맛집이 있으면 true , 없으면 false
	private boolean isRestaurantInDB(RestaurantDTO rdto) {
		String sql = "select * from restaurant where  name = ? and addr = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rdto.getName());
			ps.setString(2, rdto.getAddr());

			rs = ps.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			System.out.println("UserDAO - isRestaurantInDB 에러");

		}
		return false;
	}

	// 내 맛집리스트에 내가 입력한 맛집이 있는지 없는지 조회
	// 내 맛집 리스트에 있으면 true, 없으면 false
	public boolean isRestaurantInMyList(String user_id, int restaurant_id) {

		String sql = "select * from user_restaurant where user_id = ? and restaurant_id = " + restaurant_id;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);

			rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			System.out.println("UserDAO - isRestaurantInMyList 에러");

		}

		return false;
	}

	public boolean removeRestaurantInMyList(UserDTO user, RestaurantDTO rdto) {
		if (rdto.getFirst_id().equals(user.getUser_id())) {
			RestaurantDAO rdao = new RestaurantDAO();
			rdao.resetFirstId(user.getUser_id(), rdto.getRestaurant_id());
		}

		String sql = "delete from user_restaurant where user_id = ? and restaurant_id = " + rdto.getRestaurant_id();
		try {
			if (isRestaurantInMyList(user.getUser_id(), rdto.getRestaurant_id())) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, user.getUser_id());
				ps.executeUpdate();
				return true;
			} else {
				System.out.println("내 맛집 리스트에 없는 맛집입니다.");
			}

		} catch (SQLException e) {
			System.out.println("UserDAO - removeRestaurantInMyList 에러");
		}

		return false;
	}

	// 세션과 다른 유저가 올린 리스트만 받아오는 메소드
	public ArrayList<UserDTO> getOtherList(UserDTO udto) {
		String sql = "select * from user where user_id not in('" + udto.getUser_id() + "')";
		ArrayList<UserDTO> otherlist = new ArrayList<UserDTO>();

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserDTO newudto = new UserDTO();
				newudto.setUser_id(rs.getString("user_id"));
				newudto.setUser_pw(rs.getString("user_pw"));
				newudto.setUser_name(rs.getString("user_name"));
				newudto.setUser_phone(rs.getString("user_phone"));
				newudto.setUser_addr(rs.getString("user_addr"));
				newudto.setUser_point(rs.getInt("user_point"));
				
				otherlist.add(newudto);
			}

			return otherlist;
		} catch (SQLException e) {
			System.out.println("UserDAO - getOtherList 에러");
			e.printStackTrace();
		}
		return null;
	}

	// user_id가 올린 식당목록을 loginUser 제외하고 가져오기
	public ArrayList<RestaurantDTO> getOtherRestaurantList(String user_id, String loginUser_id) {
		String select = "select * from user_restaurant where user_id = ? and user_id not in(?)";
		ArrayList<Integer> restaurantid = new ArrayList<Integer>();
		ArrayList<RestaurantDTO> otherlist = new ArrayList<RestaurantDTO>();

		try {
			ps = conn.prepareStatement(select);
			ps.setString(1, user_id);
			ps.setString(2, loginUser_id);

			rs = ps.executeQuery();
			while (rs.next()) {
				restaurantid.add(rs.getInt("restaurant_id"));
			}
			
			int i = 0;
			while (rs.next()) {
				String sql = "select * from restaurant where restaurant_id = "+restaurantid.get(i++);

				ps = conn.prepareStatement(sql);
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

					otherlist.add(restaurant);
				}
			}

			return otherlist;
		} catch (SQLException e) {
			System.out.println("UserDAO - getOtherRestaurantList 에러");
			e.printStackTrace();
		}

		return null;
	}
	public void upUserPoint(String userId) {
	    String sql = "update user set user_point = user_point + 1 where user_id = ?";
	    try {
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, userId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	    	System.out.println("UserDAO - upUserPoint 에러");
	    	
	    }
	}
	public void downUserPoint(String userId) {
		String sql = "update user set user_point = user_point - 1 where user_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("UserDAO - upUserPoint 에러");
			
		}
	}

}