package se.pedramfk.portfolio.pdftotext.converter;

import java.util.List;
import lombok.Getter;


@Getter
//@Entity
public final class InvoiceAndOrderDetails {

    // @Column(nullable = false, unique = false) 
    private final InvoiceDetails invoiceDetails;
    // @Column(nullable = false, unique = false) 
    private final List<OrderDetails> orderDetails;

    public InvoiceAndOrderDetails(InvoiceDetails invoiceDetails, List<OrderDetails> orderDetails) {
        this.invoiceDetails = invoiceDetails;
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\n 'invoice': " + invoiceDetails.toString());
        sb.append(", \n 'orders': [\n");
        for (OrderDetails orderDetail : orderDetails)
            sb.append(", \n  " + orderDetail.toString());
        sb.append(" ]\n}");

        return sb.toString();

    }

}
