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
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue
 */
class RegionConverter extends ParentConverter {

    private final Document document;

    RegionConverter(Document document) {
        super(document);
        this.document = document;
    }

    @Override
    public Element convert(Node node) {
        Region region = (Region) node;
        Element regionElement = super.convert(node);

        if (region.getBorder() != null) {
            for (BorderStroke borderStroke : region.getBorder().getStrokes()) {
                BorderWidths borderWidths = borderStroke.getWidths();

                if (borderStroke.getTopStroke().isOpaque() && borderWidths.getTop() > 0) {
                    Line line = new Line(0, 0, region.getWidth(), 0);
                    line.setStroke(borderStroke.getTopStroke());
                    line.setStrokeWidth(borderWidths.getTop());

                    regionElement.appendChild(new LineConverter(document).convert(line));
                }

                if (borderStroke.getBottomStroke().isOpaque() && borderWidths.getBottom() > 0) {
                    Line line = new Line(0, region.getHeight(), region.getWidth(), region.getHeight());
                    line.setStroke(borderStroke.getRightStroke());
                    line.setStrokeWidth(borderWidths.getRight());

                    regionElement.appendChild(new LineConverter(document).convert(line));
                }

                if (borderStroke.getLeftStroke().isOpaque() && borderWidths.getLeft() > 0) {
                    Line line = new Line(0, 0, 0, region.getHeight());
                    line.setStroke(borderStroke.getLeftStroke());
                    line.setStrokeWidth(borderWidths.getLeft());

                    regionElement.appendChild(new LineConverter(document).convert(line));
                }

                if (borderStroke.getRightStroke().isOpaque() && borderWidths.getRight() > 0) {
                    Line line = new Line(region.getWidth(), 0, region.getWidth(), region.getHeight());
                    line.setStroke(borderStroke.getRightStroke());
                    line.setStrokeWidth(borderWidths.getRight());

                    regionElement.appendChild(new LineConverter(document).convert(line));
                }
            }
        }

        return regionElement;
    }

    @Override
    public boolean canConvert(Node node) {
        return super.canConvert(node) && node instanceof Region;
    }

}
