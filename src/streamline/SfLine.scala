package streamline
import scala.xml._
import scala.collection.mutable.ListBuffer


/**
 * Line trait
 */
trait Line {
  
  def getLines:String
  
}




/**
 * Static Page super class 
 */
class StaticPageLine (val lineText: String) extends Line {  
  def getLines = lineText
}





/**
 * Lyric page sub class
 */

class LyricPageLine (lineText:String) extends StaticPageLine (lineText){
  
  val characters: ListBuffer[CharHighlight] = ListBuffer()
    
  def this(lineText:String, highlights:Node) = {
    this(lineText)
    (highlights \\ "Highlight").foreach { Highlight => characters += CharHighlight.createFromNode(Highlight) }
  }
  
  
  def getCharacters = characters
  
  
  override def getLines = {
    "\n>>>>>>>NEW LINE>>>>>>>\n\n  "+
    super.getLines + "\n" +
    "LINE OK? " + indexMatch + "\n" + 
    getIndexFromCharacters.toString() +"\n"+
    getLineIndexesAsList(lineText).toString() + 
    println() // + 
    //characters.foldLeft(new StringBuffer()){ (sb, s) => sb append s.getCharHighlightVal}.toString() 
  }
  
  
  def indexMatch: Boolean = {
    getIndexFromCharacters == getLineIndexesAsList(lineText)
  }
  
  /**
   * Get a time index from list of characters and returns as a list
   */
  def getIndexFromCharacters: List[Int] = {
    characters.foldLeft(List[Int]()){ (charIndex, char) => charIndex :+ char.character.toInt}
  }
  
  
  
  /**
   * Get line indexes methods
   */
 def getLineindexes(str:String): List[(Int,Int)] = {
  var lengthSoFar = 0
  val words = str.split(" ")
  var list = new ListBuffer[(Int,Int)]
 
  for(i <- 0 to words.length-1) {
    if(i == 0) {
      list += ( (0, (words(i).length -1)))
      lengthSoFar += words(i).length + 1
    } else {
      list += ( (lengthSoFar, lengthSoFar + (words(i).length -1 ) ))
      lengthSoFar += words(i).length + 1
    } 
   }
    
    list.toList
   }                                               

 
 
  def getLineindexesWithWord(str:String): List[(String,(Int,Int))] = str.split(" ").toList zip getLineindexes(str)
  
  def getLineIndexesAsList(str:String): List[Int] = {
    var lengthSoFar = 0
    val words = str.split(" ")
    var list = new ListBuffer[Int]
   
    for(i <- 0 to words.length-1) {
      if(i == 0) {
        list += 0
        list += words(i).length -1
        lengthSoFar += words(i).length + 1
      } else {
        list += lengthSoFar
        list += lengthSoFar + (words(i).length -1 )
        lengthSoFar += words(i).length + 1
      } 
     }
      
      list.toList
 }        

  
}




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
 
                     
//Companion object
object CharHighlight{
  def createFromNode(highlight: Node):CharHighlight = {
       val  character = (highlight \\ "Character").text
       val time = (highlight \\ "Time").text
       val charType = (highlight \\ "Type").text
      
       
       new CharHighlight(character, time, charType)
  }
}
  



/*      parse each highlight block with Highlights
 *     0     6     11   16
 <Text>Didn't know what time</Text>
 

Each words needs start and end value

Start value = matching (or nearest) character + Empty "Type"
End value = matching (or nearest) character + Full "Type"
 
0
30.541666666666664
Empty

5
30.75
Full

7
30.791666666666664
Empty

10
31.125
Full

12
31.125
Empty

15
31.375
Full

17
31.416666666666664
Empty

20
31.833333333333332
Full

class SfLine (val lineText: String){
  
  val matrix = Array.ofDim[String](lineText.length)
}


* 
*/