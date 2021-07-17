
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginCliente extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtPort;
	private JTextField servertxtPort;
	private JLabel lblIpAddress;
	private JLabel lblPort;
	private JLabel serverPort;
	private JLabel lblAddressDesc;
	private JLabel lblPortDesc;
	private JLabel lblserverPortDesc;
	private String player1 = "player 1";
    private String player2 = "player 2";
    JRadioButton p1b = new JRadioButton(player1);
    JRadioButton p2b = new JRadioButton(player2);
    
    
	public LoginCliente() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	
        
		setResizable(false);
		setTitle("Login Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		
		p1b.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(p1b);
        group.add(p2b);
        
		p1b.setBounds(70, 310, 90, 28);
		contentPane.add(p1b);
		p2b.setBounds(160, 310, 90, 28);
		contentPane.add(p2b);
		
		
		
		txtName = new JTextField();
		txtName.setBounds(67, 50, 165, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(127, 34, 45, 16);
		contentPane.add(lblName);

		txtAddress = new JTextField();
		txtAddress.setBounds(67, 116, 165, 28);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);

		lblIpAddress = new JLabel("IP Address:");
		lblIpAddress.setBounds(111, 96, 77, 16);
		contentPane.add(lblIpAddress);

		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(67, 191, 165, 28);
		contentPane.add(txtPort);

		lblPort = new JLabel("Client Port:");
		lblPort.setBounds(113, 171, 64, 16);
		contentPane.add(lblPort);
		
		servertxtPort = new JTextField();
		servertxtPort.setColumns(10);
		servertxtPort.setBounds(67, 261, 165, 28);
		contentPane.add(servertxtPort);

		serverPort = new JLabel("Server Port:");
		serverPort.setBounds(113, 245, 64, 16);
		contentPane.add(serverPort);
		
		lblAddressDesc = new JLabel("(eg. \"224.0.0.3\")");
		lblAddressDesc.setBounds(94, 142, 112, 16);
		contentPane.add(lblAddressDesc);

		lblPortDesc = new JLabel("(eg. 5000)");
		lblPortDesc.setBounds(116, 218, 68, 16);
		contentPane.add(lblPortDesc);
		
		lblserverPortDesc = new JLabel("(eg. 8888)");
		lblserverPortDesc.setBounds(116, 288, 68, 16);
		contentPane.add(lblserverPortDesc);
		
		

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
				int serverport =Integer.parseInt(servertxtPort.getText());
				int port = Integer.parseInt(txtPort.getText());
				try {
					LoginCliente(name, address, port, serverport);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(91, 360, 117, 29);
		contentPane.add(btnLogin);
	}

	private void LoginCliente(String name, String address, int port, int serverport) throws UnknownHostException {
		dispose();
		if(p1b.isSelected())
		(new Thread(new MulticastSocketClient(name, address,port, serverport,0))).start();
		else
		(new Thread(new MulticastSocketClient(name, address,port, serverport,1))).start();	
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginCliente frame = new LoginCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
