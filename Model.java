
public class Model {
	Student [] studentData = new Student[50];
	int numStudents = 0;
	public Model() {
		
	}
	
	public void storeStudentData(String name, String id, String gpa) {
		Student temp = new Student();
		temp.name = name;
		temp.id = id;
		temp.gpa = gpa;
		studentData[numStudents] = temp;
		numStudents ++;
		
		for (int i = 0; i < numStudents; i++) {
			System.out.println("" + i + " name: " + studentData[i].name);
			System.out.println("" + i + " id: " + studentData[i].id);
			System.out.println("" + i + " gpa: " + studentData[i].gpa);
			
		}
	}
}