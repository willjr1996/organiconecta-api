package br.com.ifpe.organiconecta_api.api.lojas;

import br.com.ifpe.organiconecta_api.modelo.lojas.Lojas;
import br.com.ifpe.organiconecta_api.modelo.lojas.LojasService;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loja")
@CrossOrigin
public class LojasController {

    @Autowired
    private LojasService lojasService;

    @Autowired
   private ClienteService clienteService;


    @PostMapping
    public ResponseEntity<Lojas> save(
            @RequestBody @Valid LojasRequest request)
            {

                Lojas novaLoja = request.build();
                novaLoja.setCliente(clienteService.obterPorID(request.getIdCliente()));
                Lojas loja = lojasService.save(novaLoja);


        
        return new ResponseEntity<Lojas>(loja, HttpStatus.CREATED);
    }

    // Listar todas as lojas
    @GetMapping
    public List<Lojas> listarTodos() {
        return lojasService.listarTodos();
    }

    // Obter loja por ID
    @GetMapping("/{id}")
    public ResponseEntity<Lojas> obterPorID(@PathVariable Long id) {
        Lojas loja = lojasService.obterPorID(id);
        return ResponseEntity.ok(loja);
    }

    // Atualizar loja com associação ao cliente
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid LojasRequest request) {

                Lojas loja = request.build();
                loja.setCliente(clienteService.obterPorID(request.getIdCliente()));
       lojasService.update(id, loja);

        return ResponseEntity.ok().build();
    }

    // Deletar loja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lojasService.delete(id);
        return ResponseEntity.ok().build();
    }
}
