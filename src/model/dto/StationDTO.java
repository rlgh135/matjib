package model.dto;

public class StationDTO {

	private String station_name;
	private String station_line;

	public StationDTO() {
	}

	public StationDTO( String station_name, String station_line) {
		this.station_name = station_name;
		this.station_line = station_line;
	}

	public StationDTO(String[] datas) {
		
		this.station_name = datas[0];
		this.station_line = datas[1];

	}

	public String getStationName() {
		return station_name;
	}

	public void setStationName(String stationName) {
		this.station_name = stationName;
	}

	public String getStationLine() {
		return station_line;
	}

	public void setStationLine(String stationLine) {
		this.station_line = stationLine;
	}
}