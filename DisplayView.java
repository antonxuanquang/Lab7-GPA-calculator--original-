import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

public class DisplayView extends JPanel implements ActionListener, ListSelectionListener {
	Lab7 lab7;
	Model fromModel;
	DisplayControl control;
	
	JLabel lb_welcome1 = new JLabel ("Welcome to the Texas Christian University", JLabel.CENTER), 
			lb_welcome2 = new JLabel ("GPA Calculation Program", JLabel.CENTER),
			lb_prompt = new JLabel ("Student's currently in the database are: ", JLabel.CENTER),
			lb_sort = new JLabel ("Make a sorting choices");
	
	DefaultListModel model = new DefaultListModel();
	JList list = new JList  (model);
	JScrollPane jsp = new JScrollPane (list);
	
	JButton b_arrage = new JButton ("Rearrage new Data"),
			b_delete = new JButton ("Delete Student!"),
			b_delete_all = new JButton ("Delete All Student"),
			b_cancel_display = new JButton ("Cancel");
	
	JComboBox order = new JComboBox(),
			field = new JComboBox ();
	
	
	JPanel p1 = new JPanel (new BorderLayout ());
	JPanel p1n = new JPanel (new GridLayout (2,1));
	JPanel p1s = new JPanel ();
	JPanel p2 = new JPanel (new BorderLayout ());
	JPanel p2c = new JPanel ();
	JPanel p2s = new JPanel ();
	JPanel p2b = new JPanel (new GridLayout (3,1));
	
	
	
	private void doNorth () {
		add("North", p1);
		p1.add("North", p1n);
			p1n.add(lb_welcome1);
			p1n.add(lb_welcome2);
				lb_welcome1.setFont(new Font("San Serif", Font.BOLD, 20));
				lb_welcome1.setForeground(Color.RED);
				lb_welcome2.setFont(new Font("San Serif", Font.ITALIC+ Font.BOLD, 18));
				lb_welcome2.setForeground(Color.RED);
		p1.add("Center", lb_prompt);
			lb_prompt.setFont(new Font("San Serif", Font.ITALIC, 15));
			lb_prompt.setForeground(Color.BLUE);
		
			

		p1.add("East", new JLabel());
		p1.add("West", new JLabel());
	}
	
	public void addItem() {
		order.addItem("**Select Order**");
		order.addItem("Ascendingly");
		order.addItem("Descendingly");
		field.addItem("**Select Field**");
		field.addItem("on Student Name");
		field.addItem("on ID Number");
		field.addItem("on GPA");
		
	}
	
	private void doCenter () {
		add("Center", p2);
		p2.add("Center", p2c);
			p2c.add(jsp);
			jsp.setSize(500, 500);
			list.setFont(new Font ("Consolas", Font.PLAIN, 12));
		
		p2.add("South", p2s);
			p2s.add(b_arrage);
			p2s.add(p2b);
				p2b.add(lb_sort);
					lb_sort.setFont(new Font ("San Serif", Font.BOLD + Font.ITALIC, 13));
					lb_sort.setForeground(Color.GREEN);
				p2b.add(order);
				p2b.add(field);
			addItem();
			p2s.add(b_delete);
			p2s.add(b_delete_all);
			p2s.add(b_cancel_display);
				b_cancel_display.setForeground(Color.RED);
	}
	
	public DisplayView(Lab7 fromLab7, Model frommodel) {
		lab7 = fromLab7;
		lab7.setSize (700,450);
		fromModel = frommodel;
		control = new DisplayControl (this);
		setLayout(new BorderLayout());
		doNorth();
		doCenter();
		control.initialize();
		b_arrage.addActionListener(this);
		b_cancel_display.addActionListener(this);
		b_delete.addActionListener(this);
		b_delete.setEnabled(false);
		b_delete_all.addActionListener(this);
		list.addListSelectionListener(this);
	}
	
	public void actionPerformed (ActionEvent ae) {
		if (ae.getSource() == b_cancel_display) {
			lab7.createStartupView();
		} else if (ae.getSource() == b_arrage) {
			if (order.isEnabled()) {
				arrangeItem();
				control.controlCb(false);
			} else {
				control.controlCb(true);
			}
		} else if (ae.getSource() == b_delete_all) {
			control.deleteAll();
		} else if (ae.getSource() == b_delete) {
			control.delete();
		}
	}
	
	public void valueChanged (ListSelectionEvent le) {
		int index = list.getSelectedIndex();
		if (index > 1) {
			if (model.getElementAt(index) != "******There is no student in Data ******") {
				b_delete.setEnabled(true);
				}
		} else {
			b_delete.setEnabled(false);
		}
	}
	
	public void arrangeItem () {
		int index_field = field.getSelectedIndex();
		int index_order = order.getSelectedIndex();
		if (index_field > 0 && index_order > 0 ) {
			switch (index_field) {
			case 0: break;
			case 1: control.arrangeByName(); break;
			case 2: control.arrangeById(); break;
			case 3: control.arrangeByGPA(); break;
			default: break;
			}
			
			switch (index_order) {
			case 0: break;
			case 1: control.ascending(); break;
			case 2: control.descending(); break;
			default: break;
			}
		}
		field.setSelectedIndex(0);
		order.setSelectedIndex(0);
		control.checkStudentNumber();
	}
}
