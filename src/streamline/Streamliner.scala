package streamline

import scala.xml._

/*
 * https://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/
 */

class Streamliner(fileName:String) {
  
  //val pageList = List(1,2,3)
  
  
  
  val file = scala.xml.XML.loadFile(fileName) 
  
  
// def printXML = (file \\ "Page").foreach { Page => parse(Page)}
  
  
  def printXML = (file \\ "Page").foldLeft(List[SfPage]()) { (pages, pageNode) => 
    pages :+ parse(pageNode)
  }


  
  //def printXML = (file \\ "Page").foreach { Page => parse(Page)}

  
  def parse(node: Node):SfPage = {
    println(node.isEmpty)
    val pageType = (node \ "Type").text
    
     pageType match{
      case "Title" => println("Title")
      case "Lyrics" => println("Lyrics")
      case "Instruction" => println("Instruction")
      case _ => println("Dont recognise this type") 
    }
    
    ???
    
  }

  def createTitlePage(titlePage: Node){
    
    
  }


}





object Test extends App {
   
  val sfParser = new Streamliner("C:/Julian/git/parser/data/sfXmlSample1.xml")
  sfParser.printXML
    
}

/*
def printXML = (file \ "Pages").foreach { Pages =>
   //  println("Pages")
     (Pages \ "Page").foreach { Page => parse(Page)
     
       (Page \ "Paragraphs").foreach { Paragraphs =>  
       //  println("    <PARAGRAPHS>") 
         (Paragraphs \ "Block").foreach { Block =>  
         //  println("      <BLOCK>")
           (Block \ "Lines").foreach { Lines =>  
           //  println("      <LINES>")
             (Lines \ "Line").foreach { Line =>  
              // println("        <LINE>")
               (Line \ "Text").foreach { Text => 
              //  println("           <TEXT>" + Text.text + "</TEXT>")
               }
             }
           }
         }
       }
     }
   }
*/