package br.com.ifpe.organiconecta_api.api.plano;

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

import br.com.ifpe.organiconecta_api.modelo.plano.Plano;
import br.com.ifpe.organiconecta_api.modelo.plano.PlanoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plano")
@CrossOrigin


public class PlanoController {
    @Autowired
    private PlanoService planoService;

    @PostMapping
        public ResponseEntity<Plano> save(@RequestBody @Valid PlanoRequest request) {
        Plano plano = planoService.save(request.build());
        return new ResponseEntity<Plano>(plano, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Plano> listarTodos() {
        return planoService.listarTodos();
    }

    @GetMapping("/{id}")
        public Plano obterPorID(@PathVariable Long id) {
        return planoService.obterPorID(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Plano> update(@PathVariable("id") Long id, @RequestBody PlanoRequest request) {
        planoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> delete(@PathVariable Long id) {
       planoService.delete(id);
       return ResponseEntity.ok().build();
   }
}
