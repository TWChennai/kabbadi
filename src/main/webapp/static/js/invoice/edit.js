var kabbadi = window.kabbadi || {};
kabbadi.invoice = kabbadi.invoice || {};
window.kabbadi = kabbadi;

kabbadi.invoice.edit = {
    routeResponse : function(redirectTo, cancelButton, tab) {
        var originalCancelURL = cancelButton.attr("href");
        var incomingTab = ($("a", tab.parent(".active")).attr("href") + "");

        redirectTo.val(incomingTab.replace("#", ""));
        cancelButton.attr("href", originalCancelURL + incomingTab);

        tab.on('shown', function (e) {
            var targetTab = $(e.target).attr("href") + "";
            redirectTo.val(targetTab.replace("#", ""));
            cancelButton.attr("href", originalCancelURL + targetTab);
        });
    },
    editValidator : function() {

        $.validator.addMethod("customDate", function(value,element){
           return this.optional(element) || (/[0-3][0-9]\/[0-1][0-9]\/[0-9]{4}$/).test(value);
        }, "Please enter a valid date");

        $.validator.addMethod("customBondNumber", function(value,element){
           return this.optional(element) || (/[0-9]{2}\/[0-9]{2}-[0-9]{2}$/).test(value);
        }, "eg. 41/11-12");

        $('#newInvoiceForm').validate({
            rules : {
                bondNumber : { customBondNumber : true },
                dateOfInvoice : { customDate : true },
                dateOfCommissioning : { customDate : true },
                bondDate : { customDate : true },
                billOfEntryDate : { customDate : true, required : true },
                dateOfArrival : { customDate : true },
                dateOfCommissioning : { customDate : true },
                invoiceNumber : { required : true },
                "foreignValue.amount" : { number : true },
                amountSTPIApproval : { number : true, required : true },
                assessableValueInINR : { required: true, number : true },
                CIFValueInINR : { number : true },
                cgApprovedInINR : { number : true },
                dutyExempt : { number : true },
                twentyFivePercentDF : { number : true },
                dutyForgone : { number : true },
                quantity : { number : true },
                openingPurchaseValueAsOnApril01 : { number : true },
                additionsDuringTheYear : { number : true },
                deletionsDuringTheYear : { number : true },
                percentageValue : { required : true, number: true },
                billOfEntryNumber : { required : true }
            },

            invalidHandler : function() {
                $("#form_errors_msg").show();
            },
            submitHandler : function(form) {
              var currentInvoiceNumber = $("input[name='invoiceNumber']").val();
              if(kabbadi.invoice.edit.previousInvoiceNumber != currentInvoiceNumber){
                $('#myModal').modal('show');
                $("#submit-modal").click(function(e) {form.submit(); });
              }
              else {
                  form.submit();
              }
            }
        });

    },

    fetchInvoiceNumber : function(baseUrl) {
            var showDuplicateInvoiceAlert = function() {

                $("#invoiceNumber_error_alert").show();

            }
            var removeDuplicateInvoiceAlert = function() {
                $("#invoiceNumber_error_alert").hide();
            }

            $("input[name='invoiceNumber']").blur(function() {
                var $this = $(this);
                $.getJSON(baseUrl + "/invoice/checkInvoiceNumber",
                {
                    invoiceNumber : $this.val(),
                },
                function(invoice) {
                   if(invoice.exists) {
                         var currentInvoiceNumber = $("input[name='invoiceNumber']").val();
                         if(kabbadi.invoice.edit.previousInvoiceNumber != currentInvoiceNumber){
                           showDuplicateInvoiceAlert();
                       }
                   }
                   else {
                       removeDuplicateInvoiceAlert();
                   }
                });

            });

        },

    fillOthers: function(evt) {
        if ($('#newInvoiceForm').validate().element(this)) {
            var assessableValueInINR = $('#assessableValueInINR').val() == "" ? 0 : $('#assessableValueInINR').val();
            var percentageValue = $('#percentageValue').val() ==  "" ? 0 : $('#percentageValue').val();
            var dutyExempt = assessableValueInINR * percentageValue;
            $('#dutyExempt').val(dutyExempt);
            $('#twentyFivePercentDF').val(dutyExempt * 0.25);
        }
    },

    wireUpEvents : function() {
        $('#assessableValueInINR').change(kabbadi.invoice.edit.fillOthers);
        $('#percentageValue').change(kabbadi.invoice.edit.fillOthers);
    },


    initialize : function(baseUrl) {

        $(function () {
            kabbadi.invoice.edit.routeResponse($("#redirectToTab"),$("#cancelButton"), $('a[data-toggle="tab"]'));
            kabbadi.invoice.edit.editValidator();
            kabbadi.invoice.edit.fetchInvoiceNumber(baseUrl);
            kabbadi.invoice.edit.previousInvoiceNumber = $("input[name='invoiceNumber']").val();
            kabbadi.invoice.edit.wireUpEvents();
            $.datepicker.setDefaults({
                dateFormat: 'dd/mm/yy'
            });
            $(".defaultDatepicker").datepicker( );
        });

    }
}