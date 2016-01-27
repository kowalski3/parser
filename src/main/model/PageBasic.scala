package main.model

/**
 * @author julian
 * Basic karaoke page, inherits from page trait.
 * Contains page type, start time and end time fields common to all other
 * concrete page instances
 */


/*--------------------------------------------------------------------
 * TITLE PAGE
 *--------------------------------------------------------------------*/
class PageBasic ( val pageType: String,
                  val startTime: String,
                  val endTime: String
                  )  extends Page {
  
 
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
