import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
//import javax.swing.table.DefaultTableModel;

class InventoryManagementSystem extends JFrame implements ActionListener 
{
    JTextField userIdTextField;
    JPasswordField passwordField;
    JButton loginButton, clearButton;

InventoryManagementSystem() 
{
	//font
		Font f = new Font("Serif",Font.BOLD, 40);
		Font f1 = new Font("Serif",Font.BOLD, 20);
		Font f2 = new Font("Arial Rounded MT Bold", Font.BOLD, 15);
		
		//header
		JPanel heading;
		heading = new JPanel();
		heading.setBackground(new Color(0,0,0,100));
		
		heading.setBounds(0,0,900,100);
		JLabel name = new JLabel("Login Page");
		name.setForeground(Color.WHITE);
		name.setFont(f);
		heading.add(name);
		
		
		//login panel
		JPanel login = new JPanel();
		login.setLayout(null);
		login.setSize(400,350);
		login.setBackground(new Color(0,0,0,120));
		login.setBounds(250,175,400,350);
		
		//Label
		JLabel l1 = new JLabel("User ID");
		l1.setFont(f1);
		l1.setForeground(Color.WHITE);
		l1.setBounds(50,10,300,50);
		login.add(l1);
		
		JLabel l2 = new JLabel("Password");
		l2.setFont(f1);
		l2.setForeground(Color.WHITE);
		l2.setBounds(50,110,300,50);
		login.add(l2);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(50, 250, 90, 30);
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLUE);
		loginButton.setFont(f1);
		login.add(loginButton);
		
		
		clearButton = new JButton("Clear");
		clearButton.setBounds(260, 250, 90, 30);
		clearButton.setForeground(Color.WHITE);
		clearButton.setBackground(Color.BLUE);
		clearButton.setFont(f1);
		login.add(clearButton);
		
		
		userIdTextField = new JTextField("");
		userIdTextField.setBounds(50,50,300,40);
		userIdTextField.setFont(f2);
		userIdTextField.setForeground(Color.WHITE);
		userIdTextField.setBackground(Color.BLUE);
		login.add(userIdTextField);
		
		passwordField = new JPasswordField("");
		passwordField.setBounds(50,150,300,40);
		passwordField.setFont(f2);
		passwordField.setForeground(Color.WHITE);
		passwordField.setBackground(Color.BLUE);
		login.add(passwordField);
		
		//frame
		setSize(900,600);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//background
		ImageIcon background_image = new ImageIcon("image3.jpg");
		
		Image img = background_image.getImage();
		Image temp_img = img.getScaledInstance(900,600,Image.SCALE_SMOOTH);
		background_image = new ImageIcon(temp_img);
		JLabel background = new JLabel("", background_image, JLabel.CENTER);
		
		background.add(login);
		background.add(heading);
		background.setBounds(0,0,900,600);
		add(background);
		
		setVisible(true);
		
		loginButton.addActionListener(this);
		clearButton.addActionListener(this);
}

public void actionPerformed(ActionEvent e) 
{
    if (e.getSource() == loginButton) 
    {
        String USERID = userIdTextField.getText();
        String PASSWORD = new String(passwordField.getPassword());

         try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSDBA", "swain2001");
            String sql = "SELECT * FROM LOGIN where USERID=? AND PASSWORD=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, USERID);
            ps.setString(2, PASSWORD);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Successful login
                System.out.println("Login successful !");

                MainPage mainPage = new MainPage(); 
                mainPage.setVisible(true);
                dispose(); // Close the login window
                
            } else {
                // Invalid credentials
                //System.out.println("Invalid credentials");
                JOptionPane.showMessageDialog(this, "Invalid credentials! Please try again.");
                userIdTextField.setText("");
                passwordField.setText("");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception ob) {
            System.out.println(ob);
        }
    }
    else if (e.getSource() == clearButton) {
        userIdTextField.setText("");
        passwordField.setText("");
        }
    }

}

class InvManageSystem 
{
    public static void main(String[] args) 
    {
        new InventoryManagementSystem();
        
    }
}
