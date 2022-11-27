package homework.second.Task2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;


/**
 * configuration class for Jackson's XML mapper
 */
public class JacksonXmlMapper {

    private static final XmlMapper XML_MAPPER;

    static {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1,true);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        XML_MAPPER = xmlMapper;
    }

    public static XmlMapper getXmlMapper() {
        return XML_MAPPER;
    }


}
