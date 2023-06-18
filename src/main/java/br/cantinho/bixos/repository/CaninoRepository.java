package br.cantinho.bixos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.cantinho.bixos.model.Canino;
import br.cantinho.bixos.model.Raca;

@Repository
public interface CaninoRepository extends JpaRepository<Canino, Long> {
	@Query("SELECT c FROM Canino c WHERE c.raca = :raca")
    List<Canino> findByRaca(Raca raca);
}

