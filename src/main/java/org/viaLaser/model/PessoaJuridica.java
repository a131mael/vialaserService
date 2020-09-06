package org.viaLaser.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class PessoaJuridica extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String razaoSocial;

    private String nomeFantasia;
    
    private String cnpj;
    
    @ElementCollection
    private List<String> telefones;
	
    private String site;
    
	public PessoaJuridica() {
		super();
	}
	
	public PessoaJuridica(PessoaJuridica pessoaJuridica){
		super();
		this.cnpj = pessoaJuridica.getCnpj();
		this.nomeFantasia = pessoaJuridica.getNomeFantasia();
		this.razaoSocial = pessoaJuridica.getRazaoSocial();
		this.site = pessoaJuridica.getSite();
		this.telefones = pessoaJuridica.getTelefones();
	}
	
	public void copy(PessoaJuridica pessoaJuridica){
		pessoaJuridica.setCnpj(this.getCnpj());
		pessoaJuridica.setNomeFantasia(this.getNomeFantasia());
		pessoaJuridica.setRazaoSocial(this.getRazaoSocial());
		pessoaJuridica.setSite(this.getSite());
		pessoaJuridica.setTelefones(this.getTelefones());
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}
