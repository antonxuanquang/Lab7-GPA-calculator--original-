import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class EnterControl {
	EnterView student;
	Student temp;
	int grade;
	
	double gpa;
	int error_count = 0;
	
	public EnterControl (EnterView fromStudent) {
		student = fromStudent;
	}
	
//initialize the view
	public void tfListener() {//makes sure that buttons is enabled only when user has provided enough information: ID, name, grade and corresponding credit hours
		DocumentListener documentListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
				enableButtons();
			}

			public void insertUpdate(DocumentEvent documentEvent) {
				enableButtons();
			}

			public void removeUpdate(DocumentEvent documentEvent) {
				enableButtons();
			}
			
			public boolean textInTf () {
				boolean b;
				String s = "";
				for (int i = 0; i < student.tf_credit.length; i++) {
					s += student.tf_credit[i].getText();
					s += student.tf_grade[i].getText();
				}
				if (s.length() > 0) {
					b = true;
				} else {
					b = false;
				}
				return b;
			}
			
			public void enableButtons() {
				if (student.tf_id_num.getText().length() > 0) {
					student.b_clear.setEnabled(true);
					if (student.tf_student_name.getText().length() > 0 && textInTf()) {
						student.b_cal.setEnabled(true);
						student.b_save.setEnabled(true);
					}
				}
			}	
		};
		
		// add DocumentListener to text fields
		student.tf_id_num.getDocument().addDocumentListener(documentListener);
		student.tf_gpa.getDocument().addDocumentListener(documentListener);
		student.tf_student_name.getDocument().addDocumentListener(documentListener);
		for (int i = 0; i < student.tf_credit.length; i++) {
			student.tf_credit[i].getDocument().addDocumentListener(documentListener);
			student.tf_grade[i].getDocument().addDocumentListener(documentListener);
		}
	}
	
	public void controlButtons (boolean b) {//enable or disable buttons
		student.b_cal.setEnabled(b);
		student.b_clear.setEnabled(b);
		student.b_save.setEnabled(b);
	}
//calculate GPA
	public void calculate() {//sum-up methods for calculate GPA
		gpa = calculateGPA();
		displayGPA (gpa);
	}
	
	public double calculateGPA() {//check validity of grade and credit hours, then calculate GPA; increase error counts along the way to provide better experience
		double total_grade = 0;
		double total_credit = 0;
		for (int i = 0; i < student.lb_grade.length; i++) {
			grade = convertGrade(student.tf_grade[i].getText().trim(), student.tf_credit[i].getText().trim());
			if (grade < 0) {
				error_count++;
				student.tf_grade[i].setText("invalid input");
			}
			double credit;
			try {
				credit = Double.parseDouble(student.tf_credit[i].getText());
				if (credit > 4 || credit <= 0) {
					error_count++;
					student.tf_credit[i].setText("invalid input");
				}
			} catch (NumberFormatException e) {		
				if (student.tf_credit[i].getText().equals("") ){
				} else {
					error_count++;
					student.tf_credit[i].setText("invalid input");
				}
				credit = 0.0;
			}
			
			total_grade += grade * credit;
			total_credit += credit;
		}
		System.out.println("Total grade: " + total_grade + " and total credit: " + total_credit);
		if (total_credit == 0) {
			gpa = 0.0;
		} else gpa = total_grade/total_credit;
		return gpa;
	}
	
	public int convertGrade(String tf, String tf2) {// convert grade accordingly
		tf = tf.toUpperCase();
		if ((tf.length() == 0) && (tf2.length()>=1)) {
			error_count++;
			grade = 0;
		} else {
			switch (tf) {
			case "A": grade = 4; break;
			case "B": grade = 3; break;
			case "C": grade = 2; break;
			case "D": grade = 1; break;
			case "F": grade = 0; break;
			case "" : grade = 0; break;
			default: grade = -1; break;
			}
		}
		return grade;
	}
	
	public void displayGPA (double gpa) {// display GPA
		if (error_count > 0) {
			student.tf_gpa.setText("invalid input");
			System.out.println("Error Count: " + error_count);
			error_count = 0;
		} else {
			student.tf_gpa.setText(student.decimal.format(gpa));
		}
	}

// for clear button	
	public void clear () {
		student.tf_gpa.setText("");
		student.tf_id_num.setText("");
		student.tf_student_name.setText("");
		for (int i = 0; i < student.tf_credit.length; i++){
			student.tf_credit[i].setText("");
			student.tf_grade[i].setText("");
		}
		controlButtons(false);
	}

// for save button
	public void save() {//harvest student's name, id, and gpa. Then, save it into model by using a method in model class.
		//harvest student data from the interface: getText from a TextField
		//wants: communicate those data 
		boolean b = true;
		int id;
		double gpa;
		String name, id_string, gpa_string;
		try {
			id = Integer.parseInt(student.tf_id_num.getText().trim());
		} catch (NumberFormatException e) {
			student.tf_id_num.setText("invalid input");
			id = 0;
			b = false;
		}
		try {
			gpa = Double.parseDouble(student.tf_gpa.getText().trim());
		} catch (NumberFormatException e) {
			gpa = 0.0;
			b = false;
		}
		name = student.tf_student_name.getText().trim();
		id_string = "" + id;
		gpa_string = "" + gpa;
	// the what happens
		if (b == true) {
			student.model.storeStudentData(name, "" + id_string.trim(), "" + gpa_string.trim());
			System.out.println("@Save");
			clear();
			controlButtons(false);
		}
	}
}
