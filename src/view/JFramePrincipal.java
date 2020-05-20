package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import java.awt.Toolkit;

public class JFramePrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8291361218541467224L;
	private JPanel contentPane;
	public JDesktopPane desktopPane;
	public JButton btnListUser;
	public JButton btnAddUser;
	public JButton btnConfiguracion;
	public JButton btnFormLogin;
	public JButton btnSalir;
	public JButton btnReportClients;
	public JButton btnCategories;
	public JButton btnProducts;
	public JButton btnReportProducts;
	public JButton btnInvoice;

	/**
	 * Create the frame.
	 */
	public JFramePrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JFramePrincipal.class.getResource("/images/logo_compacto.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1047, 752);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnFormLogin = new JButton("Login");
		btnFormLogin.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/login.png")));
		toolBar.add(btnFormLogin);

		btnAddUser = new JButton("Add Client");
		btnAddUser.setEnabled(false);
		btnAddUser.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/addUser.png")));
		toolBar.add(btnAddUser);
		
		btnListUser = new JButton("List Clients");
		btnListUser.setEnabled(false);
		btnListUser.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/usuarios.png")));
		toolBar.add(btnListUser);
		
		btnProducts = new JButton("Products");
		btnProducts.setEnabled(false);
		btnProducts.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/objects.png")));
		toolBar.add(btnProducts);
		
		btnCategories = new JButton("Categories");
		btnCategories.setEnabled(false);
		btnCategories.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/categories.png")));
		toolBar.add(btnCategories);
		
		btnInvoice = new JButton("Invoice");
		btnInvoice.setEnabled(false);
		btnInvoice.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/invoice.png")));
		toolBar.add(btnInvoice);
		
		btnReportClients = new JButton("Clients");
		btnReportClients.setEnabled(false);
		btnReportClients.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/Printer.png")));
		toolBar.add(btnReportClients);
		
		btnReportProducts = new JButton("Products");
		btnReportProducts.setEnabled(false);
		btnReportProducts.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/Printer.png")));
		btnReportProducts.setSelectedIcon(null);
		toolBar.add(btnReportProducts);
		
		btnConfiguracion = new JButton("Configuracion");
		btnConfiguracion.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/configuracion.png")));
		toolBar.add(btnConfiguracion);
		
		btnSalir = new JButton("Salir");
		btnSalir.setEnabled(false);
		btnSalir.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/images/exit.png")));
		toolBar.add(btnSalir);

		desktopPane = new JDesktopPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);
	}
}
