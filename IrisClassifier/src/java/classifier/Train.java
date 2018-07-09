package classifier;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Byambaa Bayarmandakh
 */
@Path("/train")
 public class Train{
    _3nodeClassifier Network= null;
    DataRead Data;
    
    
    @POST    
     public String Training(@QueryParam("dir") String dir, @QueryParam("NoTE") int size,@QueryParam("E") int epoch, @QueryParam("LR") double LR){
  
        Data= new DataRead(dir, size);
        Network= new _3nodeClassifier( Data,LR);
        Network.PerformTraining(epoch, Data); 
        return Network.weightToString();
     }
     
     @POST
     @Path("/SOW")
     public String SOW(){
       
         Network= new _3nodeClassifier( 0.7);
         
            double[][] optimalBias= new double[][]{{1.397346375925213},{11.815224535861178},{-12.1863745338508}};
            double[][] optimalWeights= new double[][] {{4.022679204300042, 7.8463344287843295, -7.573723431094632, -2.8508701756532124}, 
    	    	{0.8211064766642499, 1.309391238555934, 2.47908625542379, -4.041320314880154}, 
    	    	{-2.1834026714747066, -4.039303613363506, 9.788668419372405, 9.123653599438443} };
            Network.weightDirectAssign(optimalWeights, optimalBias);
            return Network.weightToString();
     }
     
     @GET
     @Path("/predict")
     @Produces(MediaType.APPLICATION_JSON)
    public JsonObject predict(@QueryParam("sl") double sl, @QueryParam("sw") double sw, @QueryParam("pl") double pl, @QueryParam("pw") double pw ,@QueryParam("weights") String weights) {
        
    	double[] x= new double[] {sl,sw,pl,pw};
    	double[] answers = new double[3];      
    	String[] weight1= weights.split(";");
        String[][] parts= new String[3][5];
       parts[0]= weight1[0].split(",");
           parts[1]= weight1[1].split(",");
       parts[2]= weight1[2].split(",");
       if(Network==null){
           Network= new _3nodeClassifier(0.7);
       }
                for(int i=0;i<parts.length;i++){
                    Network.bias[i][0]=Double.parseDouble(parts[i][0]);
                    for(int j=1;j<parts[i].length;j++){
                        Network.weights[i][j-1]=Double.parseDouble(parts[i][j]);
                    }
                }
    	 answers= Network.classify(x);
    	    JsonObjectBuilder factory = Json.createObjectBuilder();
    	  JsonObject jsonObject = factory.build();      
    	if(answers[0]>=answers[1]) {
    		if (answers[0]>=answers[2]) {    			             
 jsonObject = factory.add("Probabilities",factory.add("Iris-setosa",answers[0]).add("Iris-versicolor",answers[1]).add("Iris-virginica",answers[2])).add("class", "Iris-setosa").build();    			
    		}
    		else {
    			jsonObject = factory.add("Probabilities",factory.add("Iris-setosa",answers[0]).add("Iris-versicolor",answers[1]).add("Iris-virginica",answers[2])).add("class", "Iris-virginica").build();
    		} }
        
    	else if(answers[1]>=answers[2]) {
    		jsonObject = factory.add("Probabilities",factory.add("Iris-setosa",answers[0]).add("Iris-versicolor",answers[1]).add("Iris-virginica",answers[2])).add("class", "Iris-versicolor").build();
    	}
    	else {
    		jsonObject = factory.add("Probabilities",factory.add("Iris-setosa",answers[0]).add("Iris-versicolor",answers[1]).add("Iris-virginica",answers[2])).add("class", "Iris-virginica").build();
    	}       
    	
        return jsonObject;  
    }
     

}