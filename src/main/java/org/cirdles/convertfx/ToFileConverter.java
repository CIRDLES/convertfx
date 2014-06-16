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
import javafx.scene.Node;

/**
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 * @param <T>
 */
public interface ToFileConverter<T> {
    public void convertToFile(T node, File file);
    public void convertToFile(T node, Path path);
}
