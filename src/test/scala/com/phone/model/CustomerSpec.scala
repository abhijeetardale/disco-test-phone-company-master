package com.phone.model

import com.phone._
import org.scalatest.{Matchers, WordSpec}

class CustomerSpec extends WordSpec with Matchers{

  val customer: Customer = Customer("A", "555-333-212", 123)

  val customerMap = Map("customer id" -> "A", "phone number called" -> "555-333-212", "call duration" -> "00:02:03")

  "Customer converter" should {

    "return correct Customer object from map" in {

      customerMap.convert[Customer] shouldBe customer
    }

    "throw runtime exception if its not able to find correct mapping" in {

      val customerMap = Map("customer id" -> "A", "call duration" -> "00:02:03")

      intercept[NoSuchElementException]{
        customerMap.convert[Customer]
      }
    }
  }

  "parseSeconds" should{

    "return 123 seconds for 00:02:03" in {
      Customer.parseSeconds("00:02:03") shouldBe 123
    }

    "return 0 seconds for 00:00:00" in {
      Customer.parseSeconds("00:00:00") shouldBe 0
    }
  }
}
