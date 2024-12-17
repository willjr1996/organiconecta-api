package br.com.ifpe.organiconecta_api.modelo.endereco;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
