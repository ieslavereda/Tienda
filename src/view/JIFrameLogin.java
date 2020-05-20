package view;

import javax.swing.JInternalFrame;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class JIFrameLogin extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5127162690066711997L;
	public JTextField txtFiledUsuario;
	public JPasswordField passwordField;
	public JButton btnLogin;

	/**
	 * Create the frame.
	 */
	public JIFrameLogin() {
		setClosable(true);
		setBounds(100, 100, 387, 251);

		JPanel panelSuperior = new JPanel();

		JPanel panelInferior = new JPanel();

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelInferior, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 155, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelSuperior, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 168, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelInferior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(12))
		);
		panelSuperior.setLayout(new MigLayout("", "[grow]", "[][][][3.00][][]"));
		
		JLabel lblUsuario = new JLabel("Usuario");
		panelSuperior.add(lblUsuario, "cell 0 1");
		
		txtFiledUsuario = new JTextField();
		panelSuperior.add(txtFiledUsuario, "cell 0 2,growx");
		txtFiledUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		panelSuperior.add(lblPassword, "cell 0 4");
		
		passwordField = new JPasswordField();
		panelSuperior.add(passwordField, "cell 0 5,growx");
		panel.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel();

		panel.add(label, BorderLayout.CENTER);
		
		panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnLogin = new JButton("Login");
		panelInferior.add(btnLogin);
		getContentPane().setLayout(groupLayout);
		
		
		
		
		BufferedImage img;
		ImageIcon imageIcon = null;
		try {
			img = ImageIO.read(JIFrameLogin.class.getResource("/images/loginPanel.png"));
			Image dimg = img.getScaledInstance(150, 170, Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(dimg);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		label.setIcon(imageIcon);

	}
}
