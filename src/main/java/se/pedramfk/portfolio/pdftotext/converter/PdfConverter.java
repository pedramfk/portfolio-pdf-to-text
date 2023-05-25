package se.pedramfk.portfolio.pdftotext.converter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import lombok.Getter;
import java.io.InputStream;
import java.sql.Timestamp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.tika.parser.pdf.PDFParser;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.parser.ParseContext;


public final class PdfConverter {

    private static final Logger LOGGER = LogManager.getLogger(PdfConverter.class);

    private @Getter PdfConverterMetadata metadata;
    private @Getter PdfConverterContent content;
    private @Getter HttpStatus status;
    private @Getter Exception exception;

    private @Getter Timestamp requestTimestamp;
    private @Getter Timestamp responseTimestamp;

    public PdfConverter(String path) {
        parse(path);
    }

    public PdfConverter(MultipartFile file) {
        parse(file);
    }

    private static final Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private final boolean parse(InputStream inputStream) {

        try {
            
            this.content = new PdfConverterContent();
            this.metadata = new PdfConverterMetadata();

            ParseContext parseContext = new ParseContext();
            PDFParser parser = new PDFParser();

            parser.parse(inputStream, this.content, this.metadata, parseContext);

            this.status = parser.toString().length() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT;

            if (this.status == HttpStatus.OK) {
                LOGGER.debug("successfully parsed content");
            } else {
                LOGGER.warn("parsed file, but no content extracted");
            }

            return true;

        } catch (Exception exception) {

            this.status = HttpStatus.BAD_REQUEST;
            this.exception = exception;
            return false;

        } finally {

            this.responseTimestamp = getCurrentTimestamp();

        }

    }

    private final boolean parse(MultipartFile multipartFile) {

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException exception) {
            LOGGER.error("failed opening input stream");
            this.status = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exception = exception;
            return false;
        }

        return parse(inputStream);

    }

    private final void parse(String path) {

        this.requestTimestamp = getCurrentTimestamp();

        LOGGER.debug(String.format("parsing %s", path));

        InputStream inputStream = null;
        
        try {

            inputStream = new FileInputStream(new File(path));
            
            this.content = new PdfConverterContent();
            this.metadata = new PdfConverterMetadata();
            
            PDFParser parser = new PDFParser();
            parser.parse(inputStream, this.content, this.metadata, new ParseContext());

            this.status = parser.toString().length() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT;

            if (this.status == HttpStatus.OK) {
                LOGGER.debug(String.format("parsed content from %s", path));
            } else {
                LOGGER.warn(String.format("no content parsed from %s", path));
            }

        } catch (Exception exception) {

            this.status = HttpStatus.BAD_REQUEST;
            this.exception = exception;

            try {

                LOGGER.debug("closing input stream");
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException ioException) {
                LOGGER.warn("failed closing input stream");
            }

        } finally {
            this.responseTimestamp = getCurrentTimestamp();
        }

    }

    public final JSONObject getJson() {
        return new JSONObject() {{
            put("parsed", getContent().toString());
            put("metadata", getMetadata().getJson());
            put("created_at", getRequestTimestamp());
            put("updated_at", getResponseTimestamp());
        }};
    }


    public static final void main(String[] args) throws Exception {

        final String path = "/Users/pedramfk/workspace/git/portfolio/pdf-to-text/src/test/resources/converter/invoice_sample.pdf";
        
        PdfConverter pdfConverter = new PdfConverter(path);

        System.out.println(pdfConverter.getJson().toString(10));

    }

}