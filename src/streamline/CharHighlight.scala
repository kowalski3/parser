package streamline

import scala.xml._

/**
 * Character highlights class for LyricPageLine
 */
class CharHighlight (val character: String,
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
object CharHighlight{
  def createFromNode(highlight: Node):CharHighlight = {
       val  character = (highlight \\ "Character").text
       val time = (highlight \\ "Time").text
       val charType = (highlight \\ "Type").text
      
       
       new CharHighlight(character, time, charType)
  }
}