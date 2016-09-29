/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se2.ideafactory.model;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Vimukthi
 */

public class TempDataWriteToXML {

    Hashtable<Integer, String> hashTableObject = new Hashtable<Integer, String>();

    public void setHashTable(Hashtable ht) {
        this.hashTableObject = ht;
        System.out.println("bind test: "+ht);
    }

    // Write data to xml
    public void writeToXmlFile(String path) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Project");
            doc.appendChild(rootElement);
            for (Map.Entry m : hashTableObject.entrySet()) {
                // Student elements
                Element Student = doc.createElement("Student");
                rootElement.appendChild(Student);

                // set attribute to Student element
                Attr attr = doc.createAttribute("student_name");
                attr.setValue(m.getKey() + "");
                Student.setAttributeNode(attr);

                // Project Title elements
                Element ProjectTitle = doc.createElement("ProjectTitle");
                ProjectTitle.appendChild(doc.createTextNode(m.getValue() + ""));
                //pass the project title to this method call
                Student.appendChild(ProjectTitle);
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //Change the path
            StreamResult result = new StreamResult(new File(path + ".xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
    //END XML file write

    //Start Implement tsv file write
    public void writeToCsvFile(String path) {
        //Delimiter used in TSV file
        String TAB_DELIMITER = "\t";
        String NEW_LINE_SEPARATOR = "\n";

        String FILE_HEADER = "Student   Project Title";

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path + ".tsv");
            //Write the TSV file header
            fileWriter.append(FILE_HEADER.toString());
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Entry<Integer, String> mapEntry : hashTableObject.entrySet()) {
                String key = String.valueOf(mapEntry.getKey());
                String value = String.valueOf(mapEntry.getValue());
                //System.out.println("final test" + key + "--" + value);
                fileWriter.append(key);
                fileWriter.append(TAB_DELIMITER);
                fileWriter.append(value);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            System.out.println("TSV file was created successfully !!!");
        } catch (Exception e) {
            System.out.println("Error in TsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }

    }
    //end tsv implemantation

}
