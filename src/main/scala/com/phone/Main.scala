package com.phone

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.phone.model.Customer
import com.phone.parser.FileParser
import com.phone.service.BillingService

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  implicit val sys: ActorSystem = ActorSystem("Test")
  implicit val mat: ActorMaterializer = ActorMaterializer()

  val parser = new FileParser
  val service = new BillingService
  val filePath = getClass.getResource("/calls.log")

  parser.parseFile[Customer](filePath, Customer.headers).map{ parseResult =>
    service.generateBill(parseResult, promotionOn = true).map{ generatedBill =>
      println(generatedBill)
    }
  }

}
