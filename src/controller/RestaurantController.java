package controller;

import java.util.ArrayList;

import model.dao.RestaurantDAO;
import model.dto.RestaurantDTO;

public class RestaurantController {
	public ArrayList<RestaurantDTO> getListByRestaurant(String rest_name){
		RestaurantDAO rdao = new RestaurantDAO();
		ArrayList<RestaurantDTO> list = new ArrayList<RestaurantDTO>();
		
		if (rdao.checkRestaurantByName(rest_name)) {
			//DB에서 찾기
			list = rdao.getListByName(rest_name);
			System.out.println("====DB에서 "+rest_name+"을 찾았습니다!====");
		}
		else {
			//DB에 아무것도 없다면 크롤링
			list = rdao.crawlingListByName(rest_name);
			System.out.println("====DB에 저장된 맛집이 없어 새로 검색했습니다!====");
		}
		
		return list;
	}
}