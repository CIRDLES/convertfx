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

import java.io.File;
import java.nio.file.Path;

/**
 * Converts objects of a type into a file of a particular format.
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 * @param <X> the type of object to convert
 */
public interface ToFileConverter<X> {

    /**
     * Converts the target object into a new file at the location specified by the <code>File</code> object.
     *
     * @param target the target object
     * @param file the location at which to produce the new file
     */
    public void convertToFile(X target, File file);

    /**
     * Converts the target object into a new file at the location specified by the <code>Path</code> object.
     *
     * @param target the target object
     * @param path the location at which to produce the new file
     */
    public void convertToFile(X target, Path path);

    /**
     * Determines whether or not this converter is able to convert the target object.
     *
     * @param target the target object
     * @return whether or not the target is convertable by this converter
     */
    public boolean canConvert(X target);

}
