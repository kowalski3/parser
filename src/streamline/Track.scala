package streamline

import scala.collection.mutable.ListBuffer


class Track(fileName:String, pageList: List[SfPage]){
  
  
  def trackOk: Option[Boolean] = {
    var pagesok = Option(true)
      
    pageList.foreach { page => 
       page match {  
         case page: LyricPage => val pageOk =  (LyricPage.pageLineIndexesOk(page.lines)) 
                                 pageOk match {
                                    case Some(false) => return Option(false)
                                    case Some(true) => return Option(true)
                                    //THROW EXCEPTION HERE?
                                    case None => return None
                                  }                                                                                 
         case _                => None           
      }
    }
    None
  }
  
  
  def getTrackData: String = {
    fileName + "\n" + pageList.foldLeft(new StringBuffer()){ (sb, s) => sb append s.pageValues}.toString()
  }
  
  
  
  def getFileName:String = fileName
  
}
  



object Track{
  def apply(fileName:String, pageList: List[SfPage]):Track =
     new Track(fileName,pageList)
}