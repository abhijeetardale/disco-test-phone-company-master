package com

package object phone {

  import scala.language.implicitConversions

  implicit class Map2Class[A](values: Map[String,String]){
    def headersToArgs(headers: List[String]) = List()
    def convert[A](implicit mapper: MapConvert[A]): A = mapper conv values
  }

  trait MapConvert[A]{
    def conv(values: Map[String,String]): A
  }
}
