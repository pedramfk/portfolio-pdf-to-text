package se.pedramfk.portfolio.pdftotext.converter;

import java.sql.Date;
import lombok.Getter;

@Getter
public final class OrderDetails {

    private final String id;
    private final double price;
    private final int count;
    private final Date date;

    public OrderDetails(String id, double price, int count, Date date) {

        this.id = id;
        this.price = price;
        this.count = count;
        this.date = date;

    }

    @Override
    public String toString() {
        return String.format("{'id': %s, 'price': %f, 'count': %d, 'date': %s}",
                id, price, count, date == null ? "" : date.toString());
    }

}
