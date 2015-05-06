package streamline

import scala.collection.mutable.ListBuffer


class Track(fileName:String, pageList: List[SfPage]){
  
  //TO DO CHECK THIS IS WORKING OK, SEEMS BUGGY
  def trackOk: Option[Boolean] = {
    var pagesok = Option(true)
      
    pageList.foreach { page => 
       page match {  
         case page: LyricPage => val pageOk =  (LyricPage.pageLineIndexesOk(page.lines)) 
                                 pageOk match {
                                    case Some(false) => pagesok = Option(false)
                                    case Some(true) => 
                                    //THROW EXCEPTION HERE?
                                    case None => 
                                  }                                                                                 
         case _                => None           
      }
    }
    pagesok
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