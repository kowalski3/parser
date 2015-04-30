package streamline

import scala.xml._
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import java.io._
import scala.io._
/*
 * https://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/
 */

class Streamliner(srcDirectoryName:String,
                  destDirectoryName:String) {

 val trackMap = scala.collection.mutable.Map[String,Track]()
 val sourceDirectory = new File(srcDirectoryName)
 val destDirectory = new File(destDirectoryName)

 //TO DO - make part of constructor
 def processDirectory = {   
   for(file <- sourceDirectory.listFiles if file.getName endsWith ".xml"){
     val key = file.getName.substring(0,file.getName.indexOf(" "))
     val value = parsePages(file.toString(), file.getPath)
     trackMap.put(key, value)
     println("processed: " + key.toString())
  }   
  
 }
  
 /**
  * Checks if indexes of characters in XML match the spacing of the lines in each lyric line
  * @returns a list of tracks that fail this test
  */
 def getBrokenTracks:List[Track] = {
   val brokenTracks: ListBuffer[Track] = ListBuffer() 
   
   for ((key, value) <- trackMap) {
     val trackOk = value.lyricPagesOk
 
     trackOk match {
       case Some(false) => println(key.toString() + " has errors")
                           brokenTracks.add(value)
       case Some(true) =>  println(key.toString() + " is error-free")
       case None =>
     }
   }
   
   brokenTracks.toList
 }
 
 
 /**
  * Saves converted track to file
  */
 def convertedTracksToFile = {
   
  
   for ((key, value) <- trackMap){
     
     //write(destDirectoryName + '\' + key.toString(), "")
     
     val path = destDirectoryName + "/" + key + ".txt"
     val text = value.getTrackData
     write(path, text)
     
   }
   
   def write(path: String, txt: String): Unit = {
     val pw = new PrintWriter(new File(path ))
     pw.write(txt)
     pw.close
     
    }
     
   
   
 }
 
 
  
 def parsePages(absfileName: String, relFileName:String):Track = { 
   val file = scala.xml.XML.loadFile(absfileName)
   
   val pageList = (file \\ "Page").foldLeft(List[SfPage]()) { (pages, pageNode) => 
      pages :+ parsePage(pageNode)
   }  
   new Track(relFileName,pageList)
 }


  def parsePage(node: Node):SfPage = {
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
   
  val myParser = new Streamliner("C:/Users/Julian.SUNFLYKARAOKE/Desktop/xmlCheck",
                                  "C:/Users/Julian.SUNFLYKARAOKE/Desktop/fileOutput"                                                )
  myParser.processDirectory
  myParser.convertedTracksToFile
  
  
  
  val broken = myParser.getBrokenTracks
  println(broken.length)
  broken.foreach { x => println(x.getFileName) }
   
}
