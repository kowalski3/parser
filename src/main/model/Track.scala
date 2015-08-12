package main.model


class Track(fileName:String,
            artist: String,
            title: String,
            copyright: String,
            writers:String,
            pageList: List[Page]){
  
  //TO DO CHECK THIS IS WORKING OK, SEEMS BUGGY
  def trackOk: Option[Boolean] = {
    var pagesok = Option(true)
      
    pageList.foreach { page => 
       page match {  
         case page: PageLyric => val pageOk =  (PageLyric.pageLineIndexesOk(page.lines)) 
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
   "********************TRACK INFO********************" +
    "\n" + "Artist: " + artist + 
    "\n" + "Title: " + title +
    "\n" + "Copyright: " + copyright +
    "\n" + "Writers: " + writers + 
    "\n" + pageList.foldLeft(new StringBuffer()){ (sb, s) => sb append s.pageValues}.toString()
  }
  
  
  
  def getFileName:String = fileName
  
}