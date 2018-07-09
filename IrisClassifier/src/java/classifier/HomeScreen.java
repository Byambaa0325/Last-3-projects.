package classifier;

import javax.json.*;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


/**
 * REST Web Service 
 * @author Byambaa Bayarmandakh
 * 
 * http://localhost:8080/IrisClassifier/
 * http://localhost:8080/IrisClassifier/TrainPage.html
 * http://localhost:8080/IrisClassifier/webresources/* * 
 * 
 */
@Path("/predict")
public class HomeScreen {
_3nodeClassifier Network;
    DataRead Data;  
    

    /**
     * Retrieves representation of an instance of classifier.HomeScreen
     * @param sl is Sepal length
     * @param sw is Sepal width
     * @param pl is Petal length
     * @param pw is Petal width
     * @return an instance of jsonObject containing class and Probabilities
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJson(@QueryParam("sl") double sl, @QueryParam("sw") double sw, @QueryParam("pl") double pl, @QueryParam("pw") double pw ) {
        
    	double[] x= new double[] {sl,sw,pl,pw};
    	double[] answers = new double[3];
       
    	if(Network==null) {
            setOptimalWeights();
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
  
     public void setOptimalWeights() {
    	if(Network!= null) {
    		 double[][] optimalBias= new double[][]{{1.397346375925213},{11.815224535861178},{-12.1863745338508}};
                double[][] optimalWeights= new double[][] {{4.022679204300042, 7.8463344287843295, -7.573723431094632, -2.8508701756532124}, 
    	    	{0.8211064766642499, 1.309391238555934, 2.47908625542379, -4.041320314880154}, 
    	    	{-2.1834026714747066, -4.039303613363506, 9.788668419372405, 9.123653599438443} };
    		Network.weightDirectAssign(optimalWeights, optimalBias);
    	}
    	else {    		
    		Network= new _3nodeClassifier( 0.7);                
                double[][] optimalBias= new double[][]{{1.397346375925213},{11.815224535861178},{-12.1863745338508}};
                double[][] optimalWeights= new double[][] {{4.022679204300042, 7.8463344287843295, -7.573723431094632, -2.8508701756532124}, 
    	    	{0.8211064766642499, 1.309391238555934, 2.47908625542379, -4.041320314880154}, 
    	    	{-2.1834026714747066, -4.039303613363506, 9.788668419372405, 9.123653599438443} };
    		Network.weightDirectAssign(optimalWeights, optimalBias);
    	}
       	
    }
    
     
     
}
 