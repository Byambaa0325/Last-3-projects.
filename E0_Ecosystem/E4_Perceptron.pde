class Perceptron {
  float weights;  // Array of weights for inputs
  float c;          // learning constant

  // Perceptron is created with n weights and learning constant
  Perceptron( float c_) {
    
    c = c_;
    // Start with random weights
  
      weights = random(0, 1);
    }
  

  // Function to train the Perceptron
  // Weights are adjusted based on vehicle's error
  void train(PVector[] forces, PVector error) {
    for (int i = 0; i < forces.length; i++) {
      weights += c*error.x*forces[i].x;         
      weights += c*error.y*forces[i].y;
      weights = constrain(weights, 0, 1);
    }
    //println(weights);
  }

  // Give me a steering result
  PVector feedforward(PVector[] forces) {
    // Sum all values
    PVector sum = new PVector();
    for (int i = 0; i < forces.length; i++) {
      forces[i].mult(weights);
      //println(forces[i]);
      sum.add(forces[i]);
    }
    //println(sum);
    return sum;
  }
}
