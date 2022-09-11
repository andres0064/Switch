package com.example.demo;

public class MetaDataFile {
	
	private String nombreArchivo;
	private String rutaArchivo;
	private long numReg;
	private String producto;
	
	
	public MetaDataFile() {
		super();
	}
	public MetaDataFile(String nombreArchivo, String rutaArchivo, long numReg, String producto) {
		super();
		this.nombreArchivo = nombreArchivo;
		this.rutaArchivo = rutaArchivo;
		this.numReg = numReg;
		this.producto = producto;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	public long getNumReg() {
		return numReg;
	}
	public void setNumReg(long numReg) {
		this.numReg = numReg;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}

}
