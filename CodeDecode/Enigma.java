package exratasks;

public class Enigma {
      class Cell{
    	  int x;
    	  int y;
    	  public Cell(){}
    	  public Cell(int x, int y){
    		  this.x = x;
    		  this.y = y;
    	  }
      }
      
      private Cell[] letters  = new Cell[25];
      private boolean[] hashSet;
      
      private char[][] codeMatrix;
      
      
      public Enigma(char[][] matrix){
    	  if(matrix == null || matrix.length < 1 || matrix.length != matrix[0].length){
    		  throw new IllegalArgumentException("Invalid Code Matrix");
    	  }
    	  this.codeMatrix = new char[matrix.length][matrix.length];
    	  hashSet = new boolean[26];
    	  
    	  for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix.length; col++) {
				char currCh = matrix[row][col];
				if(!Character.isLetter(currCh)){
					throw new IllegalArgumentException("The is occurence of non alpha charachters in the Code Matrix");
				}
				currCh =  Character.toUpperCase(currCh);
				
				if(currCh == 'Z'){
					throw new IllegalArgumentException("Z is must not be include in the Code Matrix");
				}
				
				if(hashSet[currCh-'A']){
					throw new IllegalArgumentException("The is repited charachter in the Code Matrix");
				}else{
					hashSet[currCh-'A'] = true;
				}
				
				this.codeMatrix[row][col]= currCh;
				letters[currCh-'A'] = new Cell(row,col);
			}
		  }
    	  
    	  hashSet=null;
      }
      
     public String decode(String message){
    	 boolean oddSymbols = false;
    	 if((message.length() &1) == 1){
    		 message=message.toUpperCase() + 'A';
    		 oddSymbols = true;
    	 }else{
    		 message=message.toUpperCase(); 
    	 }
    	 
    	 StringBuilder sb = getString(message,"Can't Decode. Unknow symbol accurs! ",true);
    	 
    	 if(oddSymbols){
    		 sb.append("#");
    	 }
    	 
    	 return sb.toString();
     }
     
     private Cell getCell(char ch){
    	 if(ch>='A' && ch <='Z'){
    		 return letters[ch-'A']; 
    	 }
    	 else{
    		 return null;
    	 } 	 
     }
     
     private void loadChars(Cell firstChar, Cell secondChar,StringBuilder sb,boolean decode){
    	 char ch1,ch2;
    	 if(firstChar.y != secondChar.y && firstChar.x != secondChar.x){
    		 ch1 = codeMatrix[firstChar.x][secondChar.y];
    		 ch2 = codeMatrix[secondChar.x][firstChar.y];
    	 }else if(firstChar.y != secondChar.y){ 
    		 ch1 = codeMatrix[firstChar.x][getXorY(firstChar.y,decode)];
    		 ch2 = codeMatrix[secondChar.x][getXorY(secondChar.y,decode)];
    	 }else{
    		 ch1 = codeMatrix[getXorY(firstChar.x,decode)][firstChar.y];
    		 ch2 = codeMatrix[getXorY(secondChar.x,decode)][secondChar.y];
    	 }
    	
    	 sb.append(ch1);
    	 sb.append(ch2);
     }
     
     public String encode(String message){
    	 message = message.toUpperCase();
    	 boolean oddSymbols = false;
    	 if(message.charAt(message.length()-1)=='#'){
    		 message = message.substring(0,message.length()-1);
    		 oddSymbols = true;
    	 }else if((message.length() &1) == 1){
    		 return "Cant encode.Odd number of symbols";
    	 }
    
    	 StringBuilder sb = getString(message,"Can't Encode. Unknow symbol accurs! ",false);
    	 
    	 if(oddSymbols){
    		 sb.delete(sb.length()-1, sb.length());
    	 }
    	 return sb.toString();
     }
     
     int getXorY(int xOrY,boolean decode){
    	 if(decode){
    		 return (xOrY+1)%codeMatrix.length;
    	 }else{
    		 return xOrY -1 < 0 ? codeMatrix.length -1:xOrY-1;
    	 }
     }
     
     public StringBuilder getString(String message,String errMsg,boolean decode){
    	 Cell firstChar; 
    	 Cell secondChar;
    	 StringBuilder sb = new StringBuilder();
    	 
    	 for (int i = 1; i < message.length(); i+=2) {
    		 firstChar = getCell(message.charAt(i-1));
    		 secondChar = getCell(message.charAt(i));
    		 if( firstChar == null || secondChar == null){
    			 sb.setLength(0);
        		 return sb.append(errMsg);
        	 }
    		 loadChars(firstChar,secondChar,sb,decode);
		 }
    	 return sb;
     }
}
