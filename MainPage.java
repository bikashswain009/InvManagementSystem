import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JFrame {
    MainPage() {
        setTitle("Inventory Management System - Main Page");
        setSize(600, 400);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new GridBagLayout());
		
		//set icon
		ImageIcon icon = new ImageIcon("inventory_icon.png");
		setIconImage(icon.getImage());

        // Create GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 40, 25, 30); // Add padding

        // Adding components and icons for Product, User, Customer, Category

        // Product Button (Top Left)
        ImageIcon productIcon = new ImageIcon("product_icon.png");
        JButton productButton = new JButton("Products", productIcon);
        productButton.setPreferredSize(new Dimension(160, 50)); // Set button size
        productButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
        productButton.setIconTextGap(10); // Set padding between image and text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        c.add(productButton, gbc);

        // User Button (Top Right)
        ImageIcon userIcon = new ImageIcon("user_icon.png");
        JButton userButton = new JButton("User", userIcon);
        userButton.setPreferredSize(new Dimension(160, 50)); // Set button size
        userButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        c.add(userButton, gbc);

        // Customer Button (Bottom Left)
        ImageIcon customerIcon = new ImageIcon("customer_icon.png");
        JButton customerButton = new JButton("Customer", customerIcon);
        customerButton.setPreferredSize(new Dimension(160, 50)); // Set button size
        customerButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        c.add(customerButton, gbc);

        // Category Button (Bottom Right)
        ImageIcon categoryIcon = new ImageIcon("category_icon.png");
        JButton categoryButton = new JButton("Category", categoryIcon);
        categoryButton.setPreferredSize(new Dimension(160, 50)); // Set button size
        categoryButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        c.add(categoryButton, gbc);

        // Add logout button (Center)
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.BLUE); 
        logoutButton.setForeground(Color.WHITE); 
        logoutButton.setPreferredSize(new Dimension(90, 35));
        logoutButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15)); // Set button font and size

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(logoutButton, gbc);

        // Customer Button action listener
        customerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageCustomer customerPage = new ManageCustomer();
                customerPage.setVisible(true);
                dispose();     // Close the main page
            }
        });

        // Product Button action listener
        productButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageProduct productPage = new ManageProduct();
                productPage.setVisible(true);
                dispose();     // Close the main page
            }
        });

        // Category Button action listener
        categoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageCategory productPage = new ManageCategory();
                productPage.setVisible(true);
                dispose();     // Close the main page
            }
        });
		
		// User Button action listener
		userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageUsers userPage = new ManageUsers();
                userPage.setVisible(true);
                dispose();     // Close the main page
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Logged out successfully!");
                InventoryManagementSystem loginPage = new InventoryManagementSystem();
                loginPage.setVisible(true);
                dispose(); // Close the main page
            }
        });

        setVisible(true);
    }
}
