package se.pedramfk.portfolio.pdftotext.converter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public final class PdfInvoiceAdapter {

    public Object map(String content) {

        InvoiceDetails invoiceDetails;
        List<OrderDetails> orderDetails = new ArrayList<>();

        String invoiceName = null;
        String invoiceOrganization = null;
        String invoiceAddressLine1 = null;
        String invoiceAddressLine2 = null;
        Date invoiceDate = null;
        String invoiceUnrecognized = null;

        String[] contentSplit = content.replaceAll("\n\n", ",").trim().split(",");

        boolean isInvoice = false;
        boolean isOrder = false;
        int invoiceCounter = 0;

        for (int i = 0; i < contentSplit.length; i++) {

            if (contentSplit[i].contentEquals("Invoice")) {

                isInvoice = true;
                isOrder = false;
                continue;

            } else if (contentSplit[i].contentEquals("Order Number Item Price Order Count Order Date")) {

                isInvoice = false;
                isOrder = true;
                continue;

            }

            String s = contentSplit[i];

            if (isInvoice) {

                if (invoiceCounter == 0)
                    invoiceName = s;
                else if (invoiceCounter == 1)
                    invoiceOrganization = s;
                else if (invoiceCounter == 2)
                    invoiceAddressLine1 = s;
                else if (invoiceCounter == 3)
                    invoiceAddressLine2 = s;
                else if (invoiceCounter == 4)
                    invoiceDate = Date.valueOf(s);
                else
                    invoiceUnrecognized = invoiceUnrecognized == null ? s : invoiceUnrecognized + s;

                invoiceCounter++;

            } else if (isOrder) {

                String[] orderContentSplit = s.split(" ");

                String id = null;
                double price = -1;
                int count = -1;
                Date date = null;

                try {

                    id = orderContentSplit[0];
                    price = Double.parseDouble(orderContentSplit[1]);
                    count = Integer.parseInt(orderContentSplit[2]);
                    date = Date.valueOf(orderContentSplit[3]);

                    orderDetails.add(new OrderDetails(id, price, count, date));

                } catch (NumberFormatException numberFormatException) {

                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {

                } catch (IllegalArgumentException illegalArgumentException) {

                }

            }

        }

        invoiceDetails = new InvoiceDetails(invoiceName, invoiceOrganization, invoiceAddressLine1, invoiceAddressLine2,
                invoiceDate, invoiceUnrecognized);

        return new InvoiceAndOrderDetails(invoiceDetails, orderDetails);

    }

}
