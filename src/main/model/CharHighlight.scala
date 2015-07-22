package main.model

import scala.xml._

/**
 * Character highlights class for LyricPageLine
 */
class Character (val character: String,
                     val time: String,
                     val empty: String){
  
  def getCharHighlightVal:String = {
    
    "   \n _____________\n\n"+ 
    "    " + character + "\n"+
    "    " + time + "\n" +
    "    " + empty + "\n"
  }
  
}
 
                     
//Companion object for CharHighLight
object Character{
  def createFromNode(highlight: Node):Character = {
       val  character = (highlight \\ "Character").text
       val time = (highlight \\ "Time").text
       val charType = (highlight \\ "Type").text
       new Character(character, time, charType)
  }
}