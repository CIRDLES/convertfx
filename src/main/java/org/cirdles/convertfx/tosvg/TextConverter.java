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

import javafx.scene.Node;
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue
 */
class TextConverter extends ShapeConverter {

    TextConverter(Document document) {
        super("text", document);
    }

    @Override
    public Element convert(Node node) {
        Text text = (Text) node;
        Element textElement = super.convert(node);
        
        textElement.setAttribute("x", String.valueOf(text.getX()));
        textElement.setAttribute("y", String.valueOf(text.getY()));
        textElement.setAttribute("font-family", text.getFont().getFamily());
        textElement.setAttribute("font-size", String.valueOf(text.getFont().getSize()));
        
        textElement.setTextContent(text.getText());
        
        return super.convert(node);
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Text;
    }
    
}
