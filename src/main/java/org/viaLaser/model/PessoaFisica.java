package org.viaLaser.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class PessoaFisica extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Size(min = 1, max = 60)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	@NotNull
	private String nome;
	
	private String cpf;
	
	private Date dataNascimento;
	
	private String telefone;
	
	private String email;
	
	public PessoaFisica() {
		super();
	}
	
	public PessoaFisica(PessoaFisica pessoaFisica){
		super();
		this.cpf = pessoaFisica.getCpf();
		this.dataNascimento = pessoaFisica.getDataNascimento();
		this.email = pessoaFisica.getEmail();
		this.nome = pessoaFisica.getNome();
		this.telefone = pessoaFisica.getTelefone();
	}

	public void copy(PessoaFisica pessoaFisica){
		pessoaFisica.setCpf(this.cpf);
		pessoaFisica.setDataNascimento(this.dataNascimento);
		pessoaFisica.setEmail(this.email);
		pessoaFisica.setNome(this.nome);
		pessoaFisica.setTelefone(this.telefone);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
