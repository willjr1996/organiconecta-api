package br.com.ifpe.organiconecta_api.api.endereco;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.organiconecta_api.modelo.endereco.EnderecoService;
import br.com.ifpe.organiconecta_api.modelo.endereco.Endereco;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/endereco")
@CrossOrigin

public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
        public ResponseEntity<Endereco> save(@RequestBody @Valid EnderecoRequest request) {
        Endereco endereco = enderecoService.save(request.build());
        return new ResponseEntity<Endereco>(endereco, HttpStatus.CREATED);
    }
    @GetMapping
        public List<Endereco> listarTodos() {
        return enderecoService.listarTodos();
    }

    @GetMapping("/{id}")
        public Endereco obterPorID(@PathVariable Long id) {
        return enderecoService.obterPorID(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Endereco> update(@PathVariable("id") Long id, @RequestBody EnderecoRequest request) {
        enderecoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
       return ResponseEntity.ok().build();
   }
   
}