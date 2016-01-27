package main.view
import processing.core._
import java.io.File
import controlP5.ControlP5
import controlP5.Textfield
import controlP5.Textarea
import main.controller.ParserController

/**
 * View class for application
 * @author julian
 */
class ParserUI extends PApplet{
  
  //Controller reference links to this view class
  var controller: ParserController = null
  
  // Processing-related fields
  var cp5: ControlP5 = null
  var myTextarea: Textarea = null
  
  // Fields controlling directory configuration
  var inDir: String = ""
  var inSet: Boolean = false
  var inSetTemp: Boolean = false
  
  var outDir: String = ""
  var outSet: Boolean = false
  var outSetTemp: Boolean = false
  
  var running = false;
  
  
 
  
  // Processing settings run at construction
  override def settings(){
    size(300,450) 
  }
  //Processing settings run at construction
  override def setup(){ 
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
  
  //Processing draw loop runs in a cycle
  override def draw(){
    background(0);
     fill(255);
  }
  
  
  /*
   * Buttons 
   * Select input directory
   */
  
  def in {
      if( ! inSet) {
        inSetTemp = true
        selectFolder("Select an input directory", "selected")
      } else{
        myTextarea.setText("Input directory is already set");
      }
  }
  
  /*
   * Buttons 
   * Select output directory
   */
  def out {
    if( ! outSet) {
        outSetTemp = true
        selectFolder("Select an output directory", "selected")
      } else{
        myTextarea.setText("Output directory is already set");
      }
  }
  
  
  /*
   * Buttons 
   * Convert tracks
   */
   def convert {
    if(inSet && outSet && !running) {
      running = true
        if(controller == null){
          controller = new ParserController(inDir, outDir, this)
        }
         
        //Start conversion process
        controller.processDirectory
        controller.convertedTracksToFile
      
        //Output broken files
        val broken = controller.getBrokenTracks
        val brokenFiles = broken.foldLeft(new StringBuffer)( (sb, s) => sb.append(s.getFileName + "\n"))
        println(brokenFiles)
    
       controller.writeToFile(outDir + "/" + "brokenTrack.txt", brokenFiles.toString())
             
    } else {
      myTextarea.setText("Error, set input and output");  
    }
    
  }
  
   /*
   * Text area 
   * Update text
   */
  def setTheText(str:String) {
    myTextarea.setText(myTextarea.getText + "\n" + str)
  }
  
  /*
   * Set directory based on user input
   */
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
  
  
  
}