package controller;

import java.util.ArrayList;

import model.Session;
import model.dao.RestaurantDAO;
import model.dao.UserDAO;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class UserController {
	public boolean checkId(String user_id) {
		// 어떤 기능 개발 코드는 여러 데이터로직(데이터를 관리하는 로직)과 비즈니스로직(실제 알고리즘)들이 합쳐진 형태
		// 데이터 접근용 객체를 이용해서 userid 값을 넘겨주며 User객체 찾기(데이터로직)
		// 찾은 객체가 있는지 없는지를 비교(비즈니스 로직)한 후 결과를 리턴
		UserDAO udao = new UserDAO();
		// 데이터 로직을 활용해서 비즈니스 로직을 구현한 상태
		// checkId는 검색된 결과가 없어야 참이므로 null일때 true를 리턴
		return udao.findUserById(user_id) == null;
	}

	public boolean join(UserDTO user) {
		// 전달받은 dto객체에 담긴 정보들을 데이터베이스에 삽입
		UserDAO udao = new UserDAO();
		// 데이터 로직을 이용해서 데이터베이스 삽입
		return udao.insertUser(user);
	}

	public boolean login(String user_id, String user_pw) {
		// 넘겨받은 아이디로 유저를 찾아보고, 찾은 유저의 비밀번호가 넘겨받은 비밀번호와 같다면 로그인 성공
		UserDAO udao = new UserDAO();
		// 넘겨받은 아이디로 유저찾기(데이터 로직) - 재사용
		UserDTO user = udao.findUserById(user_id);
		// 유저가 찾아졌다면
		if (user != null) {
			// 찾은 유저의 비밀번호도 비교(비즈니스 로직)
			if (user.getUser_pw().equals(user_pw)) {
				// 로그인 성공했으면 세션에 세팅
				Session.setData("loginUser", user);
				return true;
			}
		}
		return false;
	}

	public boolean modifyUser(String user_id, int choice, String newData) {
		UserDAO udao = new UserDAO();
		boolean result = udao.updateUser(user_id, choice, newData);
		// 수정이 성공한 경우
		if (result) {
			// 수정이 된 곳은 DB이다. 자바 시스템 내부의 세션객체는 여전히 수정되기 전 정보를 가지고 있다.
			// 다시 바뀐 정보로 DB에서 검색을 하고, 그 정보 기반으로 새로운 객체를 만들어서 세션정보를 바꿔준다.
			UserDTO user = udao.findUserById(user_id);
			Session.setData("loginUser", user);
			return true;
		}
		return false;
	}

	public boolean userWithDraw(String user_id) {
		UserDAO udao = new UserDAO();

		// 해당 유저가 등록한 맛집 리스트를 가져옴
		ArrayList<RestaurantDTO> list = udao.getListByUserId(user_id);

		RestaurantDAO rdao = new RestaurantDAO();
		for (RestaurantDTO rdto : list) {
			if (user_id.equals(rdto.getFirst_id())) {
				rdao.resetFirstId(user_id, rdto.getRestaurant_id());
			}
			udao.deleteUListByRDTO(user_id, rdto);
		}

		// 회원 정보에서 이 사람의 정보를 삭제
		udao.deleteUser(user_id);

		// 회원정보가 삭제되었기 때문에 세션 정보도 더 이상 유지해서는 안된다.
		Session.setData("loginUser", null);
		return true;
	}

	public boolean addMyListByRDTO(UserDTO user, RestaurantDTO rdto) {
		UserDAO udao = new UserDAO();
		return udao.addRestaurantToMyList(user, rdto);
	}

	// 다른 사람이 올린 레스토랑 가져오기
	public ArrayList<RestaurantDTO> getOtherRestaurantList(String user_id, String loginUser_id) {
		UserDAO udao = new UserDAO();
		return udao.getOtherRestaurantList(user_id, loginUser_id);
	}

	// 다른 사람의 아이디 가져오기
	public ArrayList<UserDTO> getOtherList(UserDTO udto) {
		UserDAO udao = new UserDAO();
		return udao.getOtherList(udto);
	}

	public ArrayList<RestaurantDTO> getListByUDTO(UserDTO user) {
		UserDAO udao = new UserDAO();
		return udao.getListByUserId(user.getUser_id());
	}

	public boolean deleteRestInMylist(UserDTO user, RestaurantDTO rdto) {
		UserDAO udao = new UserDAO();
		return udao.removeRestaurantInMyList(user, rdto);
	}
}