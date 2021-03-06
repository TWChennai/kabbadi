package invoiceController;

import domain.builder.InvoiceTestBuilder;
import integration.IntegrationTest;
import kabbadi.controller.InvoiceController;
import kabbadi.domain.Invoice;
import kabbadi.domain.Location;
import kabbadi.domain.db.InvoiceRepository;
import kabbadi.domain.json.InvoiceNumberExistent;
import kabbadi.domain.json.PreviousInvoiceRunningBalanceData;
import kabbadi.service.InvoiceService;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class InvoiceControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;
    private InvoiceController controller;
    @Autowired
    private InvoiceRepository invoiceRepository;
    private InvoiceService invoiceService;

    @Before
    public void setup() {
        this.invoiceService = buildInvoiceService();
        this.controller = buildInvoiceController(invoiceService);
    }

    @Test
    public void should_add_a_new_invoice_in_the_database() throws Exception {
        String invoiceNumber = "123456";
        controller.add(invoiceWith(invoiceNumber), "admin");
        assertThat(invoiceService.findBy(invoiceNumber).getInvoiceNumber(), equalTo(invoiceNumber));
    }

    @Test
    public void should_not_add_an_invoice_without_mandatory_fields() throws Exception {
        String invoiceNumber = "";
        controller.add(invoiceWith(invoiceNumber), "admin");
        assertThat(invoiceService.findBy(invoiceNumber), nullValue());
    }

    @Test
    public void should_generate_report_for_given_location() {
        Location location = Location.valueOf("BANGALORE");
        ModelAndView modelAndView = controller.generateReport("BANGALORE");
        assertThat((List<Invoice>) modelAndView.getModel().get("newInvoiceList"), is(equalTo(invoiceService.getOldAndNewData(location).get("newInvoices"))));
    }

    private Invoice invoiceWith(String invoiceNumber) {
        return new InvoiceTestBuilder().withInvoiceNumber(invoiceNumber).build();
    }

    @Test
    public void should_show_new_invoice_form() throws Exception {
        ModelAndView createView = controller.create();
        assertThat(createView.getViewName(), equalTo("invoice/edit"));
    }

    @Test
    public void should_show_the_single_invoice_view_form() {
        Integer invoiceId = 22;
        controller.add(invoiceWith("22"), "");
        ModelAndView singleInvoiceView = controller.viewDetails(invoiceId);
        assertThat(singleInvoiceView.getViewName(), equalTo("invoice/view"));
    }

    @Test
    public void should_list_the_invoices() {
        ModelAndView listView = controller.list();
        assertThat(listView.getViewName(), equalTo("invoice/list"));
    }

    @Test
    public void should_get_the_previous_invoice_when_it_exist() {
        Invoice invoice1 = new InvoiceTestBuilder()
                .withInvoiceNumber("123")
                .withBondNumber("15/20-21")
                .withRunningBalance("123")
                .withLocation(Location.BANGALORE)
                .build();

        Invoice invoice2 = new InvoiceTestBuilder()
                .withInvoiceNumber("123")
                .withBondNumber("15/20-21")
                .withRunningBalance("123")
                .withLocation(Location.CHENNAI)
                .build();

        Invoice invoice3 = new InvoiceTestBuilder()
                .withInvoiceNumber("124")
                .withBondNumber("15/30-31")
                .withRunningBalance("123")
                .withLocation(Location.BANGALORE)
                .build();

        controller.add(invoice1, "");
        controller.add(invoice2, "");

        assertThat(controller.previousRunningBalance("01/21-22", Location.BANGALORE), equalTo(new PreviousInvoiceRunningBalanceData(invoice1)));
        assertThat(controller.previousRunningBalance("01/21-22", Location.CHENNAI), equalTo(new PreviousInvoiceRunningBalanceData(invoice2)));

        assertThat(controller.previousRunningBalance("16/30-31", Location.BANGALORE), equalTo(new PreviousInvoiceRunningBalanceData(invoice3)));

        assertThat(controller.previousRunningBalance("123", Location.PUNE), equalTo(new PreviousInvoiceRunningBalanceData(new Invoice())));
        assertThat(controller.previousRunningBalance("01/1-1", Location.PUNE), equalTo(new PreviousInvoiceRunningBalanceData(null)));
    }

    @Test
    public void should_advise_about_existing_invoices() {
        String invoiceNumber = "invoiceBeta";
        Invoice invoice = new InvoiceTestBuilder()
                .withInvoiceNumber(invoiceNumber)
                .build();

        controller.add(invoice, "");

        assertThat(controller.checkInvoiceNumber(invoiceNumber), equalTo(new InvoiceNumberExistent(invoice)));

        assertThat(controller.checkInvoiceNumber("none"), equalTo(new InvoiceNumberExistent(null)));
    }

    @Test
    public void should_get_old_and_new_data() {
        HashMap<String, List<Invoice>> oldAndNewData = invoiceService.getOldAndNewData(Location.BANGALORE);

        assertThat(oldAndNewData.size(), equalTo(2));
        assertThat(oldAndNewData.get("newInvoices").get(0).getInvoiceNumber(), not("old data"));
        assertThat(oldAndNewData.get("oldInvoices").get(0).getInvoiceNumber(), equalTo("old data"));
    }
    
    private InvoiceController buildInvoiceController(InvoiceService invoiceService) {
        return new InvoiceController(invoiceService);
    }

    private InvoiceService buildInvoiceService() {
        return new InvoiceService(invoiceRepository);
    }
}
