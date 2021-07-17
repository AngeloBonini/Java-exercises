
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CreateServer extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtName2;
	private JTextField txtAddress;
	private JTextField txtPort;
	private JTextField servertxtPort;
	private JLabel lblIpAddress;
	private JLabel Address;
	private JLabel lblPort;
	private JLabel serverPort;
	private JLabel lblAddressDesc;
	private JLabel lblPortDesc;
	private JLabel lblPort1Desc;
	private JLabel lblserverPortDesc;
	private JLabel lblserverPortDesc2;
	

	public CreateServer() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setResizable(false);
		setTitle("Create Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtName = new JTextField();
		txtName.setBounds(67, 35, 165, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Server Port cliente1: ");
		lblName.setBounds(95, 5, 110, 40);
		contentPane.add(lblName);
		
		
		txtName2 = new JTextField();
		txtName2.setBounds(67, 100, 165, 28);
		contentPane.add(txtName2);
		txtName2.setColumns(10);

		JLabel lblName1 = new JLabel("Server Port cliente2:");
		lblName1.setBounds(95, 70, 110, 40);
		contentPane.add(lblName1);

		txtAddress = new JTextField();
		txtAddress.setBounds(67, 166, 165, 28);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);

		lblIpAddress = new JLabel("IP Address:");
		lblIpAddress.setBounds(111, 146, 77, 16);
		contentPane.add(lblIpAddress);

		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(67, 241, 165, 28);
		contentPane.add(txtPort);

		lblPort = new JLabel("Client1 Port:");
		lblPort.setBounds(113, 221, 64, 16);
		contentPane.add(lblPort);
		
		servertxtPort = new JTextField();
		servertxtPort.setColumns(10);
		servertxtPort.setBounds(67, 311, 165, 28);
		contentPane.add(servertxtPort);

		serverPort = new JLabel("Client2 Port:");
		serverPort.setBounds(113, 295, 64, 16);
		contentPane.add(serverPort);
		
		lblAddressDesc = new JLabel("(eg. \"224.0.0.3\")");
		lblAddressDesc.setBounds(94, 192, 112, 16);
		contentPane.add(lblAddressDesc);

		lblPortDesc = new JLabel("(eg. 5000)");
		lblPortDesc.setBounds(116, 268, 68, 16);
		contentPane.add(lblPortDesc);
		
		lblPort1Desc = new JLabel("(eg. 5001)");
		lblPort1Desc.setBounds(116, 338, 68, 16);
		contentPane.add(lblPort1Desc);
		
		lblserverPortDesc = new JLabel("(eg. 8889)");
		lblserverPortDesc.setBounds(116,127, 68, 16);
		contentPane.add(lblserverPortDesc);
		
		lblserverPortDesc2 = new JLabel("(eg. 8888)");
		lblserverPortDesc2.setBounds(116,65, 68, 16);
		contentPane.add(lblserverPortDesc2);
		

		JButton btnLogin = new JButton("Create");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int name = Integer.parseInt(txtName.getText());
				int name2 = Integer.parseInt(txtName2.getText());
				String address = txtAddress.getText();
				int serverport =Integer.parseInt(servertxtPort.getText());
				int port = Integer.parseInt(txtPort.getText());
				try {
					CreateServer(name, name2, address, port, serverport);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(91, 361, 117, 29);
		contentPane.add(btnLogin);
	}

	private void CreateServer(int name, int name2, String address, int port, int serverport) throws UnknownHostException {
		dispose();
		MulticastSocketServer server = new MulticastSocketServer(name, name2, address,port, serverport);
	    new Thread(server.servcliente1).start();
	    new Thread(server.servcliente2).start(); 
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateServer frame = new CreateServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
