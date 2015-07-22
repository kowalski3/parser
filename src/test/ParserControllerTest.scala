package test

import org.scalatest._
import main._
import collection.mutable.Stack
import org.scalatest.BeforeAndAfter

class ParserControllerTest extends FlatSpec with Matchers with BeforeAndAfter {
  
  
//	var testBoard: Board = _
//	var aiPlayer: AI = _
//  
//  before{
//  	testBoard = new Board
//	aiPlayer = new AI (RED, 4)
//	}
//	
//	
//	"The AI" should "return only one move (clearly the best) when opponent has 3 counters in a row" in {
//  		testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(RED,1))
//	    testBoard.makeMove(new Move(RED,4))
//	    testBoard.makeMove(new Move(RED,6))
//	    aiPlayer.getMoves(testBoard).length should be (1)   	  
//	} 
//  	it should "block connect4 by playing in column 0" in {
//  		testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(YELLOW,0))
//	    
//	    testBoard.makeMove(new Move(RED,1))
//	    testBoard.makeMove(new Move(RED,4))
//	    testBoard.makeMove(new Move(RED,6))
//	    
//  	  	aiPlayer.getMoves(testBoard)(0).column  should be (0)	
//  	}
//  	
//  	"An exception" should "be thrown when a move is attempted in a full column" in {
//  	    testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(RED,0))
//	    testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(RED,0))
//	    testBoard.makeMove(new Move(YELLOW,0))
//	    testBoard.makeMove(new Move(RED,0))
//	    intercept[IllegalArgumentException] {
//  	    	testBoard.makeMove(new Move(YELLOW,0))
//	    }
//  	}
//  	
//  	"The game" should "finish in a draw when the board is full without connect4" in {
//  		var player: Player = RED
//  		
//  	  for (column <- 0 until Board.NUM_COLS ){
//  	    for(row <- 0 until Board.NUM_ROWS ){
//  	      testBoard.makeMove(new Move(player, column))
//  	      player = player.opponent
//  	    }
//  	    if (column % 2 == 0) player = player.opponent
//  	  }
//  		testBoard.getPossibleMoves(RED).length should be (0)
//  		testBoard.hasConnectFour should be (None)
//  		 
//  	}
//  	
//  	
  
  
}