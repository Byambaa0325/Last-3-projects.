class Deer implements Prey {
  DNA dna;
  Perceptron brain;

  PVector location;
  PVector velocity;
  PVector acceleration;
  PVector desired; 
  float type=2;
  float range;
  float health;
  float maxspeed;
  float accelerationRate;

  float size;

  Deer ( PVector l, DNA dna_)
  {
    location = l.get();
    dna = dna_;
    velocity= new PVector(0, 0);
    acceleration= new PVector(0, 0);
    brain = new Perceptron ( 0.001*dna.genes[2]);
    health = 200*dna.genes[0];

    maxspeed = map(dna.genes[1], 0, 1, 50, 0);
    accelerationRate=maxspeed*dna.genes[1]; 
    size = map(dna.genes[0], 0, 1, 0, 15);
    range=400*dna.genes[2]+200;
  }

  Deer reproduce(Deer b) {
    Deer ne= new Deer(b.location, dna.copy(b.dna));
    ne.dna.mutate(0.02);
    return ne;
  }

  ArrayList<Animal> scan(ArrayList<Animal> pop) {
    ArrayList<Animal> targets= new ArrayList<Animal>();
    for (int i=0; i<pop.size(); i++)
    {
      float d = PVector.dist((pop.get(i)).location, location);
      if (d<range&&(pop.get(i)).type ==1) {
        targets.add(pop.get(i));
      }
    }
    return targets;
  }

  void run() {
    update();
    borders();
    display();
  }

  void steer(ArrayList<Animal> targets) {
    // Make an array of forces
    PVector[] forces = new PVector[targets.size()];

    // Steer towards all targets
    for (int i = 0; i < forces.length; i++) {
      forces[i] = seek((targets.get(i)).location);
    }

    // That array of forces is the input to the brain
    PVector result = brain.feedforward(forces);
    //println(result);
    result = new PVector(-result.x, -result.y);

    // Use the result to steer the vehicle
    applyForce(result);

    // Train the brain according to the error
    desired = new PVector(location.x*2, location.y*2);
    PVector error = PVector.sub(desired, location);
    brain.train(forces, error);
  }
  void applyForce(PVector force) {
    // We could add mass here if we want A = F / M
    acceleration.add(force);
  }

  // A method that calculates a steering force towards a target
  // STEER = DESIRED MINUS VELOCITY
  PVector seek(PVector target) {
    PVector desired = PVector.sub(target, location);  // A vector pointing from the location to the target
    //println(desired);
    // Normalize desired and scale to maximum speed
    desired.normalize();
    desired.mult(maxspeed);
    //println(desired);
    // Steering = Desired minus velocity
    PVector steer = PVector.sub(desired, velocity);
    //println(steer);
    steer.limit(accelerationRate);  // Limit to maximum steering force
    //println(steer);
    return steer;
  }

  void update() {
    velocity.add(acceleration);
    velocity.limit(maxspeed);
    location.add(velocity);    
    acceleration.mult(0);

    // Death always looming
    health -= 0.02;
  }

  void display() {       
    stroke(0, health);
    fill(255, 0, health);
    rect(location.x, location.y, size, size);
  }

  void eat(Food f, ArrayList<Animal> targets) {
    ArrayList<PVector> food = f.getFood();
    // Are we touching any food objects?
    for (int i = food.size()-1; i >= 0; i--) {
      PVector foodLocation = food.get(i);
      float d= PVector.dist(location, foodLocation);     
      // If we are, juice up our strength!
      if (d < size/2) {
        health += 100; 
        food.remove(i);
      }
    }
  }

  void borders() {
    if (location.x < -size) location.x = width+size;
    if (location.y < -size) location.y = height+size;
    if (location.x > width+size) location.x = -size;
    if (location.y > height+size) location.y = -size;
  }

  boolean dead() {
    if (health < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}
