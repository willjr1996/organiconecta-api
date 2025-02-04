package br.com.ifpe.organiconecta_api.api.pagamento;

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

import br.com.ifpe.organiconecta_api.modelo.pagamento.Pagamento;
import br.com.ifpe.organiconecta_api.modelo.pagamento.PagamentoService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pagamento")
@CrossOrigin

@Tag(
    name = "API Pagamento",
    description = "API responsável pelos serviços de pagamentos no sistema"
)

public class PagamentoController {
    
    @Autowired
    private PagamentoService pagamentoService;

    @Operation(
        summary = "Serviço responsável por salvar um pagamento no sistema."
        
    )
    @PostMapping
        public ResponseEntity<Pagamento> save(@RequestBody @Valid PagamentoRequest request) {
        Pagamento pagamento = pagamentoService.save(request.build());
        return new ResponseEntity<Pagamento>(pagamento, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por listar os pagamentos no sistema."
        
    )
    @GetMapping
        public List<Pagamento> listarTodos() {
        return pagamentoService.listarTodos();
    }

    @Operation(
        summary = "Serviço responsável por buscar um pagamento pelo ID no sistema."
        
    )
    @GetMapping("/{id}")
        public Pagamento obterPorID(@PathVariable Long id) {
        return pagamentoService.obterPorID(id);
    }

    @Operation(
        summary = "Serviço responsável por alterar o pagamento no sistema."
        
    )
    @PutMapping("/{id}")
        public ResponseEntity<Pagamento> update(@PathVariable("id") Long id, @RequestBody PagamentoRequest request) {
        pagamentoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por remover o pagamento no sistema."
        
    )
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.ok().build();
    }
}

