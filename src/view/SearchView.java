package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.RestaurantController;
import controller.StationController;
import controller.UserController;
import model.Session;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class SearchView {
	public SearchView() {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. 역별 랭킹 보기\n2. 맛집 검색 ");
		int choice = sc.nextInt();

		sc.nextLine();

		if (choice == 1) {
			StationController controller = new StationController();
			System.out.print("어떤 역을 검색하시겠습니까? :");
			String station_name = sc.nextLine();
			
			

			// 역 컨트롤러 객체 생성

			// 역 컨트롤러로 가서 내가 입력한 역이름으로 찾아서 list에 넣기
			ArrayList<RestaurantDTO> list = controller.getListByStation(station_name);

			System.out.println("=======" + station_name + "맛집 입니다.=======");
			int k = 1;
			for (RestaurantDTO restaurant : list) {
				System.out.printf("%d %s - %s\n 주소: %s\n 맛집 전화번호 : %s", k++, restaurant.getName(),
						restaurant.getCategory(), restaurant.getAddr(),
						restaurant.getPhone());
				System.out.println("===========================");
			}
		}
		else if (choice == 2) {
			RestaurantController controller = new RestaurantController();
			System.out.println("맛집 이름을 입력해주세요 : ");
			String rest_name = sc.nextLine();

			ArrayList<RestaurantDTO> list = controller.getListByRestaurant(rest_name);

			while (true) {
				if (list == null) {
					System.out.println("검색 결과가 없습니다.");
				} else {
					System.out.println("=======" + rest_name + "맛집 입니다.=======");
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
				System.out.println("1. 다시 검색하기\t2. 나가기");
				int search = sc.nextInt();
				if (search == 1) {
					sc.nextLine();
					System.out.println("맛집 이름을 입력해주세요 : ");
					rest_name = sc.nextLine();
					list = controller.getListByRestaurant(rest_name);
				} else if (search == 2)
					break;
			}
		}
	}

	public SearchView(String user_id) {
		UserDTO loginUser = (UserDTO)Session.getData("loginUser");
		Scanner sc = new Scanner(System.in);

		System.out.println("1. 역별 랭킹 보기\n2. 맛집 검색 ");
		int choice = sc.nextInt();

		sc.nextLine();

		if (choice == 1) {
			StationController controller = new StationController();
			System.out.print("어떤 역을 검색하시겠습니까? :");
			String station_name = sc.nextLine();
			
			ArrayList<RestaurantDTO> list = controller.getListByStation(station_name);

			System.out.println("=======" + station_name + "맛집 입니다.=======");
			int k = 1;
			for (RestaurantDTO restaurant : list) {
				System.out.printf("%d %s - %s\n 주소: %s\n 맛집 전화번호 : %s", k++, restaurant.getName(),
						restaurant.getCategory(), restaurant.getAddr(),
						restaurant.getPhone());
				System.out.println("===========================");
			}
			while(true) {
				System.out.println("찾으시는 식당이 있으신가요?");
				System.out.println("1. 내 맛집에 저장하기\t2. 식당명으로 검색하기\t3. 나가기");
				int search = sc.nextInt();
				if (search == 1) {
					sc.nextLine();
					System.out.println("저장하고자 하는 맛집 번호 입력해주세요 : ");
					int listnum = sc.nextInt();
					if(listnum>list.size()) {
						System.out.println("입력하신 번호에 해당되는 식당이 없습니다.");
						continue;
					}
					UserController usercontroller = new UserController();
					if(usercontroller.addMyListByRDTO(loginUser,list.get(listnum-1))) {
						System.out.println("맛집 추가 완료!");
					}
				} else if (search == 2) {
					//반복문 멈추고 밑에 맛집으로 검색으로 넘어가기
					choice = 2;
					break;
				}else if(search == 3)
					break;
			}
		}
		else if (choice == 2) {
			RestaurantController controller = new RestaurantController();
			System.out.println("맛집 이름을 입력해주세요 : ");
			String rest_name = sc.nextLine();

			ArrayList<RestaurantDTO> list = controller.getListByRestaurant(rest_name);

			while (true) {
				if (list == null) {
					System.out.println("검색 결과가 없습니다.");
				} else {
					System.out.println("=======" + rest_name + "맛집 입니다.=======");
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
				System.out.println("1. 내 맛집에 저장하기\t2. 다시 검색하기\t3. 나가기");
				int search = sc.nextInt();
				if(search == 1) {
					sc.nextLine();
					System.out.println("저장하고자 하는 맛집 번호 입력해주세요 : ");
					int listnum = sc.nextInt();
					if(listnum>list.size()) {
						System.out.println("입력하신 번호에 해당되는 식당이 없습니다.");
						search = 2;
					}
					else {
						UserController usercontroller = new UserController();
						if(usercontroller.addMyListByRDTO(loginUser,list.get(listnum-1))) {
							System.out.println("맛집 추가 완료!");
						}
					}
				}
				else if (search == 2) {
					sc.nextLine();
					System.out.println("맛집 이름을 입력해주세요 : ");
					rest_name = sc.nextLine();
					list = controller.getListByRestaurant(rest_name);
				} else if (search == 3)
					break;
			}
		}
	}
}