package com.cowsunday.sparkdataanalysis.data

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.scalatest.Assertions
import org.junit.Test
import org.junit.Before
import org.junit.After

class RddPriceDataLoaderTest extends Assertions {

  private val master = "local[2]"
  private val appName = "rdd-pricedataloader-test"
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
  
  @Test def testPriceDataLoading() {
    val rdd = RddPriceDataLoader.loadPriceData(sc, "src/test/resources/test_pricedata/candy_bars.txt")
    
    var date = 20150101
    var open = 1.0
    var high = 2.0
    var low = 0.5
    var close = 1.5
    
    val priceBars = rdd.take(8)
    
    for (i <- 0 until 8) {
      println(priceBars(i))
      
      assert(priceBars(i).getDate === date)
      assert(priceBars(i).getOpen === open)
      assert(priceBars(i).getHigh === high)
      assert(priceBars(i).getLow === low)
      assert(priceBars(i).getClose === close)
      
      date+=1
      open+=1
      high+=1
      low+=1
      close+=1
    }
  }
}