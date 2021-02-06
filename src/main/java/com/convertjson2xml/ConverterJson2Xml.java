package com.convertjson2xml;

import com.convertjson2xml.util.PropertiesHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ConverterJson2Xml {

    private Logger LOG = Logger.getLogger(ConverterJson2Xml.class.getName());

    @Autowired
    private PropertiesHelper propertiesHelper;

    public void converter() throws IOException {

        LOG.info("--- [CONVERTER JSON 2 XML] Converter method START ---");

        List<File> files = this.getFiles();
        for (File file : files) {
            String json = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            JSONObject jsonObject = new JSONObject(json);
            String xml = XML.toString(jsonObject);

            this.saveFile(xml, file);
        }

        LOG.info("--- [CONVERTER JSON 2 XML] Converter method END ---");
    }

    private List<File> getFiles(){

        String file = propertiesHelper.getProperty("file");
        File dir = new File(file);
        List<File> files = Arrays.asList(dir.listFiles((dir1, name) -> name.endsWith(".json")));

        return files;
    }

    private void saveFile(String xml, File file) throws IOException {
        String unEscapeXml = StringEscapeUtils.unescapeXml(xml);

        String path = file.getAbsolutePath();
        path = this.removeFileExtension(path);
        try(FileWriter fileWriter = new FileWriter(path + ".xml")){
            fileWriter.write(unEscapeXml); //prettyFormat(unEscapeXml, 2)
        }
    }

    private String removeFileExtension(String fileName){
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }
}
