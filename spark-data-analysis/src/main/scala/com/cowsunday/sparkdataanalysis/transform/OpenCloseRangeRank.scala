package com.cowsunday.sparkdataanalysis.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.rdd.RDDFunctions._
import com.cowsunday.sparkdataanalysis.data.PriceBar

object OpenCloseRangeRank {

  /**
   * Ranks the last length bars by the absolute value of (close - open)
   * 
   * Smallest values (closest to 0) have the lowest rank (lowest rank is 0).
   * Largest values (farthest from 0) have the highest rank (highest rank is length-1)
   */
  def transform(priceData: RDD[PriceBar], length: Integer): RDD[Integer] = {
    priceData.sliding(length).map(currentSlice => {
      val lastBar = currentSlice.takeRight(1)(0)
      currentSlice.sortBy(_.getOpenCloseRange).indexOf(lastBar)
    })
  }
}