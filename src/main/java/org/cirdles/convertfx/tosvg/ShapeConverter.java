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

import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
abstract class ShapeConverter extends NodeConverter {

    ShapeConverter(String tagName, Document document) {
        super(tagName, document);
    }

    @Override
    public Element convert(Node node) {
        Shape shape = (Shape) node;
        Element shapeElement = super.convert(node);

        shapeElement.setAttribute("fill", convertPaintToSVG(shape.getFill()));

        shapeElement.setAttribute("stroke", convertPaintToSVG(shape.getStroke()));
        shapeElement.setAttribute("stroke-width", String.valueOf(shape.getStrokeWidth()));
        shapeElement.setAttribute("stroke-linecap", shape.getStrokeLineCap().name().toLowerCase());
        shapeElement.setAttribute("stroke-linejoin", shape.getStrokeLineJoin().name().toLowerCase());
        shapeElement.setAttribute("stroke-miterlimit", String.valueOf(shape.getStrokeMiterLimit()));

        if (!shape.getStrokeDashArray().isEmpty()) {
            shapeElement.setAttribute("stroke-dasharray", String.join(",", shape.getStrokeDashArray()
                                                                      .stream()
                                                                      .map(String::valueOf)
                                                                      .collect(Collectors.toList())));
            shapeElement.setAttribute("stroke-dashoffset", String.valueOf(shape.getStrokeDashOffset()));
        }

        shapeElement.setAttribute("opacity", String.valueOf(shape.getOpacity()));

        return shapeElement;
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Shape;
    }

    private static String convertPaintToSVG(Paint paint) {
        if (paint == null || !paint.isOpaque() || !(paint instanceof Color)) {
            return "none";
        }

        Color color = (Color) paint;
        return String.format("rgb(%d,%d,%d)",
                             (int) (color.getRed() * 255),
                             (int) (color.getGreen() * 255),
                             (int) (color.getBlue() * 255));
    }

}
