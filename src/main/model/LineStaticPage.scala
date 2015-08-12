package main.model

/**
 * @author julian
 */
/**
 * Static Page super class 
 */
class LineStaticPage (val lineText: String,
                      val voice: String) extends Line   { 
  def getLines = "Voice: " + voice + "\nLine: " + lineText 
  }

