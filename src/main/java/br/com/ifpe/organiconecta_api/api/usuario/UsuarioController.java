package br.com.ifpe.organiconecta_api.api.usuario;
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

import br.com.ifpe.organiconecta_api.modelo.usuario.Credenciais;
import br.com.ifpe.organiconecta_api.modelo.usuario.Usuario;
import br.com.ifpe.organiconecta_api.modelo.usuario.UsuarioId;
import br.com.ifpe.organiconecta_api.modelo.usuario.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
        public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioRequest request) {
        Usuario usuario = usuarioService.save(request.build());
        return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
        public Usuario obterPorID(@PathVariable Long id) {
        return usuarioService.obterPorID(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Usuario> update(@PathVariable("id") Long id, @RequestBody UsuarioRequest request) {
        usuarioService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> delete(@PathVariable Long id) {
       usuarioService.delete(id);
       return ResponseEntity.ok().build();
   }

//    @PostMapping("/login")
//    public ResponseEntity <String> login (@RequestBody @Valid LoginRequest loginRequest){
//      String nome = usuarioService.Login(loginRequest.getEmail(), loginRequest.getSenha());
//      if(nome != null){
//         return ResponseEntity.ok("Bem Vindo " + nome + "!");
//      }
//      return ResponseEntity.status(404).body("Usuário não encontrado");
//    }

    //pegar id do usuário quando ele loga
    @PostMapping("/login")
    public ResponseEntity<UsuarioId> getIdByCredenciais(@RequestBody Credenciais credenciais) {
        UsuarioId usuarioId = usuarioService.getIdByCredenciais(credenciais);
        return ResponseEntity.ok(usuarioId);
    }

    //editar e-mail e senha
    @PutMapping("/{id}/atualizarcredenciais")
    public Usuario atualizarEmailESenha(
            @PathVariable Long id,
            @RequestBody @Valid Credenciais credenciais) {
        return usuarioService.atualizarEmailESenha(id, credenciais);
    }
}