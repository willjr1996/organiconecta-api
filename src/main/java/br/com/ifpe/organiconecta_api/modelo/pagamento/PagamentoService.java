package br.com.ifpe.organiconecta_api.modelo.pagamento;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    
    @Autowired
    private PagamentoRepository repository;

    @Transactional
    public Pagamento save(Pagamento pagamento) {
        
        pagamento.setHabilitado(Boolean.TRUE);
        return repository.save(pagamento);

    }

    public List<Pagamento> listarTodos() {
        
        return repository.findAll();

    }

    public Pagamento obterPorID(Long id) {
        
        return repository.findById(id).get();
        
    }

    @Transactional
    public void update(Long id, Pagamento pagamentoAlterado) {
        
        Pagamento pagamento = repository.findById(id).get();
        pagamento.setPagamentoFeito(pagamentoAlterado.getPagamentoFeito());
        // pagamento.setTipoPagamento(pagamentoAlterado.getTipoPagamento());
        // pagamento.setModalidade(pagamentoAlterado.getModalidade());
        // pagamento.setCartao(pagamentoAlterado.getCartao());

       

        repository.save(pagamento);
    }

    @Transactional  
    public void delete(Long id) {
        Pagamento pagamento = repository.findById(id).get();
        pagamento.setHabilitado(Boolean.FALSE);
        repository.save(pagamento);

   }


}
