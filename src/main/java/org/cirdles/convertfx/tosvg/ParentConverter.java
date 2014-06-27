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
import javafx.scene.Parent;
import org.cirdles.convertfx.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
class ParentConverter extends NodeConverter {

    private final Document document;

    ParentConverter(Document document) {
        super("g", document);
        this.document = document;
    }

    @Override
    public Element convert(Node node) {
        Parent parent = (Parent) node;
        Element parentElement = super.convert(node);
        
        String transform = parentElement.getAttribute("transform");
        transform += String.format(" translate(%f,%f)", parent.getLayoutX(), parent.getLayoutY());
        parentElement.setAttribute("transform", transform);

        Converter<Node, Element> genericConverter = new GenericNodeConverter(document);
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (genericConverter.canConvert(child)) {
                parentElement.appendChild(genericConverter.convert(child));
            }
        }

        return parentElement;
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Parent;
    }

}
