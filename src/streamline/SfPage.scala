package streamline
import scala.xml._

trait SfPage 
  
 


//THIS IS TITLE PAGE
class BasicPage ( val startTime: Double,
                  val endTime: Double,
                  val line: SfLine,
                  val pageType: String)  extends SfPage {
  
  

  
  def this (pageNode: Node) {
   
    this(???,???,???,???) 
  }
  
}

//THIS IS INSTRUCTION AND LYRIC PAGE
class NormalPage  ( startTime: Double,
                    endTime: Double,
                    line: SfLine,
                    pageType: String) extends BasicPage(startTime,endTime,line,pageType)   {
  
  
}



