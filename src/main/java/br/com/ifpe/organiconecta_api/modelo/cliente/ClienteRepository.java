package br.com.ifpe.organiconecta_api.modelo.cliente;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  // @Query("SELECT u FROM  Usuario u WHERE u.email= :email AND u.senha = :senha")
  // Cliente findByEmailAndSenha(String email, String senha);

  // @Query("SELECT u.id FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
  // Long findIdByEmailAndSenha(String email, String senha);
}