package br.cantinho.bixos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cantinho.bixos.model.Raca;
import br.cantinho.bixos.repository.RacaRepository;


@RestController
@RequestMapping("/racas")
public class RacaController {
    private final RacaRepository racaRepository;

    @Autowired
    public RacaController(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    // CADASTRAR RACAS NO BANCO
    @PostMapping
    public ResponseEntity<Object> criarRaca(@RequestBody Raca raca) {
        if (racaRepository.existsByNome(raca.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A raça já está cadastrada.");
        }

        Raca racaCadastrada = racaRepository.save(raca);
        return ResponseEntity.status(HttpStatus.CREATED).body(racaCadastrada);
    }
    
    // CONSULTAR RACAS POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarRaca(@PathVariable Long id) {
        Optional<Raca> raca = racaRepository.findById(id);
        if (raca.isPresent()) {
            return ResponseEntity.ok(raca.get());
        } else {
            String mensagem = "Não há raças cadastradas com o ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }
    }
    
    // LISTAR TODAS AS RACAS CADASTRADAS
    @GetMapping("/")
    public ResponseEntity<List<Raca>> listarRacas() {
        List<Raca> racas = racaRepository.findAll();
        return ResponseEntity.ok(racas);
    }

    // EXCLUIR RACAS POR ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarRaca(@PathVariable Long id) {
        Optional<Raca> racaOptional = racaRepository.findById(id);
        
        if (racaOptional.isPresent()) {
            Raca raca = racaOptional.get();
            String nomeRaca = raca.getNome();
            
            racaRepository.deleteById(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(id));
            response.put("nome", nomeRaca);
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    // ATUALIZAR RACAS POR ID
    @PutMapping("/{id}")
    public ResponseEntity<Raca> atualizarRaca(@PathVariable Long id, @RequestBody Raca novaRaca) {
        Optional<Raca> racaOptional = racaRepository.findById(id);

        if (racaOptional.isPresent()) {
            Raca raca = racaOptional.get();
            raca.setNome(novaRaca.getNome());

            Raca racaAtualizada = racaRepository.save(raca);
            return ResponseEntity.ok(racaAtualizada);
        }

        return ResponseEntity.notFound().build();
    }


}
