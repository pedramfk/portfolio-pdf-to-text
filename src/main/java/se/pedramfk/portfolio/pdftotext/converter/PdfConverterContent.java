package se.pedramfk.portfolio.pdftotext.converter;

import org.apache.tika.sax.BodyContentHandler;


final class PdfConverterContent extends BodyContentHandler {

    PdfConverterContent() {
        super();
    }

    PdfConverterContent(int writeLimit) {
        super(writeLimit);
    }
    
    public final String getContent(String regex, String replacement) {
        return super.toString().replaceAll(regex, replacement);
    }

    public final String getContent() {
        return super.toString();
    }

}
