/*
 * Copyright 2014 CIRDLES.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cirdles.convertfx.tosvg;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.cirdles.convertfx.Converter;
import org.cirdles.convertfx.ToFileConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
public class FXToSVGConverter implements Converter<Node, Document>, ToFileConverter<Node> {

    private DocumentBuilder documentBuilder;
    private Transformer transformer;

    public FXToSVGConverter() {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(FXToSVGConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Document convert(Node node) {
        Document svgDocument = documentBuilder.newDocument();
        svgDocument.setXmlStandalone(true);

        // create and configure svg element
        Element svgElement = svgDocument.createElement("svg");
        svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        svgElement.setAttribute("version", "1.1");

        svgElement.appendChild(
                new GenericNodeConverter(svgDocument).convert(node));

        return svgDocument;
    }

    @Override
    public void convertToFile(Node target, File file) {
        try {
            transformer.setOutputProperty(
                    OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 1.1//EN");
            transformer.setOutputProperty(
                    OutputKeys.DOCTYPE_SYSTEM, "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd");
            transformer.setOutputProperty(
                    javax.xml.transform.OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(convert(target)), new StreamResult(file));
        } catch (TransformerException ex) {
            Logger.getLogger(FXToSVGConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void convertToFile(Node target, Path path) {
        convertToFile(target, path.toFile());
    }

    @Override
    public boolean canConvert(Node node) {
        return node != null;
    }

}
