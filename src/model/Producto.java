package model;

public class Producto {
	
	private int id;
	private String descArticulo;
	private int existencias;
	private float pvp;
	private int categoriaId;
	
	public Producto(int id, String descArticulo, int existencias, float pvp, int categoriaId) {
		super();
		this.id = id;
		this.descArticulo = descArticulo;
		this.existencias = existencias;
		this.pvp = pvp;
		this.categoriaId = categoriaId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescArticulo() {
		return descArticulo;
	}

	public void setDescArticulo(String descArticulo) {
		this.descArticulo = descArticulo;
	}

	public int getExistencias() {
		return existencias;
	}

	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}

	public float getPvp() {
		return pvp;
	}

	public void setPvp(float pvp) {
		this.pvp = pvp;
	}

	public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}
	

}
