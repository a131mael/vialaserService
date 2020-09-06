package org.viaLaser.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "GENERATE_pessoa", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "GENERATE_pessoa", sequenceName = "Pessoa_pk_seq", allocationSize = 1)
    private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
}
