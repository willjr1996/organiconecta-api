package br.com.ifpe.organiconecta_api.modelo.lojas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojasRepository extends JpaRepository<Lojas, Long> {
  
  
}
