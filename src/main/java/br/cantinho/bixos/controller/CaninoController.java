package br.cantinho.bixos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cantinho.bixos.model.Canino;
import br.cantinho.bixos.model.Raca;
import br.cantinho.bixos.repository.CaninoRepository;
import br.cantinho.bixos.repository.RacaRepository;

@RestController
@RequestMapping("/caninos")
public class CaninoController {
    private CaninoRepository caninoRepository;
    private RacaRepository racaRepository;
    
    public CaninoController(CaninoRepository caninoRepository, RacaRepository racaRepository) {
        this.caninoRepository = caninoRepository;
        this.racaRepository = racaRepository;
    }
    // CADASTRAR CANINO
    @PostMapping("/racas/{racaId}/caninos")
    public ResponseEntity<Canino> cadastrarCanino(@PathVariable Long racaId, @RequestBody Canino canino) {
        Raca raca = racaRepository.findById(racaId).orElse(null);
        
        if (raca == null) {        	
            return ResponseEntity.notFound().build();
        }
        
        canino.setRaca(raca);
        caninoRepository.save(canino);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(canino);
    }
    // CONSULTAR CANICO POR ID DA RAÇA
    @GetMapping("/racas/{racaId}/caninos")
    public ResponseEntity<List<Canino>> listarCaninosPorRaca(@PathVariable("racaId") Long racaId) {
        Optional<Raca> racaOptional = racaRepository.findById(racaId);
        
        if (racaOptional.isEmpty()) {        	
            return ResponseEntity.notFound().build();
        }
        
        Raca raca = racaOptional.get();
        List<Canino> caninos = caninoRepository.findByRaca(raca);
        
        return ResponseEntity.ok(caninos);
    }
    //LICAS TODOS CANINOS CADASTRADOS
    @GetMapping
    public ResponseEntity<List<Canino>> listarTodosCaninos() {
        List<Canino> caninos = caninoRepository.findAll();
        
        return ResponseEntity.ok(caninos);
    }

}
