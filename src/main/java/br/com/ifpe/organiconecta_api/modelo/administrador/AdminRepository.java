package br.com.ifpe.organiconecta_api.modelo.administrador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
}
