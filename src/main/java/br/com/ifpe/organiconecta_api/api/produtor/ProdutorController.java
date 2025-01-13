package br.com.ifpe.organiconecta_api.api.produtor;

import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import br.com.ifpe.organiconecta_api.modelo.lojas.LojasService;
import br.com.ifpe.organiconecta_api.modelo.produtor.Produtor;
import br.com.ifpe.organiconecta_api.modelo.produtor.ProdutorService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtor")
public class ProdutorController {

    @Autowired
    private ProdutorService produtorService;

    @Autowired
   private LojasService lojaService;

   @Autowired
   private ClienteService clienteService;


    // Criar produtor com associação à loja
    @PostMapping
    public ResponseEntity<Produtor> save(
            @RequestBody @Valid ProdutorRequest request) {

        Produtor novoProdutor = request.build();
        novoProdutor.setLoja(lojaService.obterPorID(request.getIdLoja()));
        novoProdutor.setCliente(clienteService.obterPorID(request.getClienteId()));
        Produtor produtor = produtorService.save(novoProdutor);

        return new ResponseEntity<>(produtor, HttpStatus.CREATED);
    }

    // Listar todos os produtores
    @GetMapping
    public List<Produtor> listarTodos() {
        return produtorService.listarTodos();
    }

    // Obter produtor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produtor> obterPorID(@PathVariable Long id) {
        Produtor produtor = produtorService.obterPorID(id);
        return ResponseEntity.ok(produtor);
    }

    // Atualizar produtor com associação à loja
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody @Valid ProdutorRequest request) {

                Produtor produtor = request.build();
                produtor.setCliente (clienteService.obterPorID(request.getClienteId()));
                produtor.setLoja (lojaService.obterPorID(request.getIdLoja()));
                produtorService.update(id, produtor);
         
        return ResponseEntity.ok().build();
    }

    

    // Deletar produtor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
