package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;
import model.dto.RestaurantDTO;
import model.dto.StationDTO;

public class StationDAO {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public StationDAO() {
		conn = DBConnection.getConnection();
	}
	
	public boolean insertStation(StationDTO station) {
		String sql = "insert into station (station_name, station_line) " + "values(?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, station.getStationName());
			ps.setString(2, station.getStationLine());

			// 데이터베이스에 삽입
			int result = ps.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
		}
		return false;
	}

	public StationDTO findStationByName(String station_name) {
		// 데이터 로직은 순수하게 데이터에 관련된 로직만 구성되어 있다.
		// 뭔진 몰라도 넘겨진 userid로 User객체 찾아서 돌려주기만 하면 끝
		// API 이용해서 데이터베이스에 접근 후 객체로 리턴

		// 검색
		String sql = "select * from station where station_name = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, station_name);
			rs = ps.executeQuery();

			if (rs.next()) {
				StationDTO station = new StationDTO();
				station.setStationName(rs.getString("station_name"));
				station.setStationLine(rs.getString("station_line"));
				return station;
			}
		} catch (SQLException e) {
		}
		return null;
	}

	public boolean updateStation(String station_name, int choice, String newData) {
		String select = "select * frome sation where station_name = ?";
		String sql = "update station set station_line = ? where station_name = ?";

		try {
			ps = conn.prepareStatement(select);
			rs = ps.executeQuery();
			String line = rs.getString("station_line");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, line+","+newData);
			ps.setString(2, station_name);

			int result = ps.executeUpdate();

			return result == 1;
		} catch (SQLException e) {
		}

		return false;
	}

	public void deleteStation(String station_name) {
		String sql = "delete from station where station_name = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, station_name);

			ps.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public boolean insertRestaurantStation(RestaurantDTO rest, StationDTO station) {
		String sql = "insert into restaurant_station values(" + rest.getRestaurant_id() 
		+ ",?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, station.getStationName());

			return 1 == ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("StationDAO - insertRestaurantStatio : 에러");
		}

		return false;
	}
	
	public ArrayList<RestaurantDTO> getListByStationName(String station_name) {
		String sql = "select * from restaurant_station where station_name = ?";
		ArrayList<RestaurantDTO> list = new ArrayList<RestaurantDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, station_name);
			
			rs = ps.executeQuery();
			
			ArrayList<Integer> restIds = new ArrayList<Integer>();
			while(rs.next()) {
				restIds.add(rs.getInt("restaurant_id"));
			}
			RestaurantDAO rdao = new RestaurantDAO(); 
			RestaurantDTO rdto;
			
			for(Integer id : restIds) {
				rdto = rdao.findRestaurantById(id);
				list.add(rdto);
			}
			return list;
			
		} catch (SQLException e) {
			System.out.println("RestaurantStationDAO - getListByStationName 에러");
		}
		return null;
	}
	
	public boolean insertByRSDTO(String station_name, int restaurant_id) {
		String sql = "insert into restaurant_station values(" + restaurant_id+", ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, station_name);

			return 1 == ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("RestaurantStationDAO - insertByRSDTO 에러");
		}
		return false;
	}
}