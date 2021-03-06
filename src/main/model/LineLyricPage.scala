package main.model
import scala.xml._
import scala.collection.mutable.ListBuffer

import scala.collection.JavaConversions._
import java.io._

/*
 * @author julian
 */
class LineLyricPage (lineText: String,
                     voice: String) extends LineStaticPage (lineText,voice){ 
  val characters: ListBuffer[Character] = ListBuffer()
  
  val errorFile = new File("C:Julian/output/Streamliner/out/outofBounds.txt")
  
  
   //REGEXS (need better validation method)
   //TO DO - Need better method to deal with validation 
    val questionMarkPattern = "[a-z][^a-zA-Z '][a-z]+".r 
  
  /*-----------------------------------------------------------------
   * Constructor
   *-----------------------------------------------------------------*/
  def this(lineText:String, voice:String, highlights:Node) = {
   this(lineText,voice)
   val x = questionMarkPattern.findFirstIn(lineText).size
   if(x > 0) println(lineText)
   
  
    (highlights \\ "Highlight").foreach { Highlight => characters += Character.createFromNode(Highlight) }
  }
  

  def getCharacters = characters
  
 
  /*-----------------------------------------------------------------
   * Get lines methods
   *-----------------------------------------------------------------*/
  override def getLines = {
    "\n>>>>>>>NEW LINE>>>>>>>\n\n" +
    super.getLines + "\n\n" +
    addTimesToWords + "\n"
  }
  
   def getFixedLines = {
    try{
     "\n>>>>>>>NEW LINE>>>>>>>\n\n" +
    super.getLines + "\n\n" +
    addTimesToWordFix + "\n"
    } catch {
      case e: IndexOutOfBoundsException => "*REMOVE*"
      //case x: 
    }
  }
  
   
    def writeToFile(txt: String): Unit = {
     val pw = new PrintWriter(errorFile)
     pw.write(txt)
     pw.close   
    }
 
   
   
  
 /**
   * Returns the debug output for lines with broken indices
   */
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
  * 
  * 
  * Takes the lines words and splits by whitespace " "
  * It then zips the words with the time index created from the character data
  */
  //TO DO - REFACTOR AS PRETTY COMPLICATED...
  def addTimesToWords: String = {
    val words = lineText.split(" ")
    val wordsAndTime = words zip getTimeFromCharacters
    wordsAndTime.foldLeft(new StringBuffer()) { (sb,s) => sb.append(
                                                            "[" + s._2(0) + "] " +
                                                            s._1 + "" + 
                                                            " [" +  s._2(1) + "]\n") 
                                                          }.toString()
}
  
  /*-----------------------------------------------------------------------
   * Line and character index methods
   *-----------------------------------------------------------------------*/  
  /**
   * Checks if the indexes from the XML and the expected indexes match
   */
  def indexMatch: Boolean = {
    getIndexFromCharacters == getLineIndexesAsList(lineText)
  }
    
   /**
   * Gets the time indices from the line's characters
   */
   def getIndexFromCharacters: List[Int] = {
    characters.foldLeft(List[Int]()){ (charIndex, char) => charIndex :+ char.character.toInt}
  }
   def getTimeFromCharacters: List[ List[Double] ] = {
    val x = characters.foldLeft(List[Double]()){ (charTime, char) => charTime :+ Math.round(char.time.toDouble *100.0)/100.0}
    x.grouped(2).toList
  }
  
      
   /**
   * For a given line take the word and return the expected word indices as list of int
   */
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
  
   /**
   * For a given line take the word and return the expected word indices as list of tuples
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
  
  


   
     
   /*-----------------------------------------------------------------
   * Fix broken lines methods
   *-----------------------------------------------------------------*/
  
  /**
   * Returns the fixed lines for tracks with broken indices
   */
 
     def addTimesToWordFix: String = {
      val words = lineText.split(" ")
      val wordsAndTime = words zip getTimeFromCharacters2
      wordsAndTime.foldLeft(new StringBuffer()) { (sb,s) => sb.append(
                                                              "[" + s._2(0) + "] " +
                                                              s._1 + "" + 
                                                              " [" +  s._2(1) + "]\n") 
                                                            }.toString()
      }
  
   
    
      def getTimeFromCharacters2: List[ListBuffer[Double] ] = { 
        var returnList = new ListBuffer[Double]
        var lineIndexFromText = getLineindexesUngrouped(lineText)
        var timePlusChar = getTimePlusChar
        
        for(i <- 0 until lineIndexFromText.length){
           val lineIndex = lineIndexFromText(i)
           val lineIndexMod = i % 2
           
          for(j <- 0 until getTimePlusChar.length){
            val timeindex = timePlusChar(j)._2
            val time = timePlusChar(j)._1
            val timeindexMod = j % 2
            
            if(lineIndexMod == timeindexMod && lineIndex == timeindex){
              returnList += time
            }   
          }   
        } 
            returnList.grouped(2).toList
       }
    
      
    
    def getTimePlusChar: List[(Double, Int)] = {
      characters.foldLeft(List[Double]()){ (charTime, char) => charTime :+ Math.round(char.time.toDouble *100.0)/100.0} zip getIndexFromCharacters
      }
    

    
     def getLineindexesUngrouped(str:String): List[Int] = {
    var lengthSoFar = 0
    val words = str.split(" ")
    var list = new ListBuffer[Int]
 
    for(i <- 0 to words.length-1) {
      if(i == 0) {
        list += 0
        list += (words(i).length -1)
        lengthSoFar += words(i).length + 1
      } else {
        list += lengthSoFar
        list += lengthSoFar + (words(i).length -1 )
        lengthSoFar += words(i).length + 1
      } 
   } 
    list.toList
   } 
     
  
   //Fix broken lines end
    
   
}