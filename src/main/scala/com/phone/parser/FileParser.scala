package com.phone.parser

import java.net.URL

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.alpakka.csv.scaladsl.CsvParsing.lineScanner
import akka.stream.alpakka.csv.scaladsl.CsvToMap.withHeaders
import akka.stream.scaladsl.{Sink, StreamConverters}
import com.google.inject.Inject
import com.phone.{MapConvert, _}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FileParser@Inject()(implicit system: ActorSystem, mat: Materializer){

  def parseFile[A](filePath:URL, headers:List[String])(implicit mapper: MapConvert[A]): Future[List[A]] = {

    StreamConverters.fromInputStream(()=> filePath.openStream())
      .via(lineScanner(delimiter = ' '))
      .map(_.filter(_.utf8String.trim.nonEmpty)).filter(_.nonEmpty)
      .via(withHeaders(headers:_*))
      .map(_.mapValues(_.utf8String)).runWith(Sink.seq)
      .map(_.map(_.convert[A]).toList).recover {
      case e =>
        println(s"${mapper.toString} for file $filePath failed to parse : $e")
        throw e
    }
  }

}
