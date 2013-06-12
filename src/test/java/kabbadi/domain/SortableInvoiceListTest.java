package kabbadi.domain;

import kabbadi.domain.builder.InvoiceTestBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SortableInvoiceListTest {

    @Test
    public void should_sort_by_date_of_invoice() {

        Invoice invoiceWithDate1 = new InvoiceTestBuilder().withInvoiceDate("12/12/2012").build();
        Invoice invoiceWithDate2 = new InvoiceTestBuilder().withInvoiceDate("11/12/2012").build();
        Invoice invoiceWithDate3 = new InvoiceTestBuilder().withInvoiceDate("13/12/2012").build();
        List<Invoice> invoices = Arrays.asList(invoiceWithDate1, invoiceWithDate2, invoiceWithDate3);

        SortableInvoiceList sortableList = new SortableInvoiceList(invoices);

        assertEquals(invoiceWithDate3.getDateOfInvoice(), sortableList.sortByInvoiceDate().get(0).getDateOfInvoice());

    }

    @Test
    public void should_sort_by_bond_number() {

        Invoice bondNumber1 = new InvoiceTestBuilder().withBondNumber("12/10-13").build();
        Invoice bondNumber2 = new InvoiceTestBuilder().withBondNumber("13/12-13").build();
        Invoice bondNumber3 = new InvoiceTestBuilder().withBondNumber("14/12-13").build();
        Invoice bondNumber4 = new InvoiceTestBuilder().withBondNumber("11/11-13").build();
        List<Invoice> invoices = Arrays.asList(bondNumber4, bondNumber3, bondNumber2, bondNumber1);

        SortableInvoiceList sortableList = new SortableInvoiceList(invoices);

        assertEquals(bondNumber1.getBondNumber(), sortableList.sortByBondNumber().get(0).getBondNumber());

    }


}
