package test
import main.model._
import java.io._
import scala.collection.mutable.Queue

import org.scalatest._

import org.scalatest.BeforeAndAfter
import scala.xml._

class LineLyricPageTest extends FlatSpec with Matchers with BeforeAndAfter {
  val file = scala.xml.XML.loadFile("C:/Julian/git/parser/data/testBroken.xml")
  val line = "I went to a party"
  var testClass: LineLyricPage = new LineLyricPage(line, "0", file)
  
//  var LyricPage: Node = _
//  var testLine1: LyricPageLine = _
  
  before{
   
   
    
  }
  
  "The something" should "blah blah" in {
    
    var ints = Queue[Int]()
    println("------") 
   // println(testClass.getLineindexes(line))
//    println(testClass.getLineindexesUngrouped(line))
//    println(testClass.getTimePlusChar) 
//    println(testClass.getTimeFromCharacters) 
//    println(testClass.getTimeFromCharacters2) 
//    println(testClass.getLineindexes(line))
//    println(testClass.getIndexFromCharacters)
////   println(testClass.getTimePlusChar)
    println(testClass.getLines)
   println(testClass.getFixedLines)

    
  } 
  
  
}