package kabbadi.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
@Getter
@Setter
public class Invoice implements Comparable<Invoice>{

    public static final String INVOICE_NUMBER = "invoiceNumber";

    private String invoiceNumber;

    private String STPIApprovalNumberAndDate;
    private String descriptionOfGoods;
    private String currency;
    private BigDecimal foreignCurrency;
    private BigDecimal amountSTPIApproval;
    private BigDecimal CIFValueInINR;
    private String bondNumber;
    private Date dateOfArrival;

    private Date bondDate;
    private String billOfEntryNumber;
    private Date billOfEntryDate;
    private BigDecimal assessableValueInINR;
    private BigDecimal dutyExempt;
    private BigDecimal twentyFivePercentDF;
    private BigDecimal CGApprovedInINR;
    private BigDecimal dutyForgone;
    private BigDecimal runningBalance;
    private BigDecimal outrightPurchase;
    private BigDecimal loanBasis;
    private BigDecimal freeOfCharge;
    private String status;
    private String remarks;
    private String purchaseOrderNumber;
    private String location;

    @Embedded
    FinanceDetails financeDetails;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Invoice() {
        if(financeDetails == null)
            financeDetails = new FinanceDetails();
    }

    public boolean valid() {
        return invoiceNumber != null && !invoiceNumber.isEmpty();
    }

    public BigDecimal totalPurchaseValue() {
        return getFinanceDetails().totalPurchaseValue();
    }

    public FinanceDetails getFinanceDetails() {
        return financeDetails == null ? new FinanceDetails() : financeDetails;
    }

    @Override
    public int compareTo(Invoice invoice) {
        return this.invoiceNumber.compareTo(invoice.invoiceNumber);
    }
}