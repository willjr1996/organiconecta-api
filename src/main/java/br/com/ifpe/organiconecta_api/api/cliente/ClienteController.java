package br.com.ifpe.organiconecta_api.api.cliente;
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
// import br.com.ifpe.organiconecta_api.modelo.cliente.Credenciais;
import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
// import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteId;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
        public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest request) {
        Cliente cliente = clienteService.save(request.build());
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
        public Cliente obterPorID(@PathVariable Long id) {
        return clienteService.obterPorID(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @RequestBody ClienteRequest request) {
        clienteService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> delete(@PathVariable Long id) {
       clienteService.delete(id);
       return ResponseEntity.ok().build();
   }

//    @PostMapping("/login")
//    public ResponseEntity <String> login (@RequestBody @Valid LoginRequest loginRequest){
//      String nome = clienteService.Login(loginRequest.getEmail(), loginRequest.getSenha());
//      if(nome != null){
//         return ResponseEntity.ok("Bem Vindo " + nome + "!");
//      }
//      return ResponseEntity.status(404).body("Usuário não encontrado");
//    }

    //pegar id do usuário quando ele loga
    // @PostMapping("/login")
    // public ResponseEntity<ClienteId> getIdByCredenciais(@RequestBody Credenciais credenciais) {
    //     ClienteId clienteId = clienteService.getIdByCredenciais(credenciais);
    //     return ResponseEntity.ok(clienteId);
    // }

    // //editar e-mail e senha
    // @PutMapping("/{id}/atualizarcredenciais")
    // public Cliente atualizarEmailESenha(
    //         @PathVariable Long id,
    //         @RequestBody @Valid Credenciais credenciais) {
    //     return clienteService.atualizarEmailESenha(id, credenciais);
    // }
}