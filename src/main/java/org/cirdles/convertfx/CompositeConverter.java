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

    /**
     * Attempts to convert the target object by iterating through this converter's constituents and delegating the task to the
     * first that is able to convert the target.
     *
     * @param target the target object
     * @return the result
     * @throws IllegalArgumentException if no constituents can perform the conversion
     */
    @Override
    public Y convert(X target) throws IllegalArgumentException {
        for (Converter<X, Y> constituent : constituents) {
            if (constituent.canConvert(target)) {
                return constituent.convert(target);
            }
        }

        throw new IllegalArgumentException("Can't convert " + target.getClass().getSimpleName() + ".");
    }

    /**
     * Determines whether or not any of this converter's constituents are able to convert the target object.
     * 
     * @param target the target object
     * @return if the target can be converted
     */
    @Override
    public boolean canConvert(X target) {
        return constituents.stream().anyMatch(constituent -> constituent.canConvert(target));
    }

}
