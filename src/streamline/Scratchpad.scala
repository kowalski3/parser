package streamline

import scala.xml._


object Tester extends App {
   
   val file = scala.xml.XML.loadFile("C:/Julian/git/parser/data/sfXmlSample1.xml") 
     
   val printXML = (file \\ "Page").foldLeft(List[String]()){(acc,node) =>        
                    (node \ "Type").text    match{
                       case "1" => println(1)
                       case "2" => println(2)
                   }
                     acc
       }
  
    
   println("hi")
   println(printXML)
 }