package br.com.ifpe.organiconecta_api.api.administrador;

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

import br.com.ifpe.organiconecta_api.modelo.acesso.Perfil;
import br.com.ifpe.organiconecta_api.modelo.administrador.Admin;
import br.com.ifpe.organiconecta_api.modelo.administrador.AdminService;
import br.com.ifpe.organiconecta_api.modelo.administrador.TipoFuncionario;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/administrador")
@CrossOrigin
@Tag(
    name = "API Administrador",
    description = "API responsável pelos serviços do administrador no sistema"
)

public class AdminController {

        @Autowired
        private AdminService adminService;
    
        @Operation(
        summary = "Serviço responsável por salvar um administrador no sistema."
    )
        @PostMapping
        public ResponseEntity<Admin> save(@RequestBody @Valid AdminRequest request) {
    
            Admin adminNovo = request.build();

            if (adminNovo.getTipo().equals(TipoFuncionario.ADMINISTRADOR)) {
                adminNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_FUNCIONARIO_ADMIN));
            } else if (adminNovo.getTipo().equals(TipoFuncionario.OPERADOR)) {
                adminNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_FUNCIONARIO_USER));
            }

            Admin admin = adminService.save(adminNovo);
            return new ResponseEntity<Admin>(admin, HttpStatus.CREATED);
        }
        @Operation(
        summary = "Serviço responsável por listar todos os administradores no sistema."
    )
        @GetMapping
        public List<Admin> listarTodos() {
    
            return adminService.listarTodos();
        }

        @Operation(
        summary = "Serviço responsável por buscar o administrador pelo ID no sistema."
    )
        @GetMapping("/{id}")
        public Admin obterPorID(@PathVariable Long id) {
    
            return adminService.obterPorID(id);
        }
    
        @Operation(
        summary = "Serviço responsável por alterar o administrador no sistema."
    )
        @PutMapping("/{id}")
        public ResponseEntity<Admin> update(@PathVariable("id") Long id, @RequestBody AdminRequest request) {
    
            adminService.update(id, request.build());
            return ResponseEntity.ok().build();
        }
    
        @Operation(
        summary = "Serviço responsável por deletar o administrador no sistema."
    )
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
    
            adminService.delete(id);
            return ResponseEntity.ok().build();
        }
    
    }
    
