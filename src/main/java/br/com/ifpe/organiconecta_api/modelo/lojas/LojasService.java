package br.com.ifpe.organiconecta_api.modelo.lojas;

import br.com.ifpe.organiconecta_api.modelo.produtor.Produtor;
import br.com.ifpe.organiconecta_api.modelo.produtor.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class LojasService {

    @Autowired
    private LojasRepository repository;

    @Autowired
    private ProdutorRepository produtorRepository;

    // Criar Loja com associação a um Produtor
    @Transactional
    public Lojas save(Lojas loja, Long produtorId) {

        // Associa o produtor à loja
        Produtor produtor = produtorRepository.findById(produtorId)
                .orElseThrow(() -> new RuntimeException("Produtor não encontrado"));
        loja.setProdutor(produtor);

        loja.setHabilitado(Boolean.TRUE);
        return repository.save(loja);
    }

    // Listar todas as lojas
    public List<Lojas> listarTodos() {
        return repository.findAll();
    }

    // Obter Loja por ID
    public Lojas obterPorID(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
    }

    // Atualizar Loja e o Produtor associado
    @Transactional
    public void update(Long id, Lojas lojaAlterada, Long produtorId) {
        Lojas lojaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

        lojaExistente.setNomeLoja(lojaAlterada.getNomeLoja());
        lojaExistente.setRegistroPropriedade(lojaAlterada.getRegistroPropriedade());
        lojaExistente.setCertificacao(lojaAlterada.getCertificacao());

        // Atualizar Produtor associado
        if (produtorId != null) {
            Produtor produtor = produtorRepository.findById(produtorId)
                    .orElseThrow(() -> new RuntimeException("Produtor não encontrado"));
            lojaExistente.setProdutor(produtor);
        }

        repository.save(lojaExistente);
    }

    // Deletar Loja
    @Transactional
    public void delete(Long id) {
        Lojas loja = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

        loja.setHabilitado(Boolean.FALSE);
        repository.save(loja);
    }
}
