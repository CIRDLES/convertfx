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
import javafx.scene.shape.Circle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue
 */
class CircleConverter extends ShapeConverter {

    CircleConverter(Document document) {
        super("circle", document);
    }

    @Override
    public Element convert(Node node) {
        Circle circle = (Circle) node;
        Element circleElement = super.convert(node);

        circleElement.setAttribute("cx", String.valueOf(circle.getCenterX()));
        circleElement.setAttribute("cy", String.valueOf(circle.getCenterY()));
        circleElement.setAttribute("r", String.valueOf(circle.getRadius()));

        return circleElement;
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Circle;
    }

}
