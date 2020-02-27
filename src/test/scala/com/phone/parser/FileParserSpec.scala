package com.phone.parser

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.phone.model.Customer
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class FileParserSpec extends WordSpec with Matchers{

  implicit val sys: ActorSystem = ActorSystem(this.getClass.getSimpleName)
  implicit val mat: ActorMaterializer = ActorMaterializer()

  val parser =  new FileParser

  "FileParser" should {

    "return valid list of customer objects for calls1.log" in {

      val filePath = getClass.getResource("/calls1.log")

      Await.result(parser.parseFile[Customer](filePath, Customer.headers), Duration.Inf) shouldBe List(
        Customer("A", "555-333-212", 123),
        Customer("B", "555-333-212", 80)
      )
    }

    "return valid list of customer objects for calls2.log" in {

      val filePath = getClass.getResource("/calls2.log")

      Await.result(parser.parseFile[Customer](filePath, Customer.headers), Duration.Inf) shouldBe List(
        Customer("A", "555-333-212", 123),
        Customer("B", "555-334-789", 3),
        Customer("A", "555-663-111", 123)
      )
    }

    "throw exception if invalid file calls3.log" in {

      val filePath = getClass.getResource("/calls3.log")

      intercept[NoSuchElementException](
        Await.result(parser.parseFile[Customer](filePath, Customer.headers), Duration.Inf)
      )
    }
  }
}
