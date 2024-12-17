package br.com.ifpe.organiconecta_api.api.lojas;
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

import br.com.ifpe.organiconecta_api.modelo.lojas.Lojas;
import br.com.ifpe.organiconecta_api.modelo.lojas.LojasService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/lojas")
@CrossOrigin
public class LojasController {
    
    @Autowired
    private LojasService lojasService;

    @PostMapping
        public ResponseEntity<Lojas> save(@RequestBody @Valid LojasRequest request) {
        Lojas lojas = lojasService.save(request.build());
        return new ResponseEntity<Lojas>(lojas, HttpStatus.CREATED);
    }
    @GetMapping
        public List<Lojas> listarTodos() {
        return lojasService.listarTodos();
    }

    @GetMapping("/{id}")
        public Lojas obterPorID(@PathVariable Long id) {
        return lojasService.obterPorID(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Lojas> update(@PathVariable("id") Long id, @RequestBody LojasRequest request) {
        lojasService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> delete(@PathVariable Long id) {
       lojasService.delete(id);
       return ResponseEntity.ok().build();
   }

}
