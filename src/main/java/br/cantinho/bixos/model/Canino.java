package br.cantinho.bixos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Canino {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String nome;
    @ManyToOne
    private Raca raca;
    
    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    
    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }   
    
    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }
}

