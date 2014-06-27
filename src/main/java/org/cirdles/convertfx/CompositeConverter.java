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
package org.cirdles.convertfx;

import java.util.Set;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 * @param <X>
 */
public class CompositeConverter<X, Y> implements Converter<X, Y> {

    private final Set<Converter<X, Y>> constituents;

    public CompositeConverter(Set<Converter<X, Y>> constituents) {
        this.constituents = constituents;
    }

    @Override
    public Y convert(X node) {
        for (Converter<X, Y> constituent : constituents) {
            if (constituent.canConvert(node)) {
                return constituent.convert(node);
            }
        }
        
        throw new IllegalArgumentException("Can't convert " + node.getClass().getSimpleName() + ".");
    }

    @Override
    public boolean canConvert(X node) {
        for (Converter<X, Y> constituent : constituents) {
            if (constituent.canConvert(node)) {
                return true;
            }
        }

        return false;
    }

}
