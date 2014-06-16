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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.cirdles.convertfx.FXConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
public class FXToSVGConverter implements FXConverter<Document> {

    private DocumentBuilder documentBuilder;

    public FXToSVGConverter() {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
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
    public boolean canConvert(Node node) {
        return node != null;
    }

}
