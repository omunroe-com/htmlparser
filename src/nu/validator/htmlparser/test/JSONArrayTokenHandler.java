package nu.validator.htmlparser.test;

import nu.validator.htmlparser.ContentModelFlag;
import nu.validator.htmlparser.TokenHandler;
import nu.validator.htmlparser.Tokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONBoolean;
import com.sdicons.json.model.JSONNull;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;

public class JSONArrayTokenHandler implements TokenHandler, ErrorHandler {

    private static final JSONString DOCTYPE = new JSONString("DOCTYPE");

    private static final JSONString START_TAG = new JSONString("StartTag");

    private static final JSONString END_TAG = new JSONString("EndTag");

    private static final JSONString COMMENT = new JSONString("Comment");

    private static final JSONString CHARACTER = new JSONString("Character");

    private static final JSONString PARSE_ERROR = new JSONString("ParseError");

    private final StringBuilder builder = new StringBuilder();

    private JSONArray array = null;

    private ContentModelFlag contentModelFlag;

    private String contentModelElement;
    
    public void setContentModelFlag(ContentModelFlag contentModelFlag, String contentModelElement) {
        this.contentModelFlag = contentModelFlag;
        this.contentModelElement = contentModelElement;
    }

    public void characters(char[] buf, int start, int length)
            throws SAXException {
        builder.append(buf, start, length);
    }

    private void flushCharacters() {
        if (builder.length() > 0) {
            JSONArray token = new JSONArray();
            token.getValue().add(CHARACTER);
            token.getValue().add(new JSONString(builder.toString()));
            array.getValue().add(token);
            builder.setLength(0);
        }
    }

    public void comment(char[] buf, int length) throws SAXException {
        flushCharacters();
        JSONArray token = new JSONArray();
        token.getValue().add(COMMENT);
        token.getValue().add(new JSONString(new String(buf, 0, length)));
        array.getValue().add(token);
    }

    public void doctype(String name, String publicIdentifier, String systemIdentifier, boolean correct) throws SAXException {
        flushCharacters();
        JSONArray token = new JSONArray();
        token.getValue().add(DOCTYPE);
        token.getValue().add(new JSONString(name));
        token.getValue().add(publicIdentifier == null ? JSONNull.NULL : new JSONString(publicIdentifier));
        token.getValue().add(systemIdentifier == null ? JSONNull.NULL : new JSONString(systemIdentifier));
        token.getValue().add(new JSONBoolean(correct));
        array.getValue().add(token);
    }

    public void endTag(String name, Attributes attributes) throws SAXException {
        flushCharacters();
        JSONArray token = new JSONArray();
        token.getValue().add(END_TAG);
        token.getValue().add(new JSONString(name));
        array.getValue().add(token);
    }

    public void eof() throws SAXException {
        flushCharacters();
    }

    public void start(Tokenizer self) throws SAXException {
        array = new JSONArray();
        self.setContentModelFlag(contentModelFlag, contentModelElement);
    }

    public void startTag(String name, Attributes attributes)
            throws SAXException {
        flushCharacters();
        JSONArray token = new JSONArray();
        token.getValue().add(START_TAG);
        token.getValue().add(new JSONString(name));
        JSONObject attrs = new JSONObject();
        for (int i = 0; i < attributes.getLength(); i++) {
            attrs.getValue().put(attributes.getQName(i), new JSONString(attributes.getValue(i)));
        }
        token.getValue().add(attrs);
        array.getValue().add(token);
    }

    public boolean wantsComments() throws SAXException {
        return true;
    }

    public void error(SAXParseException exception) throws SAXException {
        flushCharacters();
        array.getValue().add(PARSE_ERROR);
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        throw new RuntimeException("Should never happen.");
    }

    public void warning(SAXParseException exception) throws SAXException {
    }

    /**
     * Returns the array.
     * 
     * @return the array
     */
    public JSONArray getArray() {
        return array;
    }

}