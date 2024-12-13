package br.com.ifpe.organiconecta_api.modelo.usuario;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario save(Usuario usuario) {
        
        usuario.setHabilitado(Boolean.TRUE);
        return repository.save(usuario);

    }

    public List<Usuario> listarTodos() {
        
        return repository.findAll();

    }

    public Usuario obterPorID(Long id) {
        
        return repository.findById(id).get();
        
    }

    @Transactional
    public void update(Long id, Usuario usuarioAlterado) {
        
        Usuario usuario = repository.findById(id).get();
        usuario.setNome(usuarioAlterado.getNome());
        usuario.setEmail(usuarioAlterado.getEmail());
        usuario.setTelefone(usuarioAlterado.getTelefone());
        usuario.setCpf(usuarioAlterado.getCpf());
        usuario.setDataNascimento(usuarioAlterado.getDataNascimento());
        usuario.setSenha(usuarioAlterado.getSenha());

        repository.save(usuario);
    }

    @Transactional  
    public void delete(Long id) {
        Usuario usuario = repository.findById(id).get();
        usuario.setHabilitado(Boolean.FALSE);
        repository.save(usuario);

   }

//    @Transactional
//     public String Login (String email, String senha){
//     Usuario usuario = repository.findByEmailAndSenha(email, senha);
    
//         if(usuario !=  null){
//             return usuario.getNome();
//         }
//         return null;
//    }

    @Transactional
    public UsuarioId getIdByCredenciais(Credenciais credenciais) {
        Long userId = repository.findIdByEmailAndSenha(credenciais.getEmail(), credenciais.getSenha());
        
        if (userId == null) {
            throw new NoSuchElementException("Usuário não encontrado com as credenciais fornecidas.");
        }
        return new UsuarioId(userId);
    }
}