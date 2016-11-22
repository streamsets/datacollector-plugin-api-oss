<!---
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
--->

#Streamsets Spark Transformer

This API can be used in combination with the `Spark Evaluator`
in Streamsets Data Collector to apply arbitrary transformations
to a batch of Records using Spark.

An implementation of the `com.streamsets.pipeline.spark.api.SparkTransformer` should
be inserted as an additional library in the SDC's classpath using
the method described [here](https://streamsets.com/documentation/datacollector/latest/help/Processors/Spark.html#task_dr2_gvd_1y)
The Spark Processor is available in several stage libraries, and this
jar should be in the correct library for the specific stage library being used.

Once you have installed the library, start the SDC with the Spark Processor
and specify the Fully Qualified Class Name of the class that implements
`SparkTransformer` class.