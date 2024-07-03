package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.UserController;
import model.Session;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class RemoveRestView {
	public RemoveRestView() {
		UserDTO user = (UserDTO) Session.getData("loginUser");
		Scanner sc = new Scanner(System.in);

		UserController controller = new UserController();
		ArrayList<RestaurantDTO> mylist = controller.getListByUDTO(user);

		while (true) {
			System.out.println("=======" + user.getUser_id() + "님의 맛집 리스트=======");
			int k = 1;
			for (RestaurantDTO restaurant : mylist) {
				System.out.printf("%d %s - %s\n 주소: %s\n 맛집 전화번호 : %s", k++, restaurant.getName(),
						restaurant.getCategory(), restaurant.getAddr(),
						restaurant.getPhone());
				System.out.println("===========================");
			}
			System.out.print("삭제하실 식당 번호 입력해 주세요 : ");
			int search = sc.nextInt();
			if (search > mylist.size()) {
				System.out.println("입력하신 번호에 해당되는 식당이 없습니다.");
			} else {
				RestaurantDTO rdto = mylist.get(search - 1);
				if (controller.deleteRestInMylist(user, rdto)) {
					System.out.println("맛집 삭제 완료!");
				}
			}
			mylist = controller.getListByUDTO(user);
			if(mylist.size()==0) {
				System.out.println("더이상 삭제하실 맛집이 없습니다.");
				break;
			}else {
				System.out.println("맛집을 더 삭제하시겠습니까?");
				System.out.println("1.네\t2.아니요");
				search = sc.nextInt();
				if(search==2) break;
			}
		}
	}
}