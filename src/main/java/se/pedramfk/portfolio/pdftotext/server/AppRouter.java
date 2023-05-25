package se.pedramfk.portfolio.pdftotext.server;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.pedramfk.portfolio.pdftotext.converter.PdfConverter;


@Controller
@RequestMapping("/pdf2text/api/1.0")
public final class AppRouter {

    private static final Logger LOGGER = LogManager.getLogger(AppRouter.class);

    @RequestMapping(path = "/test", method = RequestMethod.GET)
	public ResponseEntity<String> index() {

        LOGGER.info("'/test' requested");
        final String response = "Hello, World!";
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

    @RequestMapping(path = "/convert", method = RequestMethod.GET)
	public ResponseEntity<String> convert(String path) {

        LOGGER.info(String.format("'/convert' requested with parameter path = '%s' ", path));
        final PdfConverter converter = new PdfConverter(path);
        return new ResponseEntity<String>(converter.getJson().toString(), converter.getStatus());

	}
    
    @RequestMapping(path = "/convert", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> convert(@RequestBody MultipartFile file) {

        LOGGER.info("'/convert' requested with multipart file");
        final PdfConverter converter = new PdfConverter(file);
        return new ResponseEntity<String>(converter.getJson().toString(), converter.getStatus());

	}

}
