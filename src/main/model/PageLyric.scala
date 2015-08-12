package main.model

/**
 * @author julian
 */
/*--------------------------------------------------------------------
 * LYRIC PAGE
 *--------------------------------------------------------------------*/
class PageLyric  (  pageType: String,
                    startTime: String,
                    endTime: String,
                    lines: List[Line],
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
    } else {
      return "\n\n********************" + pageType.toUpperCase() + "********************" + "\n" + 
      "!!!!!!!!! THIS LYRIC PAGE CONTAINS ERRORS !!!!!!!!!\n" +
      lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.asInstanceOf[LineLyricPage].getLinesBrokenTrack}.toString()
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
