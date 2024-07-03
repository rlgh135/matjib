package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.UserController;
import model.Session;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class MyRestView {
	public MyRestView() {
		UserDTO udto = (UserDTO)Session.getData("loginUser");
		UserController controller = new UserController();
		Scanner sc = new Scanner(System.in);
		
		ArrayList<RestaurantDTO> list = controller.getListByUDTO(udto);

		System.out.println("=======" + udto.getUser_id() + "님의 맛집 목록!=======");
		int k = 1;
		for (RestaurantDTO restaurant : list) {
			System.out.printf("%d %s - %s\n 주소: %s\n 맛집 전화번호 : %s", k++, restaurant.getName(),
					restaurant.getCategory(), restaurant.getAddr(),
					restaurant.getPhone());
			System.out.println("===========================");
		}
		//추가분
		System.out.println("1. 맛집 추가하기\t2. 맛집 삭제하기\t3. 나가기");
		
		while(true) {
			int choice = sc.nextInt();
			if(choice == 3)
				break;
			
			System.out.println("===========================");
			if(choice == 1) {
				new AddRestView();
			}
			else if(choice == 2) {
				new RemoveRestView();
			}
		}
	}
}