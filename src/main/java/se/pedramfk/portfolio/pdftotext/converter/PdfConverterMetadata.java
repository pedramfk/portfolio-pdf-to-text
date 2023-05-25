package se.pedramfk.portfolio.pdftotext.converter;

import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.json.JSONObject;
import org.springframework.boot.json.BasicJsonParser;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;


final class PdfConverterMetadata extends Metadata {

    PdfConverterMetadata() {
        super();
    }

    private static final JSONObject getNestedJson(JSONObject obj, String[] keys, Object value) {

        String currentKey = keys[0];

        if (keys.length == 1) {
            return obj.put(currentKey, value);
        }

        String[] remainingKeys = Arrays.copyOfRange(keys, 1, keys.length);
        JSONObject nestedObj = obj.has(currentKey) ? obj.getJSONObject(currentKey) : new JSONObject();
        return obj.put(currentKey, getNestedJson(nestedObj, remainingKeys, value));

    }

    private static final void setNestedJson(JSONObject obj, String[] keys, Object value) {

        String currentKey = keys[0];

        if (keys.length == 1) {
            obj.put(currentKey, value);
        } else {
            String[] remainingKeys = Arrays.copyOfRange(keys, 1, keys.length);
            JSONObject nestedObj = obj.has(currentKey) ? obj.getJSONObject(currentKey) : new JSONObject();
            obj.put(currentKey, getNestedJson(nestedObj, remainingKeys, value));
        }

    }

    private static final Object getInferredValue(Object value) {
        try {

            if (value == null) return null;

            String s = value.toString();
            if (s.toLowerCase().contentEquals("true") || s.toLowerCase().contentEquals("false")) {
                return Boolean.parseBoolean(s.toLowerCase().toString());
            }

            try {
                return Timestamp.valueOf(s);
            } catch (IllegalArgumentException e) {}

            try {
                return Integer.valueOf(s);
            } catch (IllegalArgumentException e) {}

            try {
                return Float.valueOf(s);
            } catch (IllegalArgumentException e) {}

            return s;

        } catch (Exception e) {
            return value;
        }
    }

    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        for (String key : names()) {
            Object value = getInferredValue(getValues(key)[0]);
            String[] nestedKeys = key.split("\\:");
            setNestedJson(obj, nestedKeys, value);
        }
        return obj;
    }

    public JSONObject getJsonFromString() {
        return new JSONObject(new BasicJsonParser().parseMap(toString()));
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();

        sb.append("{");
        boolean isFirstElement = true;
        for (String key : names()) {

            System.out.println(key);
            String[] values = getValues(key);

            StringBuilder newKey = new StringBuilder();
            for (int i = 0; i < key.length(); i++) {
                if ((i > 0) && (key.charAt(i - 1) == '_')) {
                    newKey.append(key.substring(i, i + 1).toUpperCase());
                } else if (key.charAt(i) != '_') {
                    newKey.append(key.charAt(i));
                }
            }

            if (!isFirstElement)
                sb.append(",");

            sb.append(String.format("\"%s\":", newKey.toString().replaceAll("\\:", ".")));

            if (values.length == 1)
                sb.append("\"" + values[0] + "\"");
            else
                sb.append(String.join(",", values));

            isFirstElement = false;

        }

        sb.append("}");

        return sb.toString();

    }

    public static void main(String[] args) throws IOException, SAXException, TikaException {

        final String path = "/Users/pedramfk/workspace/git/portfolio/pdf-to-text/src/test/resources/converter/invoice_sample.pdf";
        PdfConverterMetadata metadata = new PdfConverterMetadata();

        new PDFParser() {
            {
                parse(new FileInputStream(new File(path)), new PdfConverterContent(), metadata, new ParseContext());
            }
        };

        //System.out.println(metadata.toString());
        System.out.println(metadata.getJson().toString(10));

        // System.out.println(metadata.getValues(SOURCE).toString());

    }

}

