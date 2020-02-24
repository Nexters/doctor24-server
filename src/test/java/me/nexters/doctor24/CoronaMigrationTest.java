package me.nexters.doctor24;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import me.nexters.doctor24.batch.processor.util.OpeningHourParser;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.corona.model.mongo.CoronaHospital;
import me.nexters.doctor24.medical.corona.repository.CoronaRepository;
import me.nexters.doctor24.medical.hospital.model.HospitalType;

/**
 * @author manki.kim
 */
@SpringBootTest
public class CoronaMigrationTest {

	@Autowired
	private CoronaRepository coronaRepository;

	@Test
	public void migrationFromXlsx() {
		try {
			FileInputStream file = new FileInputStream("/Users/user/Downloads/전국 코로나 진료소.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex = 0;
			XSSFSheet sheet = workbook.getSheetAt(0);
			//행의 수
			for (rowindex = 2; rowindex < 568; rowindex++) {
				//행을읽는다
				XSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					//셀의 수
					XSSFCell name = row.getCell(3);
					XSSFCell latitue = row.getCell(5);
					GeoJsonPoint point = getPoint(latitue);
					if (point == null) {
						continue;
					}
					XSSFCell mon = row.getCell(7);
					XSSFCell tues = row.getCell(8);
					XSSFCell wedn = row.getCell(9);
					XSSFCell thurs = row.getCell(10);
					XSSFCell fri = row.getCell(11);
					XSSFCell sat = row.getCell(12);
					XSSFCell sun = row.getCell(13);
					XSSFCell hol = row.getCell(14);

					String id = "C:" + rowindex;
					CoronaHospital coronaHospital = CoronaHospital.builder()
						.id(id)
						.hospitalType(HospitalType.NORMAL)
						.name(readValue(name).replace("*", ""))
						.phone(readValue(row.getCell(4)))
						.location(point)
						.days(getDays(mon, tues, wedn, thurs, fri, sat, sun, hol))
						.address(readValue(row.getCell(6)))
						.isEmergency(readValue(row.createCell(15)).equals("Y"))
						.build();
					System.out.println(coronaHospital);
					//coronaRepository.save(coronaHospital).block();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Day> getDays(XSSFCell mon, XSSFCell tue, XSSFCell wed, XSSFCell thu, XSSFCell fri, XSSFCell sat,
		XSSFCell sun, XSSFCell hol) {
		List<Day> days = new ArrayList<>();
		if (Objects.nonNull(mon)) {
			String[] parsedTime = timeParse(readValue(mon));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.MONDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		if (Objects.nonNull(tue)) {
			String[] parsedTime = timeParse(readValue(tue));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.TUESDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		if (Objects.nonNull(wed)) {
			String[] parsedTime = timeParse(readValue(wed));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.WEDNESDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		if (Objects.nonNull(thu)) {
			String[] parsedTime = timeParse(readValue(thu));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.THURSDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		if (Objects.nonNull(fri)) {
			String[] parsedTime = timeParse(readValue(fri));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.FRIDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		if (Objects.nonNull(sat)) {
			String[] parsedTime = timeParse(readValue(sat));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.SATURDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		if (Objects.nonNull(sun)) {
			String[] parsedTime = timeParse(readValue(sun));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.SUNDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		if (Objects.nonNull(hol)) {
			String[] parsedTime = timeParse(readValue(hol));
			if (parsedTime.length > 1) {
				days.add(Day.of(Day.DayType.HOLIDAY,
					OpeningHourParser.parseForMig(parsedTime[0]),
					OpeningHourParser.parseForMig(parsedTime[1])));
			}
		}

		return days;
	}

	private String[] timeParse(String value) {
		return value.split(",");
	}

	private GeoJsonPoint getPoint(XSSFCell latitue) {
		if (latitue == null) {
			return null;
		}
		String lat = readValue(latitue);
		if (lat.equals("false")) {
			return null;
		}
		String[] latSplit = lat.split(",");
		if (latSplit.length < 2) {
			return null;
		}
		return new GeoJsonPoint(Double.parseDouble(latSplit[1]), Double.parseDouble(latSplit[0]));
	}

	private String readValue(XSSFCell cell) {
		if (cell == null) {
			return "";
		} else {
			switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_FORMULA:
					return cell.getCellFormula();
				case XSSFCell.CELL_TYPE_NUMERIC:
					return cell.getNumericCellValue() + "";
				case XSSFCell.CELL_TYPE_STRING:
					return cell.getStringCellValue() + "";
				case XSSFCell.CELL_TYPE_BLANK:
					return cell.getBooleanCellValue() + "";
				case XSSFCell.CELL_TYPE_ERROR:
					return cell.getErrorCellValue() + "";
			}
		}
		return "";
	}
}
