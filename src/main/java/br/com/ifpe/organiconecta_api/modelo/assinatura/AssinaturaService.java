package br.com.ifpe.organiconecta_api.modelo.assinatura;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository repository;

    @Transactional
    public Assinatura save(Assinatura assinatura) {

        assinatura.setHabilitado(Boolean.TRUE);
        assinatura.setStatus(Boolean.FALSE);
        return repository.save(assinatura);

    }

    public List<Assinatura> listarTodos() {

        return repository.findAll();

    }

    public Assinatura obterPorID(Long id) {

        return repository.findById(id).get();

    }

    @Transactional
    public void update(Long id, Assinatura assinaturaAlterado) {

        Assinatura assinatura = repository.findById(id).get();
        assinatura.setDataInicio(assinaturaAlterado.getDataInicio());
        assinatura.setValidade(assinaturaAlterado.getValidade());
        assinatura.setStatus(assinaturaAlterado.getStatus());
        assinatura.setTipoPlano(assinaturaAlterado.getTipoPlano());
        assinatura.setPlanoPreco(assinaturaAlterado.getPlanoPreco());

        repository.save(assinatura);
    }

    @Transactional
    public void delete(Long id) {
        Assinatura assinatura = repository.findById(id).get();
        assinatura.setHabilitado(Boolean.FALSE);
        assinatura.setStatus(Boolean.FALSE);
        repository.save(assinatura);

    }

}
