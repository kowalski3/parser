
import scala.collection.mutable.ListBuffer
object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val line = "Didn't know what time"              //> line  : String = Didn't know what time
  



	def getIndexWithWord(str:String): List[(String,(Int,Int))] = {
	
		str.split(" ").toList zip getindex(str)
		
	}                                         //> getIndexWithWord: (str: String)List[(String, (Int, Int))]

 def getindex(str:String): List[(Int,Int)] = {
 	var lengthSoFar = 0
 	val words = str.split(" ")
 	var list = new ListBuffer[(Int,Int)]
 	
 	
 	for(i <- 0 to words.length-1) {
 	
 	if(i == 0) {
 		list += ( (0, (words(i).length -1)))
 		lengthSoFar += words(i).length + 1
 	} else {
 		list += ( (lengthSoFar, lengthSoFar + (words(i).length -1 ) ))
 		lengthSoFar += words(i).length + 1
 	}
 	
 	}
 	
 	list.toList
 }                                                //> getindex: (str: String)List[(Int, Int)]
 

val characters = List('a','b','c')                //> characters  : List[Char] = List(a, b, c)
characters.foldLeft(List[Int]()){ (sb, s) => sb :+ 1}.toString()
                                                  //> res0: String = List(1, 1, 1)

  println(getIndexWithWord(line))                 //> List((Didn't,(0,5)), (know,(7,10)), (what,(12,15)), (time,(17,20)))
  
  //List(1,2,3) zip List (4, 5,6)
  
  
  List(1,2,3) == List(1,2,3)                      //> res1: Boolean = true
  
}