# Recent projects
This is my recent projects

#1 E0_Ecosystem
(Still in Development)
This is an application of neuroevolution system, which is a form of artificial intelligence that uses evolutionary algorithms to generate artificial neural networks (ANN), parameters, topology and rules.[1]. It is a simulation of an ecosystem with predators and preys with each having 2 types varying in their attributes. Each class that implements Animal interface searches for food, mates with same class objects, the matings are done in an asexual manner, and Predator class exclusively huntes Prey class objects. 

  For the implementation, I have currently added only the Predator and Prey classes and its subclasses. Movement is implemented using steering forces[2]. Each animal has a brain (or perceptron), which receives list of targets from the scanned area then processes the list to adjust steering forces, and a dna, consisting of 3 attributes each modifying behaviors of the animal such as speed, chance of succesfully mating, health, size, and learingRate etc. 
  
  The simulation is done on Processing, an open source programm for visuals.
  
//Will be adding environmental objects to enhance the dynamics of the movement and decision making.


1."Neuroevolution." En.wikipedia.org. N. p., 2018. Web. 9 July 2018.
2. "Steering Behaviors For Autonomous Characters." Red3d.com. N. p., 2018. Web. 9 July 2018. https://www.red3d.com/cwr/steer/gdc99/

#2 Iris-Classifier

Here is my first machine learning project which is multiclass classifier. It is applied on Iris Data Set, perhaps the best known database to be found in the pattern recognition literature[1]. It takes four inputs into each 3 nodes, which has its own weights and guesses exclusively one class, then it guesses the chance. The outputs from 3 nodes converge into softmax probabilistic layer, and the output is an array of 3 numbers each representing the individual possibilities. It uses Gradient Descent to optimize the loss which is found by the Sum-squared loss function. 

  After succesfully finishing the algorithm, I tried to implement it as a webservice in RESTful web servers. It was not top-notch, and I will be coming back to this programm in future.

1. "UCI Machine Learning Repository: Iris Data Set." Archive.ics.uci.edu. N. p., 2018. Web. 9 July 2018. https://archive.ics.uci.edu/ml/datasets/Iris

