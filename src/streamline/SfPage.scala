package streamline
import scala.xml._

trait SfPage {  
  def pageValues:String

}
  
 

//TO REFACTOR SEE COMMENTS BELOW. In "Lyric Page"

//TITLE PAGE
class BasicPage ( val pageType: String,
                  val startTime: String,
                  val endTime: String
                  )  extends SfPage {
  
 
  def pageValues:String = {
    //println("Start time " + startTime.length())
    "\n\n********************" + pageType.toUpperCase() + "********************" + "\n" +
    "PageType: " + pageType +  "\n" +
    "StartTime: " + roundTime(startTime) + " \n" + 
    "EndTime: " + roundTime(endTime) + " \n"
  } 
  
  
  def roundTime(time:String):String = {
  
  val x = Math.round(time.toDouble *100.0)/100.0
  x.toString()
  
}
  
  
}




 

//THIS IS INSTRUCTION PAGE
class InstructionPage  ( pageType: String,
                         startTime: String,
                         endTime: String,
                         val lines: List[Line]) extends BasicPage(pageType,startTime,endTime)   {
   
  override def pageValues:String = {
     return super.pageValues  + " \n" +
     lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.getLines + "\n"}
  }
   
}





//LYRIC PAGE
class LyricPage  (  pageType: String,
                    startTime: String,
                    endTime: String,
                    lines: List[Line],
                    val pageIndexBug: Boolean) extends InstructionPage (pageType,startTime,endTime,lines)   {
  
  
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
      lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.asInstanceOf[LyricPageLine].getLines}.toString()
    } else {
      return "\n\n********************" + pageType.toUpperCase() + "********************" + "\n" + 
      "!!!!!!!!! THIS LYRIC PAGE CONTAINS ERRORS !!!!!!!!!\n" +
      lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.asInstanceOf[LyricPageLine].getLinesBrokenTrack}.toString()
    } 
  }
  
  

}

object LyricPage {
  
  def apply(pageType: String, startTime: String,  endTime: String,lines: List[Line]):LyricPage = {
    val pageIndexBug: Boolean = pageLineIndexesOk(lines) match {
     case Some(true) => true
     case Some (false) => false
  }
    new LyricPage(pageType, startTime, endTime, lines, pageIndexBug) 
  }
    
  /**
  * Compares character indexes in XML with index of words in lyric 
  */
  def pageLineIndexesOk(lines: List[Line]): Option[Boolean] = {
    var flag = Option(true) 
    lines.foreach { lines =>  
      lines match{
        case lines: LyricPageLine => if( ! lines.indexMatch) flag = Option(false)                 
        case _ => return None  
      } 
    }
   // println(flag)
    flag
  }                      
                    
}
