package model;

public class LineaFactura {

	private int linea;
	private int articuloId;
	private float unidades;
	private float pvp;
	private float importeLinea;

	public LineaFactura(int linea, int articuloId, float unidades, float pvp, float importeLinea) {
		super();
		this.linea = linea;
		this.articuloId = articuloId;
		this.unidades = unidades;
		this.pvp = pvp;
		this.importeLinea = importeLinea;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public int getArticuloId() {
		return articuloId;
	}

	public void setArticuloId(int articuloId) {
		this.articuloId = articuloId;
	}

	public float getUnidades() {
		return unidades;
	}

	public void setUnidades(float unidades) {
		this.unidades = unidades;
	}

	public float getPvp() {
		return pvp;
	}

	public void setPvp(float pvp) {
		this.pvp = pvp;
	}

	public float getImporteLinea() {
		return importeLinea;
	}

	public void setImporteLinea(float importeLinea) {
		this.importeLinea = importeLinea;
	}
	

}
