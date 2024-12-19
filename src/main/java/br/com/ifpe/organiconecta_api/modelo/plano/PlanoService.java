package br.com.ifpe.organiconecta_api.modelo.plano;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class PlanoService {
    
     @Autowired
    private PlanoRepository repository;

    @Transactional
    public Plano save(Plano plano) {
        
        plano.setHabilitado(Boolean.TRUE);
        return repository.save(plano);

    }

    public List<Plano> listarTodos() {
        
        return repository.findAll();

    }

    public Plano obterPorID(Long id) {
        
        return repository.findById(id).get();
        
    }

    @Transactional
    public void update(Long id, Plano planoAlterado) {
        
        Plano plano = repository.findById(id).get();
        plano.setTipoPlano(planoAlterado.getTipoPlano());
        plano.setPlanoPreco(planoAlterado.getPlanoPreco());
        plano.setValidade(planoAlterado.getValidade());
       

        repository.save(plano);
    }

    @Transactional  
    public void delete(Long id) {
        Plano plano = repository.findById(id).get();
        plano.setHabilitado(Boolean.FALSE);
        repository.save(plano);

   }


}
