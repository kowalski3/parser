package streamline
import scala.xml._

trait SfPage {
  
  def pageValues:String

}
  
 



//THIS IS TITLE PAGE
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



//THIS IS INSTRUCTION 
class InstructionPage  ( pageType: String,
                         startTime: String,
                         endTime: String,
                         val lines: List[Line]) extends BasicPage(pageType,startTime,endTime)   {
  
  
  
  override def pageValues:String = {
     super.pageValues  + " \n" +
     lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.getLines + "\n"}

  }
  
  
}





//same as InstructionPage but has a different Sfline type
class LyricPage  (  pageType: String,
                    startTime: String,
                    endTime: String,                    
                    lines: List[Line]) extends InstructionPage (pageType,startTime,endTime,lines)   {
  
  override def pageValues:String = {
     super.pageValues  + " \n" +
     lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.asInstanceOf[LyricPageLine].getLines}.toString()

  }
  
 
  
  
  
// def charsToString:String = lines.foldLeft(new StringBuilder) 
//                                 { (sb, s) => sb append  "   __________"  + "\n" +
//                                                         "   " + s._1 + "\n" + 
//                                                         "   " + s._2 + "\n" +
//                                                         "   " + s._3 + "\n" }.toString() 
//  
//     override def pageValues:String = {
//      super.pageValues  + " \n" +
//      lyric + " \n" +
//      charsToString
//    
//  }                                                    
  
}

