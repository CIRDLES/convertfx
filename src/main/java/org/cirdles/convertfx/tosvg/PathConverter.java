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
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
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
        StringBuilder pathData = new StringBuilder();
        
        for (PathElement pathElement : path.getElements()) {
            if (pathElement instanceof MoveTo) {
                MoveTo moveTo = (MoveTo) pathElement;
                
                
            }
        }
        
        return pathData.toString();
    }
    
}
