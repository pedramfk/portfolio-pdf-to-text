package se.pedramfk.portfolio.pdftotext.converter;

import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
public class TestPdfConverter {

    @Test
    public void testGetContent() {

        getTestResource();

        //PdfConverter converter = new PdfConverter("");

        //System.out.println(converter.getRequest().toString());
        //System.out.println(converter.getResponse().toString());

    }

    private final InputStream getTestResource() {
        // TestPdfConverter.class.getClassLoader().getResourceAsStream("comverter/invoice_sample.pdf");
        // ClassLoader.class.getResourceAsStream("invoice_sample.pdf");
        return this.getClass().getClassLoader().getResourceAsStream("converter/invoice_sample.pdf");
    }

}

