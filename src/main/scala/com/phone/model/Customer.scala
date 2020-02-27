package com.phone.model

import com.phone.MapConvert

case class Customer(customerId:String, phoneNumber:String, callDuration:Int)

object Customer{

  def parseSeconds(timeString : String): Int = {
    timeString.split(":").map(_.toInt).reduceLeft((x,y) => x*60 +y)
  }

  val headers: List[String] = List("customer id","phone number called","call duration")

  implicit val mapper: MapConvert[Customer] = (values: Map[String, String]) => {
    Customer(
      values("customer id").toString,
      values("phone number called").toString,
      parseSeconds(values("call duration").toString)
    )
  }
}
