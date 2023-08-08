import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ManageCategory extends JFrame implements ActionListener {
    Container c;
    JLabel l1, l2, l3;
    JTextField t1, t2;
    JButton b1, b2, b3;
	JTable table;

    ManageCategory() {
        setTitle("Manage Category");
        setSize(850, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(100, 100);
        setVisible(true);
        c = getContentPane();
		
		//set icon
		ImageIcon icon = new ImageIcon("inventory_icon.png");
		setIconImage(icon.getImage());

        l1 = new JLabel("Category ID");
        l2 = new JLabel("Name");
		l3 = new JLabel("Category List");

        t1 = new JTextField();
        t2 = new JTextField();

        b1 = new JButton("Add");
        b2 = new JButton("Edit");
        b3 = new JButton("Home");

        l1.setBounds(50, 50, 100, 30);
        l2.setBounds(50, 100, 100, 30);
		l3.setBounds(520, 20, 200, 30);

        t1.setBounds(150, 50, 170, 30);
        t2.setBounds(150, 100, 170, 30);

        b1.setBounds(50, 200, 90, 30);
        b2.setBounds(160, 200, 90, 30);
        b3.setBounds(270, 200, 90, 30);

        c.add(l1);
        c.add(l2);
		c.add(l3);
        c.add(t1);
        c.add(t2);
        c.add(b1);
        c.add(b2);
        c.add(b3);

        // Design textfields
        Font labelFont = new Font("Arial", Font.BOLD, 15);
        l1.setFont(labelFont);
        l2.setFont(labelFont);
		l3.setFont(labelFont);

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
		
		// Create table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("CATID");
        model.addColumn("CATNAME");
		
		//create table
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
            ResultSet rs = st.executeQuery("SELECT * FROM CATAGORY");

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows in the table

            while (rs.next()) {
                int catid = rs.getInt("CATID");
                String catname = rs.getString("CATNAME");

                model.addRow(new Object[]{catid, catname});

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
            int categoryID = Integer.parseInt(t1.getText());
            String name = t2.getText();
			
			// Check if CATID already exists
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                int existingCatid = (int) model.getValueAt(i, 0);
                if (existingCatid == categoryID) {
                    JOptionPane.showMessageDialog(this, "Category ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
				// Insert data into the table model
                model.addRow(new Object[]{categoryID, name});
				
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA",
                        "swain2001");
                String sql = "INSERT INTO CATAGORY VALUES(" + categoryID + ",'" + name + "')";
                Statement st = con.createStatement();
                int i = st.executeUpdate(sql);
                if (i > 0) {
                    System.out.println("One record inserted");
                    t1.setText("");
                    t2.setText("");
                } else {
                    System.out.println("DB ERROR");
                }
				//name = getCategories();
                st.close();
                con.close();
            } catch (Exception ob) {
                System.out.println(ob);
            }
        } else if (e.getSource() == b2) {
            // CODE FOR EDIT BUTTON
            int categoryID = Integer.parseInt(t1.getText());
            String name = t2.getText();

            try {
				// Update data in the table model
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int rowCount = model.getRowCount();
                int rowIndex = -1;

                // Find the row index of the customer ID
                for (int i = 0; i < rowCount; i++) {
                    int existingCid = (int) model.getValueAt(i, 0);
                    if (existingCid == categoryID) {
                        rowIndex = i;
                        break;
                    }
                }

                // If customer ID is found, update the fields
                if (rowIndex != -1) {
                    model.setValueAt(name, rowIndex, 1);
                   // model.setValueAt(phone, rowIndex, 2);
                
				
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA",
                        "swain2001");
                String sql = "UPDATE CATAGORY SET catname = '" + name + "' WHERE catid = " + categoryID+ " ";
                Statement st = con.createStatement();
                int i = st.executeUpdate(sql);
                if (i > 0) {
                    System.out.println("One record updated");
                    t1.setText("");
                    t2.setText("");
                } else {
                    System.out.println("DB ERROR");
                }
                st.close();
                con.close();
				}else {
                    JOptionPane.showMessageDialog(this, "Category ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
             catch (Exception ob) {
                System.out.println(ob);
            }
			
        } else if (e.getSource() == b3) {
            // CODE FOR HOME BUTTON
            new MainPage();
            setVisible(false);  //hide current frame
        }
    }

    public static void main(String args[]) {
        new ManageCategory();
    }
}
