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
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import org.cirdles.convertfx.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
abstract class NodeConverter implements Converter<Node, Element> {

    private static final String TRANSLATE_FORMAT = "translate(%f,%f)";
    private static final String SCALE_FORMAT = "scale(%f,%f)";
    private static final String ROTATE_FORMAT = "rotate(%f,%f,%f)";

    private static int clipPathCount = 0;

    private final Document document;
    private final String tagName;

    NodeConverter(String tagName, Document document) {
        this.tagName = tagName;
        this.document = document;
    }

    @Override
    public Element convert(Node node) {
        Element nodeElement = document.createElement(tagName);

        nodeElement.setAttribute("transform", buildTransformString(node));

        if (node.getClip() != null) {
            Element clipPathElement = document.createElement("clipPath");

            clipPathElement.setAttribute("id", "clip" + ++clipPathCount);
            clipPathElement.appendChild(new GenericNodeConverter(document).convert(node.getClip()));
            
            nodeElement.setAttribute("clip-path", "url(#" + clipPathElement.getAttribute("id") + ")");
            nodeElement.appendChild(clipPathElement);
        }

        return nodeElement;
    }

    @Override
    public boolean canConvert(Node node) {
        return node.isVisible();
    }

    private static String buildTransformString(Node node) {
        List<String> transforms = new ArrayList<>(node.getTransforms().size());

        for (Transform transform : node.getTransforms()) {
            if (transform instanceof Translate) {
                Translate translate = (Translate) transform;
                transforms.add(String.format(TRANSLATE_FORMAT, translate.getX(), translate.getY()));
            } else if (transform instanceof Scale) {
                Scale scale = (Scale) transform;
                transforms.add(String.format(SCALE_FORMAT, scale.getX(), scale.getY()));
            } else if (transform instanceof Rotate) {
                Rotate rotate = (Rotate) transform;
                transforms.add(String.format(ROTATE_FORMAT, rotate.getAngle(), rotate.getPivotX(), rotate.getPivotY()));
            }
        }

        return String.join(" ", transforms);
    }

}
