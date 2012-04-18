package kabbadi.controller;

import kabbadi.domain.Invoice;
import kabbadi.service.InvoiceService;
import kabbadi.spring.util.NullSafeDatePropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,  ServletRequestDataBinder binder) throws Exception{
        binder.registerCustomEditor(Date.class,new NullSafeDatePropertyEditor());
    }

    @RequestMapping(value = "invoice/create", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute Invoice invoice) {
        if (invoice.valid()) {
            invoiceService.save(invoice);
            return new ModelAndView(new RedirectView("/invoice/list", true));
        }
        return new ModelAndView(new RedirectView("/invoice/create", true));
    }

    @RequestMapping(value = "invoice/create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("invoice/create", "invoice", new Invoice());
    }

    @RequestMapping(value = "invoice/listAdmin", method = RequestMethod.GET)
    public ModelAndView listAdmin() {
        ModelAndView modelAndView = new ModelAndView("invoice/listAdmin");
        List<Invoice> invoices = invoiceService.list();
        modelAndView.addObject("invoices", invoices);
        return modelAndView;
    }

    @RequestMapping(value = "invoice/listFinance", method = RequestMethod.GET)
    public ModelAndView listFinance() {
        ModelAndView modelAndView = new ModelAndView("invoice/listFinance");
        List<Invoice> invoices = invoiceService.list();
        modelAndView.addObject("invoices", invoices);
        return modelAndView;
    }
}
