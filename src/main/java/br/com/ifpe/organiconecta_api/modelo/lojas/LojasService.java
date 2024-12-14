package br.com.ifpe.organiconecta_api.modelo.lojas;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LojasService {
    @Autowired
    private LojasRepository repository;

    @Transactional
    public Lojas save(Lojas lojas) {
        
        lojas.setHabilitado(Boolean.TRUE);
        return repository.save(lojas);

    }
    public List<Lojas> listarTodos() {
        
        return repository.findAll();

    }
    public Lojas obterPorID(Long id) {
        
        return repository.findById(id).get();
        
    }
    @Transactional
    public void update(Long id, Lojas lojasAlterado) {
        
        Lojas lojas = repository.findById(id).get();
        lojas.setNomeLoja(lojasAlterado.getNomeLoja());
        lojas.setRegistroPropriedade(lojasAlterado.getRegistroPropriedade());
        lojas.setCertificacao(lojasAlterado.getCertificacao());
        repository.save(lojas);
    }
    @Transactional  
    public void delete(Long id) {
        Lojas lojas = repository.findById(id).get();
        lojas.setHabilitado(Boolean.FALSE);
        repository.save(lojas);

   }
}
