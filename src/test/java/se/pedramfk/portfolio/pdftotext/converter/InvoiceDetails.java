package se.pedramfk.portfolio.pdftotext.converter;

import java.sql.Date;
import lombok.Getter;

@Getter
public final class InvoiceDetails {

    private final String name;
    private final String organization;
    private final String addressLine1;
    private final String addressLine2;
    private final Date date;
    private final String unrecognized;

    public InvoiceDetails(
        String name, 
        String organization, 
        String addressLine1, 
        String addressLine2, 
        Date date, 
        String unrecognized) {

        this.name = name;
        this.organization = organization;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.date = date;
        this.unrecognized = unrecognized;

    }

    public InvoiceDetails(
        String name, 
        String organization, 
        String addressLine1, 
        String addressLine2, 
        Date date) {

        this(name, organization, addressLine1, addressLine2, date, null);

    }

    @Override
    public String toString() {
        return String.format("{'name': %s, 'organization': %s, 'addressLine1': %s, 'addressLine2': %s, 'date': %s, 'unrecognized': %s}", 
            name, organization, addressLine1, addressLine2, date == null ? "" : date.toString(), unrecognized);
    }

}
