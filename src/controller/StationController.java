package controller;

import java.util.ArrayList;

import model.dao.RestaurantDAO;
import model.dao.StationDAO;
import model.dto.RestaurantDTO;
import crawling.KakaoMap;

public class StationController {
	public StationController() {
	}

	public ArrayList<RestaurantDTO> getListByStation(String station_name) {
		// RestaurantStationDAO rsdao = new RestaurantStationDAO();
		StationDAO sdao = new StationDAO();
		ArrayList<RestaurantDTO> rests = sdao.getListByStationName(station_name);
		if (rests.size() == 0) {
			try {
				ArrayList<String[]> list = new KakaoMap(station_name).getList();
				RestaurantDTO rdto;
				RestaurantDAO rdao = new RestaurantDAO();
//				RestaurantStationDTO rsdto;
				for (String[] infos : list) {
					rdto = new RestaurantDTO(infos, 0);
					rdao.insertRestaurant(rdto);
					int rest_id = rdao.getIdByRDTO(rdto);
					sdao.insertByRSDTO(station_name, rest_id);
					rests.add(rdto);
				}
			} catch (Exception e) {
				System.out.println("StationController - getListByStation 에러");
			}
		}
		return rests;
	}
}