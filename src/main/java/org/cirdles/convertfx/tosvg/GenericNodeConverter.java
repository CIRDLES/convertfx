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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.Node;
import org.cirdles.convertfx.CompositeConverter;
import org.cirdles.convertfx.Converter;
import org.cirdles.convertfx.FXConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
class GenericNodeConverter extends CompositeConverter<Node, Element> implements FXConverter<Element> {

    private static final Map<Document, Set<Converter<Node, Element>>> constituentsCache = new HashMap<>();

    GenericNodeConverter(Document document) {
        super(getConstituentsForDocument(document));
    }

    private static Set<Converter<Node, Element>> getConstituentsForDocument(Document document) {
        if (!constituentsCache.containsKey(document)) {
            // generate new constituents set
            constituentsCache.put(document, new HashSet<>(Arrays.asList(
                                  new LineConverter(document)
                          )));
        }

        return constituentsCache.get(document);
    }

}
