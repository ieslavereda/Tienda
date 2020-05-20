package view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import com.github.lgooddatepicker.components.DatePicker;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import java.awt.Component;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;

public class JIFrameFacturas extends JInternalFrame {
	public JTextField txtFieldNombre;
	public JTextField txtFieldApellidos;
	public JTextField txtFieldDNI;
	public JTextField txtFieldNumeroFactura;
	public JTextField txtFieldFechaFactura;
	public JTextField txtFieldSuma;
	public JTextField txtFieldImpuestos;
	public JTextField txtFieldTotal;
	public JTable tableDetalles;
	public JButton btnPreviousInvoice;
	public JButton btnNextInvoice;
	public JButton btnNewInvoce;
	public JButton btnRemoveInvoice;
	public JButton btnClient;
	public JButton btnPrint;
	public JButton btnPdf;
	public JButton btnAddDetail;
	public JButton btnRemoveDetail;
	public JButton btnCerrar;

	/**
	 * Create the frame.
	 */
	public JIFrameFacturas() {
		setTitle("Facturas");
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 810, 739);
		
		JPanel panelSuperior = new JPanel();
		
		JPanel panelBotonesInferior = new JPanel();
		panelBotonesInferior.setBorder(null);
		panelBotonesInferior.setForeground(new Color(128, 128, 128));
		
