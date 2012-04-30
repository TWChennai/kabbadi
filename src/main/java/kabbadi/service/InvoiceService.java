package kabbadi.service;

import kabbadi.domain.Invoice;
import kabbadi.domain.db.GenericRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
public class InvoiceService {

    private GenericRepository<Invoice> invoiceRepository;

    @Autowired
    public InvoiceService(@Qualifier("invoiceRepository") GenericRepository<Invoice> invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public Invoice findBy(String invoiceNumber) {
        return invoiceRepository.findBy(Invoice.INVOICE_NUMBER, invoiceNumber);
    }

    @Transactional
    public void saveOrUpdate(Invoice invoice) {
        invoiceRepository.saveOrUpdate(invoice);
    }

    @Transactional
    public List<Invoice> list() {
        return invoiceRepository.list();
    }


    @Transactional(readOnly = true)
    public Invoice get(Integer id) {
        return invoiceRepository.get(id);
    }
}
