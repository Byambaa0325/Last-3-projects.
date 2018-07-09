package classifier;

import java.io.*;
import java.util.Scanner;

class DataRead  {
     double[][] data;
     double[] outputs;
 public DataRead(String directory, int size)
 {
  try{
      data=new double[size][4];
      outputs= new double[size];
      String[] strLine = new String[size];
      Scanner input = new Scanner(new File(directory));


    int i=0;
      for(i = 0; i < strLine.length; i++) {
          strLine[i] = input.nextLine();        
      }

   for (i=0;i<strLine.length;i++){

    if(strLine[i].contains("Iris-setosa")){
        outputs[i]=1;
        strLine[i]= strLine[i].substring(0,15);
    }
    else if(strLine[i].contains("Iris-versicolor")){
        outputs[i]=2;
        strLine[i]= strLine[i].substring(0,15);
    }
    else if(strLine[i].contains("Iris-virginica")){
           outputs[i]=3;
        strLine[i]= strLine[i].substring(0,15);
       }       


   }
   for(i=0;i<strLine.length;i++){
       String[] parts = strLine[i].split(",");

       for(int j = 0 ; j<parts.length;j++){
           data[i][j]=Double.parseDouble(parts[j]);          
       }    
   }
   //Close the input stream
      input.close();
  }catch (FileNotFoundException | NumberFormatException e){//Catch exception if any
   System.err.println("Error: " + e.getMessage());
  }
 }

}
