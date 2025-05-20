package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.io.Serializable;

public class payrollBilling extends JFrame implements ActionListener, Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private JLabel nameLabel, positionLabel, statusLabel, salaryLabel, otLabel;
	private JTextField nameField, positionField, statusField, salaryField, otField;
	private JButton calculateButton;
	private JPanel panel;
	
	public static String name, position, status;
	static double total = 0, wtax = 0, phic = 0, gsis = 0, ot = 0, gross = 0, deduction = 0, salary, overtime, pagibig = 200;
	
	public payrollBilling() {
		setTitle("Payroll Information");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		nameLabel = new JLabel("Name: ");
		positionLabel = new JLabel("Profession: ");
		statusLabel = new JLabel("Status of Appointment: ");
		salaryLabel = new JLabel("Basic Salary: ");
		otLabel = new JLabel("Overtime (hours): ");
		
		nameField = new JTextField(20);
		positionField = new JTextField(20);
		statusField = new JTextField(20);
		salaryField = new JTextField(20);
		otField =new JTextField(20);
		
		calculateButton = new JButton("Calculate Payroll");
		calculateButton.addActionListener(this);
		
		panel = new JPanel(new GridLayout (6, 2, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(positionLabel);
		panel.add(positionField);
		panel.add(statusLabel);
		panel.add(statusField);
		panel.add(salaryLabel);
		panel.add(salaryField);
		panel.add(otLabel);
		panel.add(otField);
		panel.add(new JLabel());
		panel.add(calculateButton);
		
		add(panel);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == calculateButton) {
			name = nameField.getText();
			position = positionField.getText();
			status = statusField.getText();
			
			try {
				salary = Integer.parseInt(salaryField.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid salary input.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				overtime = Integer.parseInt(otField.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid overtime input.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			showPayrollResultFrame();
			this.dispose();
		}
	}
	
	private void showPayrollResultFrame() {
		JFrame resultFrame = new JFrame("Payroll Calculation Results");
		resultFrame.setSize(300, 500);
		resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		resultFrame.setLocationRelativeTo(null);
		
		JPanel resultPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		calculateOt();
		calculateGross();
		calculatePhic();
		calculateGsis();
		calculateWtax();
		calculateDeduction();
		calculateTotal();
		
		JLabel nameResultLabel = new JLabel("Name:  " + name);
		JLabel positionResultLabel = new JLabel("Profession:  " + position);
		JLabel statusResultLabel = new JLabel("Status of Appointment:  " + status);
		JLabel salaryResultLabel = new JLabel("Basic Salary:  " + salary);
		JLabel overtimeResultLabel = new JLabel("Overtime (hour):  " + overtime);
		JLabel otResultLabel = new JLabel("Overtime Pay:  ₱ " + String.format("%.2f", ot));
		JLabel separator1 = new JLabel("----------------------------------");
		JLabel grossResultLabel = new JLabel("Gross:  ₱ " + String.format("%.2f", gross));
		JLabel separator2 = new JLabel("----------------------------------");
		JLabel wtaxResultLabel = new JLabel("Withholding Tax:  ₱ " + String.format("%.2f", wtax));
		JLabel pagibigResultLabel = new JLabel("Pagibig:  ₱ " + pagibig);
		JLabel phicResultLabel = new JLabel("Philhealth:  ₱ " + String.format("%.2f", phic));
		JLabel gsisResultLabel = new JLabel("GSIS:  ₱ " + String.format("%.2f", gsis));
		JLabel separator3 = new JLabel("----------------------------------");
		JLabel deductionResultLabel = new JLabel("Toatal Deduction:  ₱ " + String.format("%.2f", deduction));
		JLabel separator4 = new JLabel("----------------------------------");
		JLabel totalResultLabel = new JLabel("Total Pay:  ₱ " + String.format("%.2f", total));
		
		gbc.gridy = 0;
		resultPanel.add(nameResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(positionResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(statusResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(statusResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(overtimeResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(salaryResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(otResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(separator1, gbc);
		
		gbc.gridy++;
		resultPanel.add(grossResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(separator2, gbc);
		
		gbc.gridy++;
		resultPanel.add(wtaxResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(pagibigResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(phicResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(gsisResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(separator3, gbc);
		
		gbc.gridy++;
		resultPanel.add(deductionResultLabel, gbc);
		
		gbc.gridy++;
		resultPanel.add(separator4, gbc);
		
		gbc.gridy++;
		resultPanel.add(totalResultLabel, gbc);
		
		resultFrame.add(resultPanel);
		resultFrame.setVisible(true);
	}
	
	private void calculatePhic() {
		phic = (salary *0.05) / 2;
	}
	
	private void calculateGsis() {
		gsis = salary * 0.09;
	}
	
	private void calculateGross() {
		gross = salary + ot;
	}
	
	private void calculateOt() {
		ot = ((salary / 30.0) / 8.0) * 1.25 * overtime;
	}
	
	private void calculateWtax() {
		if (salary <= 20832) {
			wtax = 0;
		} else if (salary >= 20833 && salary <= 33332) {
			wtax = (salary - (phic + gsis + pagibig) - 20833) * 0.15;
		} else if (salary >= 33333 && salary <= 66666) {
			wtax = ((salary - (phic + gsis + pagibig) - 33333) * 0.20) + 1875;
		} else if (salary >= 66667 && salary <=166666) {
			wtax = ((salary - (phic + gsis + pagibig) - 66667) * 0.25) + 8541.80;
		} else if (salary >= 166667 && salary <= 666666) {
			wtax =((salary - (phic + gsis + pagibig) - 166667) * 0.30) + 33541.80;
		} else {
			wtax = ((salary - (phic + gsis + pagibig) - 666667) * 0.35) +183541.80;
		}
	}
	
	private void calculateDeduction() {
		deduction = gsis + phic + wtax +pagibig;
	}
	
	private void calculateTotal() {
		total = gross - deduction;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new payrollBilling());
	}

}