/*

    public boolean hasValues(String key) {

        System.out.println(getValues(key));
        return getValues(key).length > 0;
    }
 * public String get(String key) {
 * return hasValues(key) ? getValues(key)[0] : null;
 * }
 * 
 * public boolean getBoolean(String key) {
 * return hasValues(key) ? Boolean.parseBoolean(get(key)) : null;
 * }
 * 
 * public int getInt(String key) {
 * return hasValues(key) ? Integer.parseInt(get(key)) : null;
 * }
 * 
 * public float getFloat(String key) {
 * return hasValues(key) ? Float.parseFloat(get(key)) : null;
 * }
 * 
 * public JSONObject pdfDocinfo() {
 * return new JSONObject() {{
 * put("title", get("pdf:docinfo:title"));
 * put("producer", get("pdf:docinfo:producer"));
 * put("creator_tool", get("pdf:docinfo:creator_tool"));
 * put("modified", get("pdf:docinfo:modified"));
 * put("created", get("pdf:docinfo:created"));
 * }};
 * }
 * 
 * public JSONObject pdf() {
 * return new JSONObject() {{
 * put("pdfVersion", get("pdf:PDFVersion"));
 * put("encrypted", getBoolean("pdf:encrypted"));
 * put("hasCollection", getBoolean("pdf:hasCollection"));
 * put("hasXFA", getBoolean("pdf:hasXFA"));
 * put("hasXMP", getBoolean("pdf:hasXMP"));
 * put("hasMarkedContent", getBoolean("pdf:hasMarkedContent"));
 * put("containsDamagedFont", getBoolean("pdf:containsDamagedFont"));
 * put("containsNonEmbeddedFont", getBoolean("pdf:containsNonEmbeddedFont"));
 * put("producer", get("pdf:producer"));
 * put("num3DAnnotations", getInt("pdf:num3DAnnotations"));
 * put("charsPerPage", getInt("pdf:charsPerPage"));
 * put("unmappedUnicodeCharsPerPage",
 * getInt("pdf:unmappedUnicodeCharsPerPage"));
 * put("totalUnmappedUnicodeChars", getInt("pdf:totalUnmappedUnicodeChars"));
 * put("overallPercentageUnmappedUnicodeChars",
 * getFloat("pdf:num3DAnnotations"));
 * put("docinfo", new JSONObject() {{
 * put("title", get("pdf:docinfo:title"));
 * put("producer", get("pdf:docinfo:producer"));
 * put("creator_tool", get("pdf:docinfo:creator_tool"));
 * put("modified", get("pdf:docinfo:modified"));
 * put("created", get("pdf:docinfo:created"));
 * }});
 * }};
 * }
 * 
 * public JSONObject accessPermission() {
 * return new JSONObject() {{
 * put("extractContent", getBoolean("access_permission:extract_content"));
 * put("extractForAccessibility",
 * getBoolean("access_permission:extract_for_accessibility"));
 * put("canPrint", getBoolean("access_permission:can_print"));
 * put("canPrintDegraded", getBoolean("access_permission:can_print_degraded"));
 * put("canModify", getBoolean("access_permission:can_modify"));
 * put("assembleDocument", getBoolean("access_permission:assemble_document"));
 * put("fillInForm", getBoolean("access_permission:fill_in_form"));
 * put("modifyAnnotations", getBoolean("access_permission:modify_annotations"));
 * }};
 * }
 * 
 * public JSONObject dc() {
 * return new JSONObject() {{
 * put("title", get("dc:title"));
 * put("format", get("dc:format"));
 * }};
 * }
 * 
 * public JSONObject dcTerms() {
 * return new JSONObject() {{
 * put("created", get("dcterms:created"));
 * put("modified", get("dcterms:modified"));
 * }};
 * }
 * 
 * public JSONObject xmpTPg() {
 * return new JSONObject() {{
 * put("numberOfPages", getInt("mpTPg:NPages"));
 * }};
 * }
 * 
 * public JSONObject xmp() {
 * return new JSONObject() {{
 * put("creatorTool", get("xmp:CreatorTool"));
 * }};
 * }
 */