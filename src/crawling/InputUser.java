package crawling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import model.dao.RestaurantDAO;
import model.dao.UserDAO;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class InputUser {
	public static void main(String[] args) throws Exception {
		ArrayList<String[]> list = new ArrayList<String[]>();
		BufferedReader br = null;
		String txtname = "박기호.txt";
		try {
			FileReader fr = new FileReader(txtname);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("파일 읽기 문제 발생");
		}

		if (br != null) {
			while (true) {
				String str = br.readLine();
				if (str == null) {
					break;
				}
				String[] info = str.split("\t");
				list.add(info);
			}
			
			UserDAO udao = new UserDAO();
			UserDTO udto = new UserDTO(list.get(0));
			printList(list.get(0));
			udao.insertUser(udto);
			
			RestaurantDAO rdao = new RestaurantDAO();
			for (int i = 1;i<11;i++) {
				printList(list.get(i));
				RestaurantDTO rdto = new RestaurantDTO(list.get(i), 0);
				rdao.insertRestaurant(rdto);
				rdao.setFirstIdByUDTO(udto, rdto);
				System.out.println(i);
			}
		}
	}

	private static void printList(String[] strings) {
		// TODO Auto-generated method stub
		for(int i = 0;i<strings.length;i++) {
			System.out.printf("%s/",strings[i]);
		}
		System.out.println();
	}
}