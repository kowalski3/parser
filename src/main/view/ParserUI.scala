package main.view
import processing.core._
import java.io.File
import controlP5.ControlP5
import controlP5.Textfield
import controlP5.Textarea
import main.controller.ParserController

/**
 * @author julian
 */
class ParserUI extends PApplet{
  //Improve this so not null start.  Have to initialise by setup() but also can't be left
  // unitialised else class
  
  var controller: ParserController = null
  
  var cp5: ControlP5 = null
  
  var inDir: String = ""
  var inSet: Boolean = false
  var inSetTemp: Boolean = false
  
  var outDir: String = ""
  var outSet: Boolean = false
  var outSetTemp: Boolean = false
  
  var running = false;
  
  var myTextarea: Textarea = null
 
  override def setup(){
    
    size(300,450) 
    cp5 = new ControlP5(this);
    var font: PFont = createFont("arial",20);
    textFont(font);
    
    
    cp5.addButton("in")
    .setBroadcast(false)  
    .setValue(100)
    .setPosition(40,40)
    .setSize(50,20)
    .setBroadcast(true);
    
    cp5.addButton("out")
    .setBroadcast(false)  
    .setValue(100)
    .setPosition(40,90)
    .setSize(50,20)
    .setBroadcast(true);
    
    cp5.addButton("convert")
    .setBroadcast(false)  
    .setValue(100)
    .setPosition(40,140)
    .setSize(50,20)
    .setBroadcast(true);
    
    myTextarea = cp5.addTextarea("txt")
      .setPosition(40,200)
      .setSize(200,220)
      .setFont(createFont("arial",12))
      .setLineHeight(14)
      .setColor(color(128))
      .setColorBackground(color(255,100))
      .setColorForeground(color(255,100));
  }
  
  override def draw(){
    background(0);
     fill(255);
    
  }
  
  
  def in {
      if( ! inSet) {
        inSetTemp = true
        selectFolder("Select an input directory", "selected")
      } else{
        myTextarea.setText("Input directory is already set");
      }
  }
 
  def out {
    if( ! outSet) {
        outSetTemp = true
        selectFolder("Select an output directory", "selected")
      } else{
        myTextarea.setText("Output directory is already set");
      }
  }
  
   def convert {
    if(inSet && outSet && !running) {
      running = true
        if(controller == null){
          controller = new ParserController(inDir, outDir, this)
        }
        controller.processDirectory
        controller.convertedTracksToFile
      
        val broken = controller.getBrokenTracks
        val brokenFiles = broken.foldLeft(new StringBuffer)( (sb, s) => sb.append(s.getFileName + "\n"))
        println(brokenFiles)
    
       controller.writeToFile(outDir + "/" + "brokenTrack.txt", brokenFiles.toString())
       
      
      
    } else {
      myTextarea.setText("Error, set input and output");  
    }
    
  }
  
  def setTheText(str:String) {
    myTextarea.setText(myTextarea.getText + "\n" + str)
  }
  
  def selected(selection: File) {
      if (selection == null) {
        myTextarea.setText("Window was closed or the user hit cancel.");
        inSetTemp = false
        outSetTemp = false
      } else if(inSetTemp){
        myTextarea.setText("Input directory is " + selection.getAbsolutePath());
        inDir =  selection.getAbsolutePath()
        inSet = true;
        inSetTemp = false;
      } else if(outSetTemp) {
        myTextarea.setText("Output directory is " + selection.getAbsolutePath());
        outDir =  selection.getAbsolutePath()
        outSet = true;
        outSetTemp = false;
      }
   } 
  
  
    
    //      this.initialInputSet = true;
//      String lines[] = loadStrings(selection.getAbsolutePath());
//      StringBuffer sb = new StringBuffer();
//     
//      // println("there are " + lines.length + " lines");
//      for (int i = 0 ; i < lines.length; i++) {
//        sb.append(lines[i]);
//      }
//      //removes all white space
//      String s = sb.toString().replaceAll("\\s+","");
//
//      //TO DO - Remove this limit once program memory footprint and speed has been improved
//      if(s.length() <= 4000){
//        input(s);  
//      } else {
//       
//      }

      
  
}