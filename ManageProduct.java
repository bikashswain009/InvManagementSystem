import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

class ManageProduct extends JFrame implements ActionListener {
    Container c;
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JTextField t1, t2, t3, t4, t5;
    JButton b1, b2, b3, b4;
    JTable table;
    JComboBox<String> categoryDropdown;  //drop down


    ManageProduct() {
        setTitle("Manage Product");
        setSize(860, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(100, 100);
        setVisible(true);
        c = getContentPane();
		
		//set icon
		ImageIcon icon = new ImageIcon("inventory_icon.png");
		setIconImage(icon.getImage());

        l1 = new JLabel("Product ID");
        l2 = new JLabel("Name");
        l3 = new JLabel("Quantity");
        l4 = new JLabel("Description");
        l5 = new JLabel("Price");
        l6 = new JLabel("Category");
		l7 = new JLabel("Products List");

        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        t4 = new JTextField();
        t5 = new JTextField();
        //t6 = new JTextField();

        b1 = new JButton("Add");
        b2 = new JButton("Edit");
        b3 = new JButton("Home");
        b4 = new JButton("Delete");

        l1.setBounds(50, 60, 100, 30);
        l2.setBounds(50, 110, 100, 30);
        l3.setBounds(50, 160, 100, 30);
        l4.setBounds(50, 210, 100, 30);
        l5.setBounds(50, 260, 100, 30);
        l6.setBounds(50, 310, 100, 30);
		l7.setBounds(520, 20, 200, 30);

        t1.setBounds(150, 60, 150, 30);
        t2.setBounds(150, 110, 150, 30);
        t3.setBounds(150, 160, 150, 30);
        t4.setBounds(150, 210, 150, 30);
        t5.setBounds(150, 260, 150, 30);
        //t6.setBounds(150, 310, 150, 30);


        b1.setBounds(50, 400, 90, 30);
        b2.setBounds(160, 400, 90, 30);
        b3.setBounds(270, 400, 90, 30);
        b4.setBounds(380, 400, 90, 30);

        c.add(l1);
        c.add(l2);
        c.add(l3);
        c.add(l4);
        c.add(l5);
        c.add(l6);
        c.add(t1);
        c.add(t2);
        c.add(t3);
        c.add(t4);
        c.add(t5);
		c.add(l7);
        
        c.add(b1);
        c.add(b2);
        c.add(b3);
        c.add(b4);

        //Design textfields
        Font labelFont = new Font("Arial", Font.BOLD, 15);   
        l1.setFont(labelFont);   
        l2.setFont(labelFont);
        l3.setFont(labelFont);
        l4.setFont(labelFont);
        l5.setFont(labelFont);
        l6.setFont(labelFont);
		l7.setFont(labelFont);

         //Design buttons
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
        b2.setBackground(Color.BLUE);
        b2.setForeground(Color.WHITE);
        b3.setBackground(Color.BLUE);
        b3.setForeground(Color.WHITE);
        b4.setBackground(Color.BLUE);
        b4.setForeground(Color.WHITE);
        Font buttonFont = new Font("Arial Rounded MT Bold", Font.BOLD, 15);
        b1.setFont(buttonFont);
        b2.setFont(buttonFont);
        b3.setFont(buttonFont);
        b4.setFont(buttonFont);

        // Create table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("PID");
        model.addColumn("PNAME");
		model.addColumn("QUANTITY");
		model.addColumn("DESCRIPTION");
		model.addColumn("PRICE");
		model.addColumn("CNAME");
		
		//create table
		table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(320, 55, 510, 320);
        c.add(scrollPane);
		
		// Fetch and populate the table with existing data
        fetchAndPopulateTable();

        // Create the category drop-down menu
        categoryDropdown = new JComboBox<>();
        categoryDropdown.setBounds(150, 310, 150, 30);
        c.add(categoryDropdown);

        // Populate the category drop-down menu
        populateCategoryDropdown();


        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

    }


    	// Method to fetch data from the database and populate the table
        private void fetchAndPopulateTable() {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA", "swain2001");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM PRODUCTS");

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Clear existing rows in the table

                while (rs.next()) {
                    int pid = rs.getInt("PID");
                    String n = rs.getString("PNAME");
                    String q = rs.getString("QUANTITY");
                    String des = rs.getString("DESCRIPTION");
                    double pr = rs.getDouble("PRICE");
                    String cat = rs.getString("CNAME");

                    model.addRow(new Object[]{pid, n, q, des, pr, cat});

                    // Fetch the table header 
                        JTableHeader header = table.getTableHeader();
                        header.setBackground(Color.BLUE); 
                        header.setForeground(Color.WHITE); 
                        header.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12)); 
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
            int pid = Integer.parseInt(t1.getText());
            String n = t2.getText();
            String q = t3.getText();
            String des = t4.getText();
            double pr = Double.parseDouble(t5.getText());
            String cat = categoryDropdown.getSelectedItem().toString();
			
