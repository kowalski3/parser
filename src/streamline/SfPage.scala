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
    return startTime + " \n" + endTime + " \n" + pageType
  }
  
}

//THIS IS INSTRUCTION AND LYRIC PAGE
class StandardPage  (  pageType: String,
                       startTime: String,
                       endTime: String,
                       val lines: List[String]) extends BasicPage(pageType,startTime,endTime)   {
  
  override def pageValues:String = {
    startTime + " \n" +
    endTime + " \n" +
    pageType
  }
  
  
}


//same as staticpage but has a different Sfline type
class LyricPage  (  pageType: String,
                    startTime: String,
                    endTime: String,
                    val lyric:String,
                    val lines: List[(String,String,String)]) extends BasicPage(pageType,startTime,endTime)   {
  
//  def this(  startTime:Double,
//             endtime:Double,
//             lines:List[Line],
//             pageType:String) = {
//    this(???,???,???,???)
//    
//  }
//  
  
  
}

