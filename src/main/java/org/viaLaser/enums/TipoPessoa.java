package org.viaLaser.enums;

public enum TipoPessoa {

	PESSOA_FISICA("Pessoa Fisica"),
	
	PESSOA_JURIDICA("Pessoa Jurídica");
	
	private String tipo;
	
	TipoPessoa(String tipo){
		this.tipo = tipo;
	}

	public String getName() {
		
		return tipo;
	}

}
