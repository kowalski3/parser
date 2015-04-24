package streamline

import scala.xml._
import scala.collection.mutable.ListBuffer

/*
 * https://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/
 */

class Streamliner(fileName:String) {

 val file = scala.xml.XML.loadFile(fileName) 
  
 def printXML = (file \\ "Page").foreach { Page => parse(Page)}
  
 def parsePages = (file \\ "Page").foldLeft(List[SfPage]()) { (pages, pageNode) => 
    pages :+ parse(pageNode)
  }


 
  def parse(node: Node):SfPage = {
    val pageType = (node \ "Type").text
   
     pageType match{
      case "Title"       =>  val titleValues = getTitleValues(node)
                             return new BasicPage(pageType,
                                                  titleValues._1,
                                                  titleValues._2
                                                  )
                             
      case "Instruction" =>  val instructionPageValues = getInstructionValues(node)
                             return new InstructionPage( pageType, 
                                                         instructionPageValues._1,
                                                         instructionPageValues._2,
                                                         instructionPageValues._3)
      
      case "Lyrics"      =>  val lyricPageValues = getLyricValues(node);
                             return new LyricPage    ( pageType,
                                                       lyricPageValues._1,
                                                       lyricPageValues._2,
                                                       lyricPageValues._3)
      case _ => println("Dont recognise this type") 
    }
    
    //HANDLE THIS BETTER DONT RETURN NULL
    return null
  
    
  }

  
  /**
   *  GET PAGE VALUES
   */
  
 def getTitleValues(pageNode: Node):(String,String)= {
   val startTime = (pageNode \\"StartTime").text
   val endTime = (pageNode \\"EndTime").text   
   (startTime, endTime )
 }

 
 
 def getInstructionValues(pageNode:Node):(String,String,List[Line]) = {
   val startTime = (pageNode \\"StartTime").text
   val endTime = (pageNode \\"EndTime").text  
   val instructionLines: ListBuffer[Line] = ListBuffer()
   
    (pageNode \\ "Line").foreach { Line => 
        (Line \\ "Text").foreach  { Text => instructionLines += new StaticPageLine(Text.text) }
     }
   
   (startTime,endTime,instructionLines.toList) 
 }
 
 
 
 
 def getLyricValues(pageNode: Node):(String, String, List[Line])= {
  
   val startTime = (pageNode \\"StartTime").text
   val endTime = (pageNode \\"EndTime").text
   val lyricLines: ListBuffer[Line] = ListBuffer() 
   
   
   (pageNode \\ "Line").foreach { Line => val lyric =  (Line \\ "Text").text
     (Line \\"Highlights").foreach { Highlights => lyricLines += new LyricPageLine(lyric,Highlights) }
        
     }
      
         
    (startTime,endTime,lyricLines.toList) 
  }
 
 
 
}





object Test extends App {
   
  val sfParser = new Streamliner("C:/Julian/git/parser/data/sfXmlSample1.xml")
  val x = sfParser.parsePages
  
  x.foreach { x => println(x.pageValues) }
    
}
