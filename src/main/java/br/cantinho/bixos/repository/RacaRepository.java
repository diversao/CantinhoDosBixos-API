package br.cantinho.bixos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cantinho.bixos.model.Raca;

public interface RacaRepository extends JpaRepository<Raca, Long> {
	boolean existsByNome(String nome);
}
