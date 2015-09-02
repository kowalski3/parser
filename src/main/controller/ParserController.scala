package main.controller

import main.model._

import scala.xml._
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import java.io._
import main.view.ParserUI

/*
 * https://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/
 */

class ParserController(srcDirectoryName:String,
                  destDirectoryName:String) {

 val trackMap = scala.collection.mutable.Map[String,Track]()
 val sourceDirectory = new File(srcDirectoryName)
 val destDirectory = new File(destDirectoryName)
 var view: ParserUI = null
 
 
 def this(srcDirectoryName:String, destDirectoryName:String, view: ParserUI){
   this(srcDirectoryName, destDirectoryName)
   this.view = view
 }
 
 //TO DO - make part of constructor
 def processDirectory = {   
   for(file <- sourceDirectory.listFiles if file.getName endsWith ".xml"){
     val key = file.getName.substring(0,file.getName.indexOf(" "))
     this.view.setTheText("processing: " + key.toString())
     //println("processing: " + key.toString())
     val value = parsePages(file.toString(), file.getPath)
     trackMap.put(key, value)
     this.view.setTheText("processed:  " + key.toString() +"\n")
     //println("processed:  " + key.toString() +"\n")
  }   
  
 }
 
 
  def validateFiles():String  = {
   var errors = ""
   for(file <- sourceDirectory.listFiles if file.getName endsWith ".xml"){
      try{
       println(file.getName)
        scala.xml.XML.loadFile(file.getPath) 
      } catch {
        case a: SAXParseException => errors += file.getName + " ---> SAXParseException"
        case b: com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException => errors += file.getName + " ---> MalformedByteSequenceException"
      }
   }
    errors   
 }
 
  
 /**
  * Checks if indexes of characters in XML match the spacing of the lines in each lyric line
  * @returns a list of tracks that fail this test
  */
 def getBrokenTracks:List[Track] = {
   val brokenTracks: ListBuffer[Track] = ListBuffer() 
   
   for ((key, value) <- trackMap) {
    
     value.trackOk match {
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
     var fileName = ""
     println(key + " being written to file")
     value.trackOk match {
       case Some(true) => fileName = key + ".txt"
       //case Some(false) =>  fileName = "ERROR_" + key + ".txt"
       case Some(false) =>  fileName = key + ".txt"
       case None => //throw exception
     }
          
     value.trackOk
     val path = destDirectoryName + "/" + fileName
     val text = value.getTrackData
     writeToFile(path, text)
     println(key + " written to file")
   }   
 }
  


 
 
 
 def writeToFile(path: String, txt: String): Unit = {
     val pw = new PrintWriter(new File(path ))
     pw.write(txt)
     pw.close   
    }
 
  
 
 def parsePages(absfileName: String, relativeFileName:String):Track = { 
   val file = scala.xml.XML.loadFile(absfileName)
   
   val pageList = (file \\ "Page").foldLeft(List[Page]()) { (pages, pageNode) => 
      pages :+ parsePage(pageNode)
   } 
   
   val artist = (file \\ "Artist").text
   val title = (file \\ "Title").text
   val copyright = (file \\ "Copyright").text
   val writers = (file \\ "Writers").text
   
   new Track(relativeFileName, artist, title, copyright, writers, pageList)
   
 }


 
  def parsePage(node: Node):Page = {
    val pageType = (node \ "Type").text
   
     pageType match{
      case "Title"       =>  val titleValues = getTitleValues(node)
                             return new PageBasic(pageType,        //pageType
                                                  titleValues._1,  //start time
                                                  titleValues._2   //end time
                                                  )
                             
      case "Instruction" =>  val instructionPageValues = getInstructionValues(node)
                             return new PageInstruction( pageType,                 // pageType
                                                         instructionPageValues._1, // start time
                                                         instructionPageValues._2, // end time
                                                         instructionPageValues._3) // lyric list
      
      case "Lyrics"      =>  val lyricPageValues = getLyricValues(node);
                             return PageLyric( pageType,           // pageType
                                               lyricPageValues._1, // start time
                                               lyricPageValues._2, // end time
                                               lyricPageValues._3) // lyrics list
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
   
    (pageNode \\ "Line").foreach { Line =>  val voice =  (Line \\ "Voice").text
        (Line \\ "Text").foreach  { Text => instructionLines += new LineStaticPage(Text.text, voice) }
     }
   
   (startTime,endTime,instructionLines.toList) 
 }
 
 
 
 def getLyricValues(pageNode: Node):(String, String, List[Line])= {
   val startTime = (pageNode \\"StartTime").text
   val endTime = (pageNode \\"EndTime").text
   val lyricLines: ListBuffer[Line] = ListBuffer() 
    
   (pageNode \\ "Line").foreach { Line => val voice =  (Line \\ "Voice").text
                                          val lyric =  (Line \\ "Text").text
     (Line \\"Highlights").foreach { Highlights => lyricLines += new LineLyricPage(lyric,voice,Highlights) }     
     }
             
    (startTime,endTime,lyricLines.toList) 
  } 
 
 
}
