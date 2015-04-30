package streamline

import scala.collection.mutable.ListBuffer


class Track(fileName:String, pageList: List[SfPage]){
  
  
  def lyricPagesOk: Option[Boolean] = {
    var pagesok = Option(true)
      
    pageList.foreach { page => 
       page match {  
         case page: LyricPage => val pageOk =  (page.pageLineIndexesOk) 
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
  
  
  
}
  



object Track{
  def newTrack(fileName:String, pageList: List[SfPage]):Track =
     new Track(fileName,pageList)
}