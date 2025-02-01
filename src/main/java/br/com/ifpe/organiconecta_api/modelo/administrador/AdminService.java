package br.com.ifpe.organiconecta_api.modelo.administrador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.organiconecta_api.modelo.acesso.Perfil;
import br.com.ifpe.organiconecta_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.organiconecta_api.modelo.acesso.UsuarioService;
import jakarta.transaction.Transactional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository repository;

     @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;


    @Transactional
    public Admin save(Admin admin) {

         usuarioService.save(admin.getUsuario());

       for (Perfil perfil : admin.getUsuario().getRoles()) {
           perfil.setHabilitado(Boolean.TRUE);
           perfilUsuarioRepository.save(perfil);
       }


        admin.setHabilitado(Boolean.TRUE);
        return repository.save(admin);
    }

    public List<Admin> listarTodos() {
  
        return repository.findAll();
    }

    public Admin obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Admin adminAlterado) {

        Admin admin = repository.findById(id).get();
        admin.setTipo(adminAlterado.getTipo());
        admin.setNome(adminAlterado.getNome());
        admin.setCpf(adminAlterado.getCpf());
        admin.setRg(adminAlterado.getRg());
        admin.setDataNascimento(adminAlterado.getDataNascimento());
        admin.setFoneCelular(adminAlterado.getFoneCelular());
        admin.setFoneFixo(adminAlterado.getFoneFixo());
        admin.setSalario(adminAlterado.getSalario());
        admin.setEnderecoRua(adminAlterado.getEnderecoRua());
        admin.setEnderecoNumero(adminAlterado.getEnderecoNumero());
        admin.setEnderecoBairro(adminAlterado.getEnderecoBairro());
        admin.setEnderecoCidade(adminAlterado.getEnderecoCidade());
        admin.setEnderecoCep(adminAlterado.getEnderecoCep());
        admin.setEnderecoUf(adminAlterado.getEnderecoUf());
        admin.setEnderecoComplemento(adminAlterado.getEnderecoComplemento());
        
        repository.save(admin);
    }

    @Transactional
    public void delete(Long id) {

        Admin admin = repository.findById(id).get();
        admin.setHabilitado(Boolean.FALSE);
        repository.save(admin);
    }
}
