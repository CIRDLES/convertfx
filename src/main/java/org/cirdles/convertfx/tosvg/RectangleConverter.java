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
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue
 */
public class RectangleConverter extends ShapeConverter {

    RectangleConverter(Document document) {
        super("rect", document);
    }

    @Override
    public Element convert(Node node) {
        Rectangle rectangle = (Rectangle) node;
        Element rectangleElement = super.convert(node);

        rectangleElement.setAttribute("x", String.valueOf(rectangle.getX()));
        rectangleElement.setAttribute("y", String.valueOf(rectangle.getY()));
        rectangleElement.setAttribute("width", String.valueOf(rectangle.getWidth()));
        rectangleElement.setAttribute("height", String.valueOf(rectangle.getHeight()));

        return rectangleElement;
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Rectangle;
    }
}
