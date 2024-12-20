package br.com.ifpe.organiconecta_api.modelo.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  @Query("SELECT u FROM  Usuario u WHERE u.email= :email AND u.senha = :senha")
  Usuario findByEmailAndSenha(String email, String senha);

  @Query("SELECT u.id FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
  Long findIdByEmailAndSenha(String email, String senha);
}