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
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

/**
 * Base class for processing data using the Streamsets Data Collector
 * Spark Evaluator. Any implementations of this class must have a
 * no-args constructor. Any configuration can be passed in via the
 * {@linkplain SparkTransformer#init(JavaSparkContext, List)} method.
 */
public abstract class SparkTransformer {

  /**
   * This method is called exactly once each time the pipeline is started.
   * This can be used to make external connections or read configuration or
   * pre-existing data from external systems.
   *
   * This method will be called before the {@linkplain #transform(JavaRDD)} or the {@linkplain #destroy()}
   * methods are called.
   *
   * @param context The JavaSparkContext that will be used to create RDDs in this
   *                run of the pipeline.
   * @param parameters List of parameters passed in via the Spark Evaluator
   */
  public void init(JavaSparkContext context, List<String> parameters) {
  }

  /**
   * This method is called once per batch. Each batch is passed in as partitioned RDD.
   * Arbitrary operations can be applied to this RDD.
   *
   * Note that all methods that force all data to be sent back to the driver may be
   * disabled and will throw an {@linkplain UnsupportedOperationException}, hence should
   * not be used. This includes all variants of {@linkplain JavaRDD#collect()} and
   * {@linkplain JavaRDD#take(int)}.
   *
   * @param recordRDD RDD representing data in the current SDC batch.
   * @return {@linkplain TransformResult} returned by processing the {@param recordRDD}.
   */
  public abstract TransformResult transform(JavaRDD<Record> recordRDD);

  /**
   * Destroy this instance of the transformer. This should be used to clean up any external connections etc. set
   * up in {@linkplain #init(JavaSparkContext, List)}.
   *
   * This method should be idempotent and should be able
   * to handle multiple calls, but it is guaranteed that neither {@linkplain #init(JavaSparkContext, List)}
   * nor {@linkplain #transform(JavaRDD)} will be called after this method has been called, until the pipeline
   * is restarted.
   */
  public void destroy() {
  }

}
