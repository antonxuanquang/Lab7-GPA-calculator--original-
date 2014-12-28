
public class DisplayControl {
	
	DisplayView display;
	
	public DisplayControl (DisplayView fromDisplay) {
		display = fromDisplay;
	}
// initialize 
	public void initialize() {
		System.out.println("@Display_model initialize");
		controlCb (false);
		setText();
		checkStudentNumber();
	}
		
	public void setText() {
		ascending();
	}
	
	public void controlCb(boolean b) {
		display.order.setEnabled(b);
		display.field.setEnabled(b);
	}
	
	public void checkStudentNumber() {
		if (display.fromModel.numStudents > 0) {
			display.b_delete_all.setEnabled(true);
			display.b_arrage.setEnabled(true);
		} else {
			display.b_delete_all.setEnabled(false);
			display.b_arrage.setEnabled(false);
			display.model.addElement("******There is no student in Data ******");
		}
	}
// arrange stuff	
	public void ascending() {
		cleanData();
		display.model.addElement(createDisplayString("Student's Name") + createDisplayString("ID Number").substring(0, 15) + createDisplayString("GPA").substring(0, 15)  + "\n");
		display.model.addElement("------------------------------------------------------------------\n");
		for (int i = 0; i < display.fromModel.numStudents; i++) {
			String s = createDisplayString(display.fromModel.studentData[i].name) + createDisplayString("" + display.fromModel.studentData[i].id).substring(0, 15)  
					+ createDisplayString("" + display.fromModel.studentData[i].gpa).substring(0, 15)  + "\n";
			display.model.addElement(s);
		}
	}
	
	public void descending() {
		cleanData();
		display.model.addElement(createDisplayString("Student's Name") + createDisplayString("ID Number").substring(0, 15)  + createDisplayString("GPA").substring(0, 15)  + "\n");
		display.model.addElement("------------------------------------------------------------------\n");
		for (int i = display.fromModel.numStudents; i > 0; i--) {
			String s = createDisplayString(display.fromModel.studentData[i-1].name) + createDisplayString("" + display.fromModel.studentData[i-1].id).substring(0, 15)  
					+ createDisplayString("" + display.fromModel.studentData[i-1].gpa).substring(0, 15)  + "\n";
			display.model.addElement(s);
		}
	}
	
	public void cleanData() {
		display.model.removeAllElements();
	}
	
	public void arrangeByName () {// using my stupid method, this sorting mechanism is not efficient in any terms.
		for (int i = 0; i < display.fromModel.numStudents - 1; i++) {
			int j;
			for (j = i; j < display.fromModel.numStudents; j++) {
				if (display.fromModel.studentData[i].name.compareTo(display.fromModel.studentData[j].name) > 0 ) {
					swapIndex(i, j, display.fromModel.studentData[i], display.fromModel.studentData[j]);
				}
			}
		}
	}
	
	public void arrangeById () {
		int step = 0;
		System.out.println("@arrageId");
		for (int i = 0 ; i < display.fromModel.numStudents-1; i++) {
			
			
																	System.out.print(i + ": ");
																	for (int z = 0; z < display.fromModel.numStudents; z++) {
																		System.out.print(display.fromModel.studentData[z].id + "   ");
																	}
																	System.out.println();
			for (int j = i+1; j < display.fromModel.numStudents; j++) {
				int id1 = Integer.parseInt(display.fromModel.studentData[i].id);
				int id2 = Integer.parseInt(display.fromModel.studentData[j].id);
				if ( id1 > id2) {
					swapIndex(i, j, display.fromModel.studentData[i], display.fromModel.studentData[j]);
																	System.out.print("           ");
																	for (int t = 0; t < display.fromModel.numStudents; t++) {
																		System.out.print(display.fromModel.studentData[t].id + "   ");
																	}
																	System.out.println();
																	step ++;
					j = i;
				} 
																	else step ++;
				
			}
																	step++;
		}
																	System.out.println("Steps = " + step);
	}
	
	public void arrangeByGPA () {
		for (int i = 0; i < display.fromModel.numStudents - 1; i++) {
			int j;
			for (j = i; j < display.fromModel.numStudents; j++) {
				double gpa1 = Double.parseDouble(display.fromModel.studentData[i].gpa);
				double gpa2 = Double.parseDouble(display.fromModel.studentData[j].gpa);
				if (gpa1 > gpa2) {
					swapIndex(i, j, display.fromModel.studentData[i], display.fromModel.studentData[j]);
					j=i;
				}
			}
		}
	}
	
	public void swapIndex(int i, int j, Student student1, Student student2) {
		display.fromModel.studentData[i] = student2;
		display.fromModel.studentData[j] = student1;
	}
	
	public String createDisplayString(String str) {// to make the JList looks like table (display values in columns).
		String blanks = "                                 ";
		int strLength = str.length();
		int int_blank_add_up = blanks.length() - strLength;
		String str_blank_add_up = blanks.substring(0, int_blank_add_up);
		str = str + str_blank_add_up;
		return str;
	}
	
// delete ALL button
	public void deleteAll () {
		for (int i = 0; i < display.fromModel.numStudents; i++) {
			display.fromModel.studentData[i].setGPA("");
			display.fromModel.studentData[i].setId("");
			display.fromModel.studentData[i].setName("");
		}
		display.fromModel.numStudents = 0;
		display.model.removeAllElements();
		display.model.addElement(createDisplayString("Student's Name") + createDisplayString("ID Number").substring(0, 15)  + createDisplayString("GPA").substring(0, 15)  + "\n");
		display.model.addElement("------------------------------------------------------------------\n");
		checkStudentNumber();
		display.b_delete_all.setEnabled(false);
	}
	
// delete button
	public void delete() {// select an index --> remove that index if index > 2; move everything after index in the Student array up 1 position
		int index = display.list.getSelectedIndex();
		display.model.remove(index);
		/*display.fromModel.studentData[index - 2].setName("");
		display.fromModel.studentData[index - 2].setId("");
		display.fromModel.studentData[index - 2].setGPA("");*/
		System.out.println(display.fromModel.numStudents + " " + 1);
		for (int i = (index - 2); i < display.fromModel.numStudents - 1; i++) {
			display.fromModel.studentData[i] = display.fromModel.studentData[i + 1];
		}
		
		display.fromModel.numStudents --;
		for (int i = 0; i < display.fromModel.numStudents; i++) {
			System.out.println("" + i + " name: " + display.fromModel.studentData[i].name);
			System.out.println("" + i + " id: " + display.fromModel.studentData[i].id);
			System.out.println("" + i + " gpa: " + display.fromModel.studentData[i].gpa);
			
		}
		display.b_delete.setEnabled(false);
		checkStudentNumber();
		display.lab7.gui.validate();
	}
}
