package main.model

/*
 * Lyric Page
 * Extends Page instruction which inherits from Page Basic 
 * which inherits from Page trait
 */

class PageLyric  (  pageType: String,
                    startTime: String,
                    endTime: String,
                    lines: List[Line], // Lyric pages contain a collection of lines.  Composition relationship
                    val pageIndexBug: Boolean) extends PageInstruction (pageType,startTime,endTime,lines)   {
  
  
//CODE SMELL . This has been here as call to super.pagevalues was causing problems. due to duplicated lines.foldleft.... methods
  def pageTimesLyric:String = {
    //println("Start time " + startTime.length())
    "\n\n********************" + pageType.toUpperCase() + "********************" + "\n" +
    "PageType: " + pageType +  "\n" +
    "StartTime: " + roundTime(startTime) + " \n" + 
    "EndTime: " + roundTime(endTime) + " \n"
  } 
  
 
   override def pageValues:String = {
    if(pageIndexBug){
      
      return pageTimesLyric + " \n" +
      lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.asInstanceOf[LineLyricPage].getLines}.toString()
    } else { //if indexing problem exists
      
      return pageTimesLyric + " \n" +
      lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.asInstanceOf[LineLyricPage].getFixedLines}.toString()
    } 
  }
  
  

}
//Companion object Lyric Page
object PageLyric {
  
  def apply(pageType: String, startTime: String,  endTime: String, lines: List[Line]): PageLyric = {
    val pageIndexBug: Boolean = pageLineIndexesOk(lines) match {
     case Some(true) => true
     case Some (false) => false
  }
    new PageLyric(pageType, startTime, endTime, lines, pageIndexBug) 
  }
    
  /**
  * Compares character indexes in XML with index of words in lyric 
  */
  def pageLineIndexesOk(lines: List[Line]): Option[Boolean] = {
    var flag = Option(true) 
    lines.foreach { lines =>  
      lines match{
        case lines: LineLyricPage => if( ! lines.indexMatch) flag = Option(false)                 
        case _ => return None  
      } 
    }
   // println(flag)
    flag
  }                                         
}
