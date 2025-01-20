package br.com.ifpe.organiconecta_api.api.assinatura;

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

import br.com.ifpe.organiconecta_api.modelo.assinatura.Assinatura;
import br.com.ifpe.organiconecta_api.modelo.assinatura.AssinaturaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assinatura")
@CrossOrigin
public class AssinaturaController {
   
    @Autowired
    private AssinaturaService assinaturaService;

    @PostMapping
        public ResponseEntity<Assinatura> save(@RequestBody @Valid AssinaturaRequest request) {
        Assinatura assinatura = assinaturaService.save(request.build());
        return new ResponseEntity<Assinatura>(assinatura, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Assinatura> listarTodos() {
        return assinaturaService.listarTodos();
    }

    @GetMapping("/{id}")
        public Assinatura obterPorID(@PathVariable Long id) {
        return assinaturaService.obterPorID(id);
    }

    
    @PutMapping("/{id}")
        public ResponseEntity<Assinatura> update(@PathVariable("id") Long id, @RequestBody AssinaturaRequest request) {
        assinaturaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> delete(@PathVariable Long id) {
       assinaturaService.delete(id);
       return ResponseEntity.ok().build();
       
   }

   @PutMapping("/{id}/ativarplano")
    public ResponseEntity<Void> ativarPlanoPago(@PathVariable("id") Long id) {
    assinaturaService.atualizarPlanoParaPago(id);
    return ResponseEntity.ok().build();
}
}
