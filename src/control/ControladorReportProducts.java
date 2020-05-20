/**
 * 
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Categoria;
import model.Modelo;
import model.Persona;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

import view.JIFrameReportClients;
import view.JIFrameReportProducts;

/**
 * Creado el 3 abr. 2019
 * 
 * @author <a href="mailto:joaalsai@ieslavereda.es">Joaquin Vicente Alonso
 *         Saiz</a>
 *
 */
public class ControladorReportProducts implements ActionListener {

	private JIFrameReportProducts view;
	private Modelo model;

	JasperReport report;
	JasperPrint reportFilled;
	JRViewer jrViewer;

	private String reportUrl;

	public ControladorReportProducts(JIFrameReportProducts view, Modelo model) {

		super();
		this.view = view;
		this.model = model;
		iniciar();
	}

	private void iniciar() {

		// Se añade las acciones a los controles del formulario padre

		view.btnCustom.setActionCommand("Custom search");

		// Se pone a escuchar las acciones del usuario

		view.btnCustom.addActionListener(this);

		// Rellenamos los combo
		rellenarComboOrderBy();
		rellenarComboCategorias();

	}

	public void go() {
		try {
			view.setMaximum(true);
			view.setVisible(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Captura en String el comando accionado por el usuario
		String comando = e.getActionCommand();

		/* Acciones del formulario padre */
		if (comando.equals("Custom search")) {
			customSearch();
		}

	}

	private void rellenarComboOrderBy() {

		List<String> campos = model.obtenerAtributosTabla("Articulo");

		for (String campo : campos)
			view.cbOrderBy.addItem(campo);

	}

	private void rellenarComboCategorias() {

		Map<Integer, Categoria> campos = model.obtenerCategorias();

		for (Integer id : campos.keySet())
			view.cbId.addItem(campos.get(id));

	}

	private void customSearch() {
		reportUrl = "/reports/reportArticulos.jasper";
		InputStream reportFile = null;
		reportFile = getClass().getResourceAsStream(reportUrl);

		Map<String, Object> parametres = new HashMap<String, Object>();

		String id = view.cbId.getSelectedItem().toString();
		String orderBy = view.cbOrderBy.getSelectedItem().toString();
		String order = "`bbddJava`.`Articulo`."+view.cbOrder.getSelectedItem().toString();

		String query = "";

		// Comprobamos id
		if (!id.equals("All"))
			query += " AND `bbddJava`.`Categoria`.descripcion=\"" + id + "\"";

		// Comprobamos precios
		if (!view.txtFieldPVPFrom.getText().isEmpty()) {
			query += " AND `bbddJava`.`Articulo`.pvp >= " + view.txtFieldPVPFrom.getText();
		}
		if (!view.txtFieldPVPTo.getText().isEmpty()) {
			query += " AND `bbddJava`.`Articulo`.pvp <= " + view.txtFieldPVPTo.getText();
		}

		// Comprobamos el orden
		query += " ORDER BY " + orderBy + " " + ((view.cbOrder.getSelectedItem().toString().equals("ascendente")) ? "ASC" : "DESC");

		// Establecemos los parametros de la consulta
		parametres.put("wherePara", query);
		//System.out.println(query);

		try {
			JasperPrint print = JasperFillManager.fillReport(reportFile, parametres, model.getConnection());
			jrViewer = new JRViewer(print);
			jrViewer.setVisible(true);

			view.getContentPane().add(jrViewer);

		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}