		JPanel panelCentral = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelCentral, GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(16)
							.addComponent(panelBotonesInferior, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelSuperior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelCentral, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBotonesInferior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JPanel panelDetalles = new JPanel();
		panelDetalles.setBorder(new TitledBorder(null, "Detalle", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(59, 59, 59)));
		
		JPanel panelBotonesDetalle = new JPanel();
		panelBotonesDetalle.setForeground(new Color(192, 192, 192));
		panelBotonesDetalle.setBorder(null);
		panelBotonesDetalle.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelBotonesDetalle.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setForeground(new Color(192, 192, 192));
		toolBar.setBorder(null);
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		panelBotonesDetalle.add(toolBar, BorderLayout.CENTER);
		
		btnNewInvoce = new JButton("");
		btnNewInvoce.setToolTipText("Añadir factura");
		btnNewInvoce.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/invoiceAdd.png")));
		toolBar.add(btnNewInvoce);
		
		btnRemoveInvoice = new JButton("");
		btnRemoveInvoice.setToolTipText("Eliminar factura");
		btnRemoveInvoice.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/invoiceRemove.png")));
		toolBar.add(btnRemoveInvoice);
		
		JSeparator separator = new JSeparator();
		toolBar.add(separator);
		
		btnClient = new JButton("");
		btnClient.setToolTipText("Cambiar cliente de la factura");
		btnClient.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/usuarios.png")));
		toolBar.add(btnClient);
		
		btnAddDetail = new JButton("");
		btnAddDetail.setToolTipText("Añadir detalle");
		btnAddDetail.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/Add.png")));
		toolBar.add(btnAddDetail);
		
		btnRemoveDetail = new JButton("");
		btnRemoveDetail.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/Remove.png")));
		btnRemoveDetail.setToolTipText("Eliminar detalle");
		toolBar.add(btnRemoveDetail);
		
		JSeparator separator_1 = new JSeparator();
		toolBar.add(separator_1);
		
		btnPrint = new JButton("");
		btnPrint.setToolTipText("Abrir visor de report");
		btnPrint.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/Printer.png")));
		toolBar.add(btnPrint);
		
		btnPdf = new JButton("");
		btnPdf.setToolTipText("Exportar a pdf");
		btnPdf.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/pdf.png")));
		toolBar.add(btnPdf);
		GroupLayout gl_panelCentral = new GroupLayout(panelCentral);
		gl_panelCentral.setHorizontalGroup(
			gl_panelCentral.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelCentral.createSequentialGroup()
					.addComponent(panelDetalles, GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBotonesDetalle, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelCentral.setVerticalGroup(
			gl_panelCentral.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCentral.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBotonesDetalle, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
				.addGroup(gl_panelCentral.createSequentialGroup()
					.addGap(5)
					.addComponent(panelDetalles, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
		);
		
		JPanel panelTabla = new JPanel();
		
		JPanel panelTotal = new JPanel();
		panelTotal.setBorder(new TitledBorder(null, "Resumen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		GroupLayout gl_panelDetalles = new GroupLayout(panelDetalles);
		gl_panelDetalles.setHorizontalGroup(
			gl_panelDetalles.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDetalles.createSequentialGroup()
					.addGroup(gl_panelDetalles.createParallelGroup(Alignment.LEADING)
						.addComponent(panelTabla, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
						.addGroup(gl_panelDetalles.createSequentialGroup()
							.addGap(402)
							.addComponent(panelTotal, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelDetalles.setVerticalGroup(
			gl_panelDetalles.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelDetalles.createSequentialGroup()
					.addComponent(panelTabla, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
					.addGap(4)
					.addComponent(panelTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panelTabla.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelTabla.add(scrollPane, BorderLayout.CENTER);
		
		tableDetalles = new JTable();
		tableDetalles.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tableDetalles.setFillsViewportHeight(true);
		tableDetalles.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"linea", "Descripcion", "Cant.", "PVP", "Total"
			}
		));
		scrollPane.setViewportView(tableDetalles);
		panelTotal.setLayout(new MigLayout("", "[][]", "[][][]"));
		
		JLabel lblTotal = new JLabel("Suma");
		lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
		panelTotal.add(lblTotal, "cell 0 0,alignx left");
		
		txtFieldSuma = new JTextField();
		txtFieldSuma.setEnabled(false);
		txtFieldSuma.setEditable(false);
		panelTotal.add(txtFieldSuma, "cell 1 0,growx");
		txtFieldSuma.setColumns(10);
		
		JLabel lblImpuestos = new JLabel("Impuestos");
		lblImpuestos.setHorizontalAlignment(SwingConstants.LEFT);
		panelTotal.add(lblImpuestos, "cell 0 1,alignx left");
		
		txtFieldImpuestos = new JTextField();
		txtFieldImpuestos.setEnabled(false);
		txtFieldImpuestos.setEditable(false);
		panelTotal.add(txtFieldImpuestos, "cell 1 1,growx");
		txtFieldImpuestos.setColumns(10);
		
		JLabel lblTotalImputestos = new JLabel("Total + Imputestos");
		lblTotalImputestos.setHorizontalAlignment(SwingConstants.LEFT);
		panelTotal.add(lblTotalImputestos, "cell 0 2,alignx trailing");
		
		txtFieldTotal = new JTextField();
		txtFieldTotal.setEnabled(false);
		txtFieldTotal.setEditable(false);
		panelTotal.add(txtFieldTotal, "cell 1 2,growx");
		txtFieldTotal.setColumns(10);
		panelDetalles.setLayout(gl_panelDetalles);
		panelCentral.setLayout(gl_panelCentral);
		panelBotonesInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setToolTipText("Cerrar ventana");
		panelBotonesInferior.add(btnCerrar);
		panelSuperior.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelCliente = new JPanel();
		panelSuperior.add(panelCliente);
		panelCliente.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panelCliente.setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		JLabel lblNombre = new JLabel("Nombre");
		panelCliente.add(lblNombre, "cell 0 0,alignx trailing");
		
		txtFieldNombre = new JTextField();
		txtFieldNombre.setEnabled(false);
		txtFieldNombre.setEditable(false);
		panelCliente.add(txtFieldNombre, "cell 1 0,growx");
		txtFieldNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		panelCliente.add(lblApellidos, "cell 0 1,alignx trailing");
		
		txtFieldApellidos = new JTextField();
		txtFieldApellidos.setEnabled(false);
		txtFieldApellidos.setEditable(false);
		panelCliente.add(txtFieldApellidos, "cell 1 1,growx");
		txtFieldApellidos.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI");
		panelCliente.add(lblDni, "cell 0 2,alignx trailing");
		
		txtFieldDNI = new JTextField();
		txtFieldDNI.setEnabled(false);
		txtFieldDNI.setEditable(false);
		panelCliente.add(txtFieldDNI, "cell 1 2,growx");
		txtFieldDNI.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Factura", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSuperior.add(panel);
		panel.setLayout(new MigLayout("", "[][grow]", "[][][grow]"));
		
		JLabel lblFacturaN = new JLabel("Factura Nº");
		panel.add(lblFacturaN, "cell 0 0,alignx trailing");
		
		txtFieldNumeroFactura = new JTextField();
		txtFieldNumeroFactura.setEnabled(false);
		txtFieldNumeroFactura.setEditable(false);
		panel.add(txtFieldNumeroFactura, "cell 1 0,growx");
		txtFieldNumeroFactura.setColumns(10);
		
		JLabel lblFecha = new JLabel("Fecha");
		panel.add(lblFecha, "cell 0 1,alignx trailing");
		
		txtFieldFechaFactura = new JTextField();
		txtFieldFechaFactura.setEnabled(false);
		txtFieldFechaFactura.setEditable(false);
		panel.add(txtFieldFechaFactura, "cell 1 1,growx");
		txtFieldFechaFactura.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "cell 1 2,grow");
		
		btnPreviousInvoice = new JButton("");
		btnPreviousInvoice.setToolTipText("Factura anterior");
		btnPreviousInvoice.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/left.png")));
		panel_1.add(btnPreviousInvoice);
		
		btnNextInvoice = new JButton("");
		btnNextInvoice.setToolTipText("Siguiente factura");
		btnNextInvoice.setIcon(new ImageIcon(JIFrameFacturas.class.getResource("/images/right.png")));
		panel_1.add(btnNextInvoice);
		getContentPane().setLayout(groupLayout);
		
		// Establecemos dimensiones y alineacion de la tabla detalles
		
		tableDetalles.getColumnModel().getColumn(0).setMaxWidth(50);
		tableDetalles.getColumnModel().getColumn(0).setMinWidth(50);
		
		tableDetalles.getColumnModel().getColumn(2).setMaxWidth(50);
		tableDetalles.getColumnModel().getColumn(2).setMinWidth(50);
		
		tableDetalles.getColumnModel().getColumn(3).setMaxWidth(75);
		tableDetalles.getColumnModel().getColumn(3).setMinWidth(75);
		
		tableDetalles.getColumnModel().getColumn(4).setMaxWidth(75);
		tableDetalles.getColumnModel().getColumn(4).setMinWidth(75);
		
		DefaultTableCellRenderer tcrRight = new DefaultTableCellRenderer();
		tcrRight.setHorizontalAlignment(SwingConstants.RIGHT);
		tableDetalles.getColumnModel().getColumn(0).setCellRenderer(tcrRight);
		tableDetalles.getColumnModel().getColumn(2).setCellRenderer(tcrRight);
		tableDetalles.getColumnModel().getColumn(3).setCellRenderer(tcrRight);
		tableDetalles.getColumnModel().getColumn(4).setCellRenderer(tcrRight);

	}
}
