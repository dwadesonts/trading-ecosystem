package com.cowsunday.sparkdataanalysis.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.rdd.RDDFunctions._
import com.cowsunday.sparkdataanalysis.data.PriceBar

object SmallestDownBarInSeries extends SlidingTransform {

  def transform(priceData: RDD[PriceBar], length: Integer): RDD[Integer] = {
    priceData.sliding(length).map(currentSlice => {
      if (currentSlice.exists { x => x.isUp || x.isSideways }) {
        0
      } else {
        val lastBar = currentSlice.takeRight(1)(0)
        val smallestBar = currentSlice.sortBy(_.getOpenCloseRange).take(1)(0)
      
        if (lastBar == smallestBar) 1 else 0  
      }
      
    });
  }
}