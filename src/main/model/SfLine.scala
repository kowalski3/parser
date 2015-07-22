package main.model
import scala.xml._
import scala.collection.mutable.ListBuffer



/**
 * Line trait
 */
trait Line { def getLines:String }
  



/**
 * Static Page super class 
 */
class StaticPageLine (val lineText: String,
                      val voice: String) extends Line   { 
  def getLines = "Voice: " + voice + "\nLine: " + lineText 
  }




/**
 * Lyric page sub class
 */

class LyricPageLine (lineText: String,
                     voice: String) extends StaticPageLine (lineText,voice){ 
  val characters: ListBuffer[Character] = ListBuffer()
  

  def this(lineText:String, voice:String, highlights:Node) = {
    this(lineText,voice)
    (highlights \\ "Highlight").foreach { Highlight => characters += Character.createFromNode(Highlight) }
  }
  

  def getCharacters = characters
  
 
 
  override def getLines = {
    "\n>>>>>>>NEW LINE>>>>>>>\n\n" +
    super.getLines + "\n\n" +
    addTimesToWords + "\n"
  }
  
  def getLinesBrokenTrack = {
    "\n>>>>>>>NEW LINE>>>>>>>\n\n"+
    super.getLines + "\n\n" +
    "LINE OK? " + indexMatch + "\n" + 
    lineText + "\n"+
    //addTimesToWords + "\n" + 
    getIndexFromCharacters.toString() +"\n"+
    getLineIndexesAsList(lineText).toString() + "\n\n"
  }
  
  /**
   * Checks if character indexes in XML match the index created from the lyric line.
   */
  //NEED REFACTORING PRETTY COMPLICATED!
  def addTimesToWords: String = {
    val words = lineText.split(" ")
    val wordsAndTime = words zip getTimeFromCharacters
    wordsAndTime.foldLeft(new StringBuffer()) { (sb,s) => sb.append(
                                                                    "[" + s._2(0) + "] " +
                                                                      s._1 + "" + 
                                                                    " [" +  s._2(1) + "]\n") }.toString()
  }
  
  
  
  
  
   /**
   * Get a time index from list of characters and returns as a list
   */
   def getIndexFromCharacters: List[Int] = {
    characters.foldLeft(List[Int]()){ (charIndex, char) => charIndex :+ char.character.toInt}
  }
   def getTimeFromCharacters: List[ List[Double] ] = {
    val x = characters.foldLeft(List[Double]()){ (charTime, char) => charTime :+ Math.round(char.time.toDouble *100.0)/100.0}
    x.grouped(2).toList
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