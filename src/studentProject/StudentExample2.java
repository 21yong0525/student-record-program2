package studentProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class StudentExample2 {

	public static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		// <학생의학번, Student> map = new HashMap 객체 생성
		Map<String, Student> map = new HashMap<String, Student>();
		
		
		// while 에 flag를 주기위함
		boolean mainFlag = false;
		// 메뉴선택에 사용하기 위함
		int select = 0;

		while (!mainFlag) {
			switch (displayMenu(select)) {
			case 1:
				studentInput(map);  
				break;
			case 2:
				scoreChange(map); 
				break;
			case 3:
				studentSearch(map); 
				break;
			case 4:
				studentDelete(map); 
				break;
			case 5:
				studentOutput(map); 
				break;
			case 6:
				studentSorting(map); 
				break;
			case 7:
				studentStats(map);
				break;
			case 8 :
				save(map);
				break;
			case 9:
				save(map);
				System.out.println("-".repeat(80));
				System.out.println("종료합니다.");
				mainFlag = true;
				break;
			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}
		}
	}
	
	/** 메뉴선택을 위해서 사용합니다. */
	public static int displayMenu(int select) {
		System.out.println("-".repeat(80));
		try {
			System.out.println("1.입력 || 2.수정 || 3.검색 || 4.삭제 || 5.출력 || 6.정렬 || 7.통계 || 8.저장 || 9.종료");
			System.out.println("-".repeat(80));
			select = sc.nextInt();
		} catch (java.util.InputMismatchException e) {
		} finally {
			sc.nextLine();
		}
		return select;
	}
	/** 학생 정보를 입력하기 위해서 사용합니다. */
	public static void studentInput(Map<String, Student> map) {
		// 학생정보
		String name = RandomValue.randomName2();
		int level = (int) (Math.random() * (3 - 1 + 1) + (1));
		int csNo = (int) (Math.random() * (9 - 1 + 1) + (1));
		int stNo = (int) (Math.random() * (30 - 1 + 1) + (1));
		String no = String.format("%02d%02d%02d", level, csNo, stNo);
		boolean gender = (int) (Math.random() * (1 - 0 + 1) + (0)) == 1 ? true : false;
		int kor = (int) (Math.random() * (100 - 0 + 1) + (0));
		int eng = (int) (Math.random() * (100 - 0 + 1) + (0));
		int math = (int) (Math.random() * (100 - 0 + 1) + (0));

		// 선생님정보
		int phoneNo1 = (int) (Math.random() * (9999 - 0 + 1) + (0));
		int phoneNo2 = (int) (Math.random() * (9999 - 0 + 1) + (0));
		String phone = String.format("010-%04d-%04d", phoneNo1, phoneNo2);

		String teacherName = RandomValue.randomName2();
		int age = (int) (Math.random() * (80 - 20 + 1) + (20));
		String major = RandomValue.major();

		Student st = new Student(name, no, gender, kor, eng, math);

		st.calTotal();
		st.calAvg();
		st.calGrade();

		st.setTeacherName(teacherName);
		st.setPhone(phone);
		st.setAge(age);
		st.setMajor(major);

		map.put(no, st);
		System.out.println(name + "학생의 정보가 입력되었습니다.");
		return;
	}
	/** 학생 정보를 수정하기 위해서 사용합니다. */
	public static void scoreChange(Map<String, Student> map) {
		if (map.isEmpty()) {
			System.out.println("입력 정보가 없습니다.");
			return;
		}
		System.out.println("성적 수정할 학생의 학번을 입력하세요");
		String no = sc.nextLine();

		if (map.containsKey(no)) {
			Student value = map.get(no);
			System.out.println(value + "\n점수를 수정합니다");

			int saveKor = value.getKor();
			int saveEng = value.getEng();
			int saveMath = value.getMath();

			try {
				System.out.print("국어점수 입력 : ");
				value.setKor(sc.nextInt());
				if (value.getKor() < 0 || value.getKor() > 100) {
					System.out.println("국어 점수를 잘못 입력하였습니다.");
					value.setKor(saveKor);
					return;
				}
				System.out.print("영어점수 입력 : ");
				value.setEng(sc.nextInt());
				if (value.getEng() < 0 || value.getEng() > 100) {
					System.out.println("영어 점수를 잘못 입력하였습니다.");
					value.setEng(saveEng);
					return;
				}
				System.out.print("수학점수 입력 : ");
				value.setMath(sc.nextInt());
				if (value.getMath() < 0 || value.getMath() > 100) {
					System.out.println("수학 점수를 잘못 입력하였습니다.");
					value.setMath(saveMath);
					return;
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("잘못된 값을 입력하였습니다.");
				value.setKor(saveKor);
				value.setEng(saveEng);
				value.setMath(saveMath);
				return;
			} finally {
				sc.nextLine();
			}

			value.calTotal();
			value.calAvg();
			value.calGrade();
			
			System.out.println("수정이 완료 되었습니다.");
			return;
		}
		System.out.println("해당 학번의 학생이 없습니다.");
	}
	/** 학생 정보를 검색하기 위해서 사용합니다. */
	public static void studentSearch(Map<String, Student> map) {
		if (map.isEmpty()) {
			System.out.println("입력 정보가 없습니다.");
			return;
		}
		System.out.println("검색할 학생의 학번을 입력하세요");
		String no = sc.nextLine();

		if (map.containsKey(no)) {
			Student value = map.get(no);
			System.out.println(value);
			return;
		}
		System.out.println("해당 학번의 학생이 없습니다.");
	}
	/** 학생 정보를 삭제하기 위해서 사용합니다. */
	public static void studentDelete(Map<String, Student> map) {
		if (map.isEmpty()) {
			System.out.println("입력 정보가 없습니다.");
			return;
		}
		
		System.out.println("삭제할 학생의 학번을 입력하세요");
		String no = sc.nextLine();

		if (map.containsKey(no)) {
			Student value = map.get(no);
			System.out.println(value.getName() + " 학생의 정보가 삭제되었습니다.");
			map.remove(no);
			return;
		}
		System.out.println("해당 학번의 학생이 없습니다.");
	}
	/** 학생 정보를 출력하기 위해서 사용합니다. */
	public static void studentOutput(Map<String, Student> map) {
		if (map.isEmpty()) {
			System.out.println("입력 정보가 없습니다.");
			return;
		}
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String value = iterator.next();
			System.out.println(map.get(value));
		}
	}
	/** 학생 정보를 정렬하기 위해서 사용합니다. */
	public static void studentSorting(Map<String, Student> map) {
		if (map.isEmpty()) {
			System.out.println("입력 정보가 없습니다.");
			return;
		}
		
		System.out.println("1.오름차순 || 2.내림차순");
		String no = sc.nextLine();
		
		List<Student> valueList = new ArrayList<>(map.values());
		int rank = 0;
		if (no.equals("1")) {
			valueList.sort(Comparator.naturalOrder());
	        for (Student data : valueList) {
	        	data.setRank(map.size() - rank);
	        	rank += 1;
	        	System.out.println(data);
	        }
		} else if (no.equals("2")) {
			valueList.sort(Comparator.reverseOrder());
	        for (Student data : valueList) {
	        	rank += 1;
	        	data.setRank(rank);
	        	System.out.println(data);
	        }
		} else {
			System.out.println("다시입력하세요");
			return;
		}
	}
	/** 학생 정보를 통계하기 위해서 사용합니다. ex) 최고,평균점수 점수비교 */
	public static void studentStats(Map<String, Student> map) {
		if (map.isEmpty()) {
			System.out.println("입력 정보가 없습니다.");
			return;
		}

		int maxScore = Integer.MIN_VALUE;
		String maxName = null;
		int totalScore = 0;
		double totalAvr = 0.0;
		double comparison = 0.0;
		
		List<Student> valueList = new ArrayList<>(map.values());
		
		
		for (int i=0;i<map.size();i++) {
			valueList.get(0).getTotal();
			if (maxScore < valueList.get(i).getTotal()) {
				maxScore = valueList.get(i).getTotal();
				maxName = valueList.get(i).getName();
			}
			totalScore += valueList.get(i).getTotal();
		}
		
		totalAvr = (double)totalScore/map.size();
		comparison = (double)maxScore - totalAvr;
		
		
		System.out.println("최고점수 : " + maxName + " " + maxScore + "점");
		System.out.println("모든 학생 총점 : " + totalScore + "점");
		System.out.println("모든 학생 평균 : " + String.format("%.2f점", totalAvr));
		
		if (maxScore > totalAvr) {
			System.out.println(maxName + "학생의 점수는 평균보다 " + String.format("%.2f점", comparison) + " 높습니다.");
		} 
	}
	/** 입력된 정보를 저장하기 위해서 사용합니다. */
	public static void save(Map<String, Student> map) throws Exception {
		File file = new File("C:/temp/StudentData.txt");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(map);
		oos.flush();
		
		FileInputStream fis = new FileInputStream(file); 
		ObjectInputStream ois = new ObjectInputStream(fis);
		
				
		Object obj = ois.readObject();
		
		if (obj instanceof Map) {
			Map<String,Student> imsiMap = (Map<String,Student>)obj;
			Set<String> set = imsiMap.keySet();
			Iterator<String> iterator = set.iterator();
			while(iterator.hasNext()) {
				String no = iterator.next();
				Student st = imsiMap.get(no);
				System.out.println(st);
			}
		}
		System.out.println("저장이 완료되었습니다.");
	}
}
