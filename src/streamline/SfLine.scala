package streamline


trait Line

// line doesn't need to be its own class, this is for lyric page type


class SfLine (val lineText: String){
  
  val matrix = Array.ofDim[String](lineText.length)
}

/*      parse each highlight block with Highlights
 *     0     6     11   16
 <Text>Didn't know what time</Text>
 

Each words needs start and end value

Start value = matching (or nearest) character + Empty "Type"
End value = matching (or nearest) character + Full "Type"
 
0
30.541666666666664
Empty

5
30.75
Full

7
30.791666666666664
Empty

10
31.125
Full

12
31.125
Empty

15
31.375
Full

17
31.416666666666664
Empty

20
31.833333333333332
Full
             

 *      
 */
