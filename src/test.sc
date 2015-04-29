
import scala.collection.mutable.ListBuffer
object test {
	 	
	 	val x = List(1,2,3,4,5,6,7,8,9)   //> x  : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
	 
    val y = x.grouped(2).toList                   //> y  : List[List[Int]] = List(List(1, 2), List(3, 4), List(5, 6), List(7, 8), 
                                                  //| List(9))
 
 		y(0)(0)                           //> res0: Int = 1
 
}