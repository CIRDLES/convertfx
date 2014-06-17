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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue
 */
class PathConverter extends ShapeConverter {

    private static final String ABSOLUTE_MOVETO_FORMAT = "M %f %f";
    private static final String RELATIVE_MOVETO_FORMAT = "m %f %f";

    private static final String ABSOLUTE_LINETO_FORMAT = "L %f %f";
    private static final String RELATIVE_LINETO_FORMAT = "l %f %f";

    private static final String ABSOLUTE_QUADRATIC_CURVETO_FORMAT = "Q %f %f %f %f";
    private static final String RELATIVE_QUADRATIC_CURVETO_FORMAT = "q %f %f %f %f";

    private static final String ABSOLUTE_CUBIC_CURVETO_FORMAT = "C %f %f %f %f %f %f";
    private static final String RELATIVE_CUBIC_CURVETO_FORMAT = "c %f %f %f %f %f %f";

    PathConverter(Document document) {
        super("path", document);
    }

    @Override
    public Element convert(Node node) {
        Path path = (Path) node;
        Element pathElement = super.convert(node);

        pathElement.setAttribute("d", buildPathDataString(path));

        return pathElement;
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Path;
    }

    private static String buildPathDataString(Path path) {
        List<String> pathElements = new ArrayList<>(path.getElements().size());

        for (PathElement pathElement : path.getElements()) {
            if (pathElement instanceof MoveTo) {
                MoveTo moveTo = (MoveTo) pathElement;
                String movetoFormat = moveTo.isAbsolute() ? ABSOLUTE_MOVETO_FORMAT : RELATIVE_MOVETO_FORMAT;

                pathElements.add(String.format(movetoFormat, moveTo.getX(), moveTo.getY()));
            } else if (pathElement instanceof LineTo) {
                LineTo lineTo = (LineTo) pathElement;
                String linetoFormat = lineTo.isAbsolute() ? ABSOLUTE_LINETO_FORMAT : RELATIVE_LINETO_FORMAT;

                pathElements.add(String.format(linetoFormat, lineTo.getX(), lineTo.getY()));
            } else if (pathElement instanceof QuadCurveTo) {
                QuadCurveTo quadCurveTo = (QuadCurveTo) pathElement;
                String quadraticCurvetoFormat = quadCurveTo.isAbsolute()
                                                ? ABSOLUTE_QUADRATIC_CURVETO_FORMAT : RELATIVE_QUADRATIC_CURVETO_FORMAT;
                
                pathElements.add(String.format(quadraticCurvetoFormat,
                                               quadCurveTo.getControlX(), quadCurveTo.getControlY(),
                                               quadCurveTo.getX(), quadCurveTo.getY()));
            } else if (pathElement instanceof CubicCurveTo) {
                CubicCurveTo cubicCurveTo = (CubicCurveTo) pathElement;
                String cubicCurvetoFormat = cubicCurveTo.isAbsolute()
                                            ? ABSOLUTE_CUBIC_CURVETO_FORMAT : RELATIVE_CUBIC_CURVETO_FORMAT;
                
                pathElements.add(String.format(cubicCurvetoFormat,
                                               cubicCurveTo.getControlX1(), cubicCurveTo.getControlY1(),
                                               cubicCurveTo.getControlX2(), cubicCurveTo.getControlY2(),
                                               cubicCurveTo.getX(), cubicCurveTo.getY()));
            }
        }

        return String.join(" ", pathElements);
    }

}
