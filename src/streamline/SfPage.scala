package streamline
import scala.xml._

trait SfPage {  
  def pageValues:String

}
  
 



//TITLE PAGE
class BasicPage ( val pageType: String,
                  val startTime: String,
                  val endTime: String
                  )  extends SfPage {
  
 
  def pageValues:String = {
    //println("Start time " + startTime.length())
    "***********************************" + "\n" +
    "PageType: " + pageType +  "\n" +
    "StartTime: " + startTime + " \n" + 
    "EntTime: " + endTime + " \n"
  } 
}



//THIS IS INSTRUCTION PAGE
class InstructionPage  ( pageType: String,
                         startTime: String,
                         endTime: String,
                         val lines: List[Line]) extends BasicPage(pageType,startTime,endTime)   {
   
  override def pageValues:String = {
     super.pageValues  + " \n" +
     lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.getLines + "\n"}
  }
   
}





//LYRIC PAGE
class LyricPage  (  pageType: String,
                    startTime: String,
                    endTime: String,                    
                    lines: List[Line]) extends InstructionPage (pageType,startTime,endTime,lines)   {
  
  override def pageValues:String = {
     super.pageValues  + " \n" +
     lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.asInstanceOf[LyricPageLine].getLines}.toString()

  }
  
  
 /**
  * Compares character indexes in XML with index of words in lyric 
  */
  def pageLineIndexesOk: Option[Boolean] = {
    var flag = Option(true) 
    lines.foreach { lines =>  
      lines match{
        case lines: LyricPageLine => if( ! lines.indexMatch) flag = Option(false)                 
        case _ => return None  
      } 
    }
    flag
  }  
  
}

