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
import javafx.scene.shape.Line;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
public class LineConverter extends ShapeConverter {

    public LineConverter(Document document) {
        super("line", document);
    }

    @Override
    public Element convert(Node node) {
        Line line = (Line) node;
        Element lineElement = super.convert(node);

        lineElement.setAttribute("x1", String.valueOf(line.getStartX()));
        lineElement.setAttribute("y1", String.valueOf(line.getStartY()));
        lineElement.setAttribute("x2", String.valueOf(line.getEndX()));
        lineElement.setAttribute("y2", String.valueOf(line.getEndY()));

        return super.convert(node);
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Line;
    }

}
