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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author zeringue
 */
public class CompositeConverterTest {

    private IntegerIdentityConverter oneConverter;

    private CompositeConverter<Integer, Integer> converter;
    private CompositeConverter<Integer, Integer> emptyConverter;

    @Before
    public void setUp() {
        oneConverter = new IntegerIdentityConverter(1);

        Set<Converter<Integer, Integer>> constituents = new HashSet<>(
                Arrays.asList(oneConverter,
                              new IntegerIdentityConverter(2),
                              new IntegerIdentityConverter(3)));

        converter = new CompositeConverter<>(constituents);
        emptyConverter = new CompositeConverter<>(new HashSet<>());
    }

    /**
     * Test of convert method, of class CompositeConverter.
     */
    @Test
    public void testConvert() {
        for (int i = -100; i <= 100; i++) {
            try {
                if (!converter.canConvert(i)) {
                    converter.convert(0);
                    fail("Should not be able to convert unconvertable value");
                }
            } catch (IllegalArgumentException ex) {
                assertEquals(ex.getMessage(), "Can't convert Integer.");
            }
        }

        Integer testInteger1a = new Integer(1);
        Integer testInteger1b = new Integer(1);
        Integer testInteger2 = new Integer(2);
        
        List<Integer> testIntegers = Arrays.asList(testInteger1a, testInteger1b, testInteger2);
        
        assertFalse(testIntegers.stream().anyMatch(x -> oneConverter.hasConverted(x)));
        
        converter.convert(testInteger1a);
        
        assertTrue(oneConverter.hasConverted(testInteger1a));
        assertFalse(oneConverter.hasConverted(testInteger1b));
        
        converter.convert(testInteger2);
        
        assertFalse(oneConverter.hasConverted(testInteger2));
    }

    /**
     * Test of canConvert method, of class CompositeConverter.
     */
    @Test
    public void testCanConvert() {
        assertFalse(converter.canConvert(-1));
        assertFalse(converter.canConvert(0));

        assertTrue(converter.canConvert(1));
        assertTrue(converter.canConvert(2));
        assertTrue(converter.canConvert(3));

        assertFalse(converter.canConvert(4));
        assertFalse(converter.canConvert(5));

        for (int i = -100; i <= 100; i++) {
            assertFalse(emptyConverter.canConvert(i));
        }
    }

    /**
     * A simple converter that only converts <code>Integer</code>s of its own value by returning a new
     * <code>Integer</code> with the same value.
     */
    private static class IntegerIdentityConverter implements Converter<Integer, Integer> {

        private final Integer value;
        private final List<Integer> converted;

        public IntegerIdentityConverter(Integer value) {
            this.value = value;
            converted = new ArrayList<>();
        }

        @Override
        public Integer convert(Integer target) {
            converted.add(target);
            return new Integer(target);
        }

        @Override
        public boolean canConvert(Integer target) {
            return value.equals(target);
        }

        public boolean hasConverted(Integer target) {
            return converted.stream().anyMatch(x -> x == target);
        }

    }

}
