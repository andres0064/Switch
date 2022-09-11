package com.example.demo;

public class Block {
	private int id;
	private String nomFile;
	private long volumen;
	private int asignado;
	private String pac;
	
	public Block() {
		
	}
	public Block(int id, String nomFile, long volumen, int asignado, String pac) {
		super();
		this.id = id;
		this.nomFile = nomFile;
		this.volumen = volumen;
		this.asignado = asignado;
		this.pac = pac;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomFile() {
		return nomFile;
	}
	public void setNomFile(String nomFile) {
		this.nomFile = nomFile;
	}
	public long getVolumen() {
		return volumen;
	}
	public void setVolumen(long volumen) {
		this.volumen = volumen;
	}
	public int getAsignado() {
		return asignado;
	}
	public void setAsignado(int asignado) {
		this.asignado = asignado;
	}
	public String getPac() {
		return pac;
	}
	public void setPac(String pac) {
		this.pac = pac;
	}

}
