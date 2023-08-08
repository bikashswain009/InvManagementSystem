import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

class ManageUsers extends JFrame implements ActionListener {
Container c;
JLabel l1, l2, l3, l4, l5, l6;
JTextField t1, t2, t3, t4;
JPasswordField passwordField;
JButton b1, b2, b3;
JTable table;
ManageUsers() {
    setTitle("Manage Users");
    setSize(800, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(null);
    setLocation(100, 100);
    setVisible(true);
    c = getContentPane();
	
	//set icon
	ImageIcon icon = new ImageIcon("inventory_icon.png");
	setIconImage(icon.getImage());

    l1 = new JLabel("Username");
    l2 = new JLabel("Password");
    l3 = new JLabel("Phone");
    l4 = new JLabel("Email ID");
	l6 = new JLabel("User List");

    t1 = new JTextField();
    passwordField = new JPasswordField();
    t3 = new JTextField();
    t4 = new JTextField();

    b1 = new JButton("Add");
    b2 = new JButton("Reset");
    b3 = new JButton("Home");

    l1.setBounds(50, 50, 100, 30);
    l2.setBounds(50, 100, 100, 30);
    l3.setBounds(50, 150, 100, 30);
    l4.setBounds(50, 200, 100, 30);
	l6.setBounds(520, 20, 200, 30);

    t1.setBounds(150, 50, 170, 30);
    passwordField.setBounds(150, 100, 170, 30);
    t3.setBounds(150, 150, 170, 30);
    t4.setBounds(150, 200, 170, 30);

    b1.setBounds(50, 280, 90, 30);
    b2.setBounds(160, 280, 90, 30);
    b3.setBounds(270, 280, 90, 30);

    c.add(l1);
    c.add(l2);
    c.add(l3);
    c.add(l4);
    c.add(t1);
    c.add(passwordField);
    c.add(t3);
    c.add(t4);
    c.add(b1);
    c.add(b2);
    c.add(b3);
	c.add(l6);

    // Design labels
    Font labelFont = new Font("Arial", Font.BOLD, 15);
    l1.setFont(labelFont);
    l2.setFont(labelFont);
    l3.setFont(labelFont);
    l4.setFont(labelFont);
	l6.setFont(labelFont);

    // Design buttons
    b1.setBackground(Color.BLUE);
    b1.setForeground(Color.WHITE);
    b2.setBackground(Color.BLUE);
    b2.setForeground(Color.WHITE);
    b3.setBackground(Color.BLUE);
    b3.setForeground(Color.WHITE);
    Font buttonFont = new Font("Arial Rounded MT Bold", Font.BOLD, 15);
    b1.setFont(buttonFont);
    b2.setFont(buttonFont);
    b3.setFont(buttonFont);
	
	//Design Table Model
	DefaultTableModel model = new DefaultTableModel();
        model.addColumn("USERNAME");
        model.addColumn("PASSWORD");
        model.addColumn("PHONE");
		model.addColumn("EMAIL");
		
		// Create table
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 55, 350, 230);
        c.add(scrollPane);
		
	// Fetch and populate the table with existing data
        fetchAndPopulateTable();
	

    b1.addActionListener(this);
    b2.addActionListener(this);
    b3.addActionListener(this);
}

// Method to fetch data from the database and populate the table
    private void fetchAndPopulateTable() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA", "swain2001");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM USERS");

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows in the table

            while (rs.next()) {
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");

                model.addRow(new Object[]{username, password, phone, email});

                // Fetch the table header 
                    JTableHeader header = table.getTableHeader();
                    header.setBackground(Color.BLUE); 
                    header.setForeground(Color.WHITE); 
                    header.setFont(new Font("Arial", Font.BOLD, 14)); 
            }

            rs.close();
            st.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == b1) {
        // CODE FOR ADD BUTTON
        String username = t1.getText();
        String password = new String(passwordField.getPassword());
        String phone = t3.getText();
        String email = t4.getText();
		
		// Check if username already exists
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String existingUsername = (String) model.getValueAt(i, 0);
                if (existingUsername.equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

        try {
			model.addRow(new Object[]{username, password, phone, email});
			
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA", "swain2001");
            String sql = "INSERT INTO USERS VALUES('" + username + "','" + password + "','" + phone + "','" + email + "')";
            Statement st = con.createStatement();
            int i = st.executeUpdate(sql);
            if (i > 0) {
                System.out.println("One record inserted");
                t1.setText("");
                passwordField.setText("");
                t3.setText("");
                t4.setText("");
            } else {
                System.out.println("DB ERROR");
            }
            st.close();
            con.close();
        } catch (Exception ob) {
            System.out.println(ob);
        }
    } else if (e.getSource() == b2) {
        // CODE FOR RESET BUTTON
        t1.setText("");
        passwordField.setText("");
        t3.setText("");
        t4.setText("");
    } else if (e.getSource() == b3) {
        // CODE FOR HOME BUTTON
        new MainPage();
        setVisible(false);  //hide current frame
    }
}

public static void main(String args[]) {
    new ManageUsers();
}
}
