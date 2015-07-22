package main

import main.controller._
/**
 * @author julian
 */
object ParserRunner extends App {
  
  val src = "C:/Julian/Streamliner/in"
  val dst = "C:/Julian/Streamliner/out"
  val myParser = new ParserController(src, dst)
  
  //error checking
//  val xmlErrors = myParser.validateFiles
//  myParser.writeToFile(dst + "/" + "xmlErrors.txt", xmlErrors)
  
  //end of error checking
  
  myParser.processDirectory 
  myParser.convertedTracksToFile
    
  val broken = myParser.getBrokenTracks
  val brokenFiles = broken.foldLeft(new StringBuffer)( (sb, s) => sb.append(s.getFileName + "\n"))
  println(brokenFiles)
  
  myParser.writeToFile(dst + "/" + "brokenTrack.txt", brokenFiles.toString())
   
}