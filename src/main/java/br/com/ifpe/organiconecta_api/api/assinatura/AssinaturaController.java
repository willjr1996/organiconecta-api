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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/assinatura")
@CrossOrigin
@Tag(
    name = "API Assinatura",
    description = "API responsável pelos serviços de assinatuda no sistema"
)

public class AssinaturaController {
    
    @Autowired
    private AssinaturaService assinaturaService;

    @Operation(
        summary = "Serviço responsável por salvar uma assinatura no sistema."
    )
    @PostMapping
        public ResponseEntity<Assinatura> save(@RequestBody @Valid AssinaturaRequest request) {
        Assinatura assinatura = assinaturaService.save(request.build());
        return new ResponseEntity<Assinatura>(assinatura, HttpStatus.CREATED);
    }


    @Operation(
        summary = "Serviço responsável por listar todas as assinaturas no sistema."
    )
    @GetMapping
        public List<Assinatura> listarTodos() {
        return assinaturaService.listarTodos();
    }

    @Operation(
        summary = "Serviço responsável por buscar uma assinatura pelo ID no sistema."
    )
    @GetMapping("/{id}")
        public Assinatura obterPorID(@PathVariable Long id) {
        return assinaturaService.obterPorID(id);
    }

    @Operation(
        summary = "Serviço responsável por alterar uma assinatura no sistema."
    )
    @PutMapping("/{id}")
        public ResponseEntity<Assinatura> update(@PathVariable("id") Long id, @RequestBody AssinaturaRequest request) {
        assinaturaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por deletar uma assinatura no sistema."
    )
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        assinaturaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por ativar uma assinatura no sistema."
    )
    @PutMapping("/{id}/ativarplano")
    public ResponseEntity<Void> ativarPlanoPago(@PathVariable("id") Long id) {
    assinaturaService.atualizarPlanoParaPago(id);
    return ResponseEntity.ok().build();
}
    @Operation(
        summary = "Serviço responsável por desativar uma assinatura no sistema."
    )
    @PutMapping("/{id}/desativarplano")
    public ResponseEntity<Void> ativarPlanoGratis(@PathVariable("id") Long id) {
    assinaturaService.atualizarPlanoParaGratis(id);
    return ResponseEntity.ok().build();
}
}
