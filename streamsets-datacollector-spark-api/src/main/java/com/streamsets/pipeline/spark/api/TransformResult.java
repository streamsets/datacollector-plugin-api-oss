/**
 * Copyright 2016 StreamSets Inc.
 * <p>
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.spark.api;

import com.streamsets.pipeline.api.Record;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFlatMapFunction;

public class TransformResult {

  private final JavaRDD<Record> result;
  private final JavaPairRDD<Record, String> errors;

  public TransformResult(JavaRDD<Record> result, JavaPairRDD<Record, String> errors) {
    this.result = result;
    this.errors = errors;
  }

  /**
   * Return the result of the processing done via {@linkplain SparkTransformer}.
   * The {@linkplain Record}s from this RDD are passed to the next stage in the pipeline.
   * @return The RDD containing the records that were successfully processed.
   *
   * Ideally, Records that are added to error should not appear here, but there is no restriction imposed.
   * Exceptions should not be thrown for bad records, they should instead be returned via the
   * {@linkplain #getErrors()} method.
   */
  public JavaRDD<Record> getResult() {
    return result;
  }

  /**
   * Get the {@linkplain Record}s that were not processed successfully and their corresponding error messages.
   * These records will be written to the error stream for the pipeline and not passed
   * to the next stage.
   *
   * A good way to calculate errors is to use
   * {@linkplain org.apache.spark.api.java.JavaRDD#mapPartitionsToPair(PairFlatMapFunction)} and generate the
   * error records and their messages into an RDD.
   * @return A Pair RDD containing the error records and corresponding error messages.
   */
  public JavaPairRDD<Record, String> getErrors() {
    return errors;
  }

}