			// Check if PID already exists
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                int existingPid = (int) model.getValueAt(i, 0);
                if (existingPid == pid) {
                    JOptionPane.showMessageDialog(this, "Product ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
				// Insert data into the table model
                model.addRow(new Object[]{pid, n, q, des, pr,cat});
				
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA",
                        "swain2001");
                String sql = "INSERT INTO PRODUCTS VALUES(" + pid + ",'" + n + "','" + q + "','" + des + "'," + pr + ",'"+cat+"')";
                Statement st = con.createStatement();
                int i = st.executeUpdate(sql);
                if (i > 0) {
                    System.out.println("One record inserted");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    t5.setText("");
                    //t6.setText("");
                } else {
                    System.out.println("DB ERROR");
                }
                st.close();
                con.close();
            } catch (Exception ob) {
                System.out.println(ob);
            }
        }	
		else if (e.getSource() == b2) {
            // CODE FOR EDIT BUTTON
            int pid = Integer.parseInt(t1.getText());
            String n = t2.getText();
            String q = t3.getText();
            String des = t4.getText();
            int pr = Integer.parseInt(t5.getText());
            //String cat = categoryDropdown.getSelectedItem().toString();

                try{
                   //update data in the table model
                    DefaultTableModel model=(DefaultTableModel) table.getModel();
                    int rowCount = model.getRowCount();
                    int rowIndex = -1;

                    //find the row index of the product id
                    for(int i=0; i < rowCount; i++){
                        int existingPid = (int) model.getValueAt(i,0);
                        if(existingPid == pid){
                            rowIndex = i;
                            break;
                        }
                    }

                    //If product id is found, update the fields
                    if(rowIndex != -1)
                    {
                        model.setValueAt(n, rowIndex, 1);
                        model.setValueAt(q, rowIndex, 2);
                        model.setValueAt(des, rowIndex, 3);
                        model.setValueAt(pr, rowIndex, 4);
                        //model.setValueAt(cat, rowIndex, 5);

                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA", "swain2001");
                        String sql = "UPDATE Products SET PNAME = '" + n + "', QUANTITY = '" + q + "', DESCRIPTION = '" + des + "', PRICE = " + pr + " WHERE PID ="+pid ;
                        Statement st = con.createStatement();
                        int i = st.executeUpdate(sql);
                        if (i > 0) {
                            System.out.println("One record updated");
                            t1.setText("");
                            t2.setText("");
                            t3.setText("");
                            t4.setText("");
                            t5.setText("");
                        } else {
                            System.out.println("DB ERROR");
                        }
                        st.close();
                        con.close();
                    } else {
                    JOptionPane.showMessageDialog(this, "Product ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ob) {
                System.out.println(ob);
            } 
        }
         else if (e.getSource() == b3) {
            // CODE FOR HOME BUTTON
            new MainPage();            // Create an instance of the main page
            setVisible(false);       // Hide current frame 
        } else if (e.getSource() == b4) {
            // CODE FOR DELETE BUTTON
            int pid = Integer.parseInt(t1.getText());

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA",
                        "swain2001");
                String sql = "DELETE FROM Products WHERE PID = " + pid;
                Statement st = con.createStatement();
                int i = st.executeUpdate(sql);
				fetchAndPopulateTable();
                if (i > 0) {
                    System.out.println("One record deleted");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    t5.setText("");
                    //t6.setText("");
                } else {
                    System.out.println("DB ERROR");
                }
                st.close();
                con.close();
            } catch (Exception ob) {
                System.out.println(ob);
            }
        }

    }

    private void populateCategoryDropdown() {
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA", "swain2001");
        Statement st = con.createStatement();
        String sql = "SELECT CATNAME FROM CATAGORY";
        ResultSet rs = st.executeQuery(sql);


        // Add category names to the drop-down menu
        while (rs.next()) {
            String categoryName = rs.getString("CATNAME");
            categoryDropdown.addItem(categoryName);
        }

        rs.close();
        st.close();
        con.close();
    } catch (Exception e) {
        System.out.println(e);
    }
}



    public static void main(String args[]) {
        new ManageProduct();
    }
}


