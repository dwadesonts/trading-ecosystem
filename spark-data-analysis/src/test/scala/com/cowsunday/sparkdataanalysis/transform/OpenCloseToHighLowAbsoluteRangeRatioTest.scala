package com.cowsunday.sparkdataanalysis.transform

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.junit.Test
import org.junit.Before
import org.junit.After
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.specs2.matcher.{ Expectable, Matcher }

import com.cowsunday.sparkdataanalysis.data.PriceBar

class OpenCloseToHighLowAbsoluteRangeRatioTest extends Specification {

  private val master = "local[2]"
  private val appName = "OpenCloseToHighLowAbsoluteRangeRatio-test"
  private val sparkHome = "/usr/local/spark-1.4.1"
  private val jars = Array[String]("/Users/dwadeson/trading-ecosystem/spark-data-analysis/target/spark-data-analysis-0.0.1-SNAPSHOT.jar")

  private var sc: SparkContext = _

  @Before def setup() {
    val conf = new SparkConf()
                  .setMaster(master)
                  .setAppName(appName)
                  .setSparkHome(sparkHome)
                  .setJars(jars)

    sc = new SparkContext(conf)
  }

  @After def after() {
    if (sc != null) {
      sc.stop()
    }
  }

  @Test def testTransform() {
    val priceBars = Seq(new PriceBar(4,2,0.5,3,20150101),
        new PriceBar(4,4,1.5,2.5,20150102),
        new PriceBar(3,6,2.5,4,20150103),
        new PriceBar(4,7,3.5,10,20150104),
        new PriceBar(6,8,4.5,5.5,20150105))
    val rdd = sc.parallelize(priceBars)

    val results = OpenCloseToHighLowAbsoluteRangeRatio.transform(rdd).take(5)

//    results(0) should be (1/1.5 +- 0.0001)
//    results(1) should be (1.5/2.5 +- 0.0001)
//    results(2) should be (1/3.5 +- 0.0001)
//    results(3) should be (6/3.5 +- 0.0001)
//    results(4) should be (0.5/3.5 +- 0.0001)

  }

}