/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wayang.api.serialization.mixins

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.{JsonAutoDetect, JsonIdentityInfo, JsonIgnore, JsonSubTypes, JsonTypeInfo, ObjectIdGenerators}
import org.apache.wayang.core.plan.wayangplan.{InputSlot, OutputSlot}

import java.util.List

object SlotMixIns {

  @JsonIdentityInfo(generator = classOf[ObjectIdGenerators.IntSequenceGenerator], property = "@id")
  @JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
  @JsonSubTypes(Array(
    new JsonSubTypes.Type(value = classOf[InputSlot[_]], name = "InputSlot"),
    new JsonSubTypes.Type(value = classOf[OutputSlot[_]], name = "OutputSlot"),
  ))
  abstract class SlotMixIn[T] {

  }

  abstract class OutputSlotMixIn[T] {
    @JsonIgnore
    private var occupiedSlots: List[InputSlot[T]] = _
  }

}