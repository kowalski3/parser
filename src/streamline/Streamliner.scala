package streamline

import scala.xml._
import scala.collection.mutable.ListBuffer

/*
 * https://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/
 */

class Streamliner(fileName:String) {
  
  //val pageList = List(1,2,3)
  
  
  
  val file = scala.xml.XML.loadFile(fileName) 
  
  
 def printXML = (file \\ "Page").foreach { Page => parse(Page)}
  
  
  def parsePages = (file \\ "Page").foldLeft(List[SfPage]()) { (pages, pageNode) => 
    pages :+ parse(pageNode)
  }


  
  //def printXML = (file \\ "Page").foreach { Page => parse(Page)}

  
  def parse(node: Node):SfPage = {
   // println(node.isEmpty)
    val pageType = (node \ "Type").text
   
     pageType match{
      case "Title"       =>  val titleValues = getTitleValues(node)
//                             return new BasicPage(pageType,
//                                                  titleValues._1,
//                                                  titleValues._2
//                                                  )
                             
      case "Instruction" =>  val instructionPageValues = getInstructionValues(node)
//                             return new BasicPage(instructionPageValues._1,
//                                                     instructionPageValues._2,
//                                                     instructionPageValues._3)
//                                                     instructionPageValues._4)
      
      case "Lyrics"      =>  val lyricValues = getLyricValues(node);
//                             return new BasicPage    (lyricValues._1,
//                                                     lyricValues._2,
//                                                     lyricValues._3)
//                                                     lyricValues._4)
                       
      case _ => println("Dont recognise this type") 
    }
    
    return new BasicPage("1","2","3")
    
  }

  
  /**
   *  GET PAGE VALUES
   */
  
 def getTitleValues(pageNode: Node):(String,String)= {
 
   val startTime = (pageNode \\"StartTime").text
   val endTime = (pageNode \\"EndTime").text  
   
   (startTime,
    endTime
    )
 }

 
 def getInstructionValues(pageNode:Node):(String,String,String) = {
   val startTime = (pageNode \\"StartTime").text
   val endTime = (pageNode \\"EndTime").text  
   val instructionLines: ListBuffer[String] = ListBuffer()
   
   (pageNode \\ "Line").foreach {  Line => instructionLines += Line.text }
  
   
   ("","","")
 }
 
 
 
 def getLyricValues(pageNode: Node):(String,String,String, List[(String, String,String)])= {
   
  
   val startTime = (pageNode \\"StartTime").text
   val endTime = (pageNode \\"EndTime").text
   val lyric = (pageNode \\"Text").text
   val charPositions : ListBuffer[(String, String,String)] = ListBuffer()
     
   (pageNode \\ "Highlight").foreach {  Highlight => charPositions += getHighlightValues(Highlight)}
    
       def getHighlightValues(hlNode: Node):(String,String,String) = {
         val character = (hlNode \\ "Character").text
         val time = (hlNode \\ "Time").text
         val charType = (hlNode \\ "Type").text
         (character,time,charType)
       }
    
   
   (startTime, endTime, lyric, charPositions.toList)
   
  }
 
 
 
}





object Test extends App {
   
  val sfParser = new Streamliner("C:/Julian/git/parser/data/sfXmlSample1.xml")
  val x = sfParser.parsePages
  
  //x.foreach { x => println(x.pageValues) }
    
}

/*
def printXML = (file \ "Pages").foreach { Pages =>
   //  println("Pages")
     (Pages \ "Page").foreach { Page => parse(Page)
     
       (Page \ "Paragraphs").foreach { Paragraphs =>  
       //  println("    <PARAGRAPHS>") 
         (Paragraphs \ "Block").foreach { Block =>  
         //  println("      <BLOCK>")
           (Block \ "Lines").foreach { Lines =>  
           //  println("      <LINES>")
             (Lines \ "Line").foreach { Line =>  
              // println("        <LINE>")
               (Line \ "Text").foreach { Text => 
              //  println("           <TEXT>" + Text.text + "</TEXT>")
               }
             }
           }
         }
       }
     }
   }
*/