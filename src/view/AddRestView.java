package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.RestaurantController;
import controller.UserController;
import model.Session;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class AddRestView {
	public AddRestView() {
		UserDTO user = (UserDTO) Session.getData("loginUser");
		Scanner sc = new Scanner(System.in);
		UserController usercontroller = new UserController();
		RestaurantController rescontroller = new RestaurantController();
		boolean flag = false;

		System.out.println("========맛집 추가==========");
		System.out.println("추가하실 맛집 이름을 알려주세요: ");
		String restaurant_name = sc.nextLine();

		ArrayList<RestaurantDTO> list = rescontroller.getListByRestaurant(restaurant_name);

		while (true) {
			if (list == null) {
				System.out.println("=============================");
				System.out.println("검색 결과가 없습니다.");
			} else {
				System.out.println("=======" + restaurant_name + "맛집 입니다.=======");
				int k = 1;
				for (RestaurantDTO restaurant : list) {
					System.out.printf("%d. %s - %s\n 주소: %s\n 맛집 전화번호 : %s\n", k++, restaurant.getName(),
							restaurant.getCategory(), restaurant.getAddr(),
							restaurant.getPhone());
					System.out.println("=============================");

				}
			}
			// 내 가게 저장 등 확장
			System.out.println("=============================");
			System.out.println("찾으시는 식당이 없으신가요?");
			System.out.println("1. 예\t2. 아니오");
			int search = sc.nextInt();

			if (search == 1) {
				sc.nextLine();
				System.out.println("=============================");
				System.out.println("검색어를 더 자세히 입력해주세요 : ");
				restaurant_name = sc.nextLine();
				list = rescontroller.getListByRestaurant(restaurant_name);
			} else if (search == 2) {
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println("=============================");
			System.out.println("맛집을 내 맛집리스트에 저장하시겠습니까?");
			System.out.println("1.예\t2. 아니오");
			int choice = sc.nextInt();

			if (1 == choice) {
				System.out.println("=============================");
				System.out.println("추가할 맛집의 번호를 입력해 주세요: ");
				int select = sc.nextInt();
				if (usercontroller.addMyListByRDTO(user, list.get(select - 1)))
					System.out.println(user.getUser_id() + "님 맛집 리스트에 " + list.get(select - 1).getName()
							+ " 맛집 추가가 완료되었습니다!");
			}
		}
	}
}