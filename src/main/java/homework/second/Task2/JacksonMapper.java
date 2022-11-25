package homework.second.Task2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

public class JacksonMapper {
    private static final JsonMapper JSON_MAPPER;
    private static final XmlMapper XML_MAPPER;

    static {
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSON_MAPPER = jsonMapper;

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1,true);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        XML_MAPPER = xmlMapper;
    }

    public static JsonMapper getJsonMapper() {
        return JSON_MAPPER;
    }

    public static XmlMapper getXmlMapper() {
        return XML_MAPPER;
    }


}
