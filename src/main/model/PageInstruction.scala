package main.model

/*
 * @author julian
 * Instruction page, extends Page Basic
 */
class PageInstruction  ( pageType: String,
                         startTime: String,
                         endTime: String,
                         val lines: List[Line]) extends PageBasic(pageType,startTime,endTime)   {
   
  override def pageValues:String = {
     return super.pageValues  + " \n" +
     lines.foldLeft(new StringBuffer()) { (sb, s) => sb append s.getLines + "\n"}
  }
   
}
