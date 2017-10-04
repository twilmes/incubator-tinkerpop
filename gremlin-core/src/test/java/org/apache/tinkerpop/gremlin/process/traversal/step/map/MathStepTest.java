/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.tinkerpop.gremlin.process.traversal.step.map;

import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.step.StepTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.both;
import static org.junit.Assert.assertEquals;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class MathStepTest extends StepTest {

    @Override
    protected List<Traversal> getTraversals() {
        return Arrays.asList(
                __.math("a+b"),
                __.math("a+b").by("age"),
                __.math("a/b").by("age"),
                __.math("a+b").by("age").by(both().count()),
                __.math("sin a + b")
        );
    }

    @Test
    public void shouldParseVariablesCorrectly() {
        assertEquals(Arrays.asList("a", "b"), new ArrayList<>(MathStep.getVariables("a + b / 2")));
        assertEquals(Arrays.asList("a", "b"), new ArrayList<>(MathStep.getVariables("a + b / sin 2")));
        assertEquals(Arrays.asList("a", "b", "_", "x", "z"), new ArrayList<>(MathStep.getVariables("(a + b / _) + log2 (x^3)^z")));
        assertEquals(Arrays.asList("a", "b", "_", "x", "z"), new ArrayList<>(MathStep.getVariables("(a + b / _) + log2 (x^3)^z + b + a")));
    }

}