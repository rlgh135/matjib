package crawling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import model.dao.StationDAO;
import model.dto.StationDTO;

public class InputStation {
	static String[] colname;

	public static void main(String[] args) throws Exception {
		ArrayList<String[]> list = new ArrayList<String[]>();
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader("station.txt");
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
				String[] info = str.split("-");
				list.add(info);
			}

			StationDAO sdao = new StationDAO();
			for (String[] info : list) {
				StationDTO sdto;
				String line = "2호선";
				for (int i = 1; i < info.length; i++) {
					line += ","+info[i];
				}
				sdto = new StationDTO(info[0], line);
				sdao.insertStation(sdto);
			}
		}
	}
}