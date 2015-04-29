package streamline
import scala.xml._
import scala.collection.mutable.ListBuffer


/**
 * Line trait
 */
trait Line { def getLines:String }
  



/**
 * Static Page super class 
 */
class StaticPageLine (val lineText: String) extends Line   { def getLines = lineText }




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
    super.getLines + "\n\n" +
   // "LINE OK? " + indexMatch + "\n" + 
    addTimesToWords + "\n" 
    //getIndexFromCharacters.toString() +"\n"+
    //getTimeFromCharacters.toString() +"\n"+
    //getLineIndexesAsList(lineText).toString() + 
    //characters.foldLeft(new StringBuffer()){ (sb, s) => sb append s.getCharHighlightVal}.toString() 
  }
  
  
  /**
   * Checks if character indexes in XML match the index created from the lyric line.
   */
  //NEED REFACTORING PRETTY COMPLICATED!
  def addTimesToWords: String = {
    val words = lineText.split(" ")
    
    val wordsAndTime = words zip getTimeFromCharacters
    
    val x = wordsAndTime.foldLeft(new StringBuffer()) { (sb,s) => sb.append(s._1 + "\n" + 
                                                                            " Start: " + s._2._1 + "\n" + 
                                                                            " End: " +  s._2._2 + "\n\n") }
    
    
    x.toString()
  }
  
  
  
  
  
   /**
   * Get a time index from list of characters and returns as a list
   */
   def getIndexFromCharacters: List[Int] = {
    characters.foldLeft(List[Int]()){ (charIndex, char) => charIndex :+ char.character.toInt}
  }
   def getTimeFromCharacters: List[(Double,Double)] = {
    val x = characters.foldLeft(List[Double]()){ (charTime, char) => charTime :+ Math.round(char.time.toDouble *100.0)/100.0}
    x.sliding(2).map(x => (x.head, x.tail.head)).toList
  }

  /**
   * Line indexes methods
   */
  
  def indexMatch: Boolean = {
    getIndexFromCharacters == getLineIndexesAsList(lineText)
  }
  
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