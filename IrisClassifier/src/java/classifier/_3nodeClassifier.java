package classifier;

import static java.lang.Math.exp;
import static java.lang.Math.random;

public class _3nodeClassifier {
   public double[][] weights; 
    public double[][] bias;
    private double[][] z;
    private double[][] outputs;
    private double learningRate;


    //Constructor used for training
      _3nodeClassifier( DataRead Data, double learningRat){
          try{
        // Declares Data variables
        weights=new double[3][4];
        bias= new double[3][1];
        // z = Hypothesis result for all inputs [ m data examples][nodes in neural network]
        z= new double[Data.data.length][3];
        //        //outputs = actual classes of the inputs [m=training examples][classes as vector i.e 3=[0,0,1}]
        outputs = new double[Data.outputs.length][3];
        learningRate=learningRat;

       //Turning scalar classes into vector
       for(int i = 0; i< outputs.length;i++){
           for(int j = 0 ; j<outputs[0].length;j++){
               outputs[i][j]=0;
           }
           outputs[i][(int)Data.outputs[i]-1]=1;
       }

       //weight initialization
        for(int i=0;i<weights.length;i++){
           for(int j=0; j<weights[i].length;j++){
               weights[i][j]=random()*2;
           }
           bias[i][0]=random();
        }        
     
    }catch (Exception e){
              System.err.println("Error: " + e.getMessage());
              System.exit(0);
          }
          }

      //Constructor used for template weights
_3nodeClassifier( double LearningRat){
     weights= new double[3][4];
        bias= new double[3][1];
              //weight initialization
        for(int i=0;i<weights.length;i++){
           for(int j=0; j<weights[i].length;j++){
               weights[i][j]=random()*2;
           }
           bias[i][0]=random();
        }
    z=null;
     outputs=null;
     learningRate=LearningRat;
          }

    public void PerformTraining(int epoch, DataRead Data){
          try{
        //Perform Training process
        /*
        First loop = epoch
        Second loop iterates for every training example
        Third loop in each training example classifies the inputs according to its weights for each nodes
         */
      for(int num=0;num<epoch;num++) {

        for (int i= 0; i<Data.data.length;i++){

            for(int j=0; j< z[i].length;j++) {
                z[i][j] = z(Data.data, i,j);
            }
            //Normalizes the node values using SoftMax
            z[i]=softMax(z[i]);
        }
        // After getting classifications for each training example adjust the weights then reiterate
        this.train(Data.data);    
      }
    }catch (Exception e){
              System.err.println("Error: " + e.getMessage());
              System.exit(0);
          }}

    public  void Classifying(double[] test){
          try{
        //Testing part
        //double[] test = new double[]{5.9, 3.0, 5.1, 1.8};
        double[] testAnswers= classify(test);

        for (double testAnswer : testAnswers) {
            System.out.println(testAnswer);
        }
    }catch (Exception e){
              System.err.println("Error: " + e.getMessage());
              System.exit(0);
          }}

    //Hypothesis function aka. Classifier (for training)
    private   double z(double[][] x, int i,int k){
        double sum=bias[k][0];
        for(int j=0;j<x[i].length;j++) sum += weights[k][j] * x[i][j];
        return sum;
    }

    //Hypothesis function for testing
   public  double[] classify( double[] test){
        double[] answers= new double[3];

        for(int i = 0 ; i<answers.length;i++)
            answers[i] = bias[i][0] + weights[i][0] * test[0] + weights[i][1] * test[1] + weights[i][2] * test[2] + weights[i][3] * test[3];

        
        return softMax(answers);


    }

    private void train(double[][] x) {
        //Temporary variables to assign the adjusted weights after adjusting process
        double[][] temp = new double[weights.length][weights[0].length];
        double[][] tempBias=new double[weights.length][1];

        for(int i =0 ; i<weights.length;i++){
            //Exclusive loss function for bias since it does not have variable with it
            tempBias[i][0]=bias[i][0]-learningRate*sum(x,i)/x.length;
            for(int j = 0 ; j<weights[i].length;j++){
                //Adjust the weights using gradient descent(partial derivative of loss function by for each weight)
                temp[i][j]=weights[i][j]-(learningRate*sum(x,j,i))/x.length;
            }
        }

        //Assign temporary values to actual weights
        for(int i =0 ; i<weights.length;i++){
            bias[i][0]=tempBias[i][0];
            System.arraycopy(temp[i], 0, weights[i], 0, weights[i].length);
        }
    }


    //Loss function after partial derivative by weight
    private double sum( double[][] x, int j, int k ){
        double sum = 0;
        for(int i = 0 ; i< x.length; i++){
            sum+=(z[i][k]-outputs[i][k])*x[i][j];
        }
        return sum;
    }

    //Exclusive loss function for bias
    private double sum( double[][] x, int k ){
        double sum = 0;
        for(int i = 0 ; i< x.length; i++){
            sum+=(z[i][k]-outputs[i][k]);
        }
        return sum;
    }

    //Softmax Function ( Self explanatory)
    private double[] softMax(double[] x) {
        double sum=0;
        for (double aX : x) {
            sum += exp(aX);
        }
        for(int i=0;i<x.length;i++){
            x[i]=exp(x[i])/sum;
        }
        return x;
    }


    //Prints weight values
    public  void weightReport(){
        for (double[] weight : weights) {
        	System.out.print(bias);
            for (int j = 0; j < weight.length; j++) {

                System.out.print(weight[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //Returns String of weights, rows are separated by ";" and columns are separated by ","
     public  String weightToString(){
         String result="";
        for (int i=0;i<weights.length;i++) {
        	result+=bias[i][0]+",";
            for (int j = 0; j < weights[i].length; j++) {

                result+=weights[i][j] + ",";
            }
            result+=";";
        }
        return result;
    }
    
     //Used for directly assigning weights
    public void weightDirectAssign(double[][] assign, double[][] bia) {
    	weights=assign;
    	bias= bia;
    }
}
