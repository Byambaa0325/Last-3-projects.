// made by Daniel shiffman
class World {

  ArrayList<Animal> population;    // An arraylist for all the creatures
  Food food;

  // Constructor
  World(int num) {
    // Start with initial food and creatures
    food = new Food(num);
    population = new ArrayList<Animal>();              // Initialize the arraylist
    for (int i = 0; i < num; i++) {
      PVector l = new PVector(random(width), random(height));
      DNA dna = new DNA();
      int c= int(random(1, 4));
      if (c==1) {
        population.add(new Tiger(l, dna));
      }
      if (c==2) {
        population.add(new Wolf(l, dna));
      }
      if (c==3) {
        population.add(new Rabbit(l, dna));
      }
      if (c==4) {
        population.add(new Deer(l, dna));
      }
    }
  }

  // Make a new creature
  void born(float x, float y) {
    PVector l = new PVector(x, y);
    DNA dna = new DNA();
    population.add(new Tiger(l, dna));
  }

  // Run the world
  void run() {
    // Deal with food
    food.run();

    // Cycle through the ArrayList backwards b/c we are deleting
    for (int i = population.size()-1; i >= 0; i--) {
      // All bloops run and eat
      Animal b = population.get(i);
      ArrayList<Animal> scan= new ArrayList<Animal>(b.scan(population));
      b.steer(scan);
      b.run();
      b.eat(food, scan);
      // If it's dead, kill it and make food
      if (b.dead()) {
        population.remove(i);
        food.add(b.location);
      }

      // Perhaps this bloop would like to make a baby?
      for (int j=population.size()-1; j>=0; j--)
      {
        Animal k= population.get(j);
        if (j==i) {
          break;
        }
        if (k.type!=b.type)
        {
          break;
        }
        float d = PVector.dist(k.location, b.location);
        if (d<b.size/2) {
          Animal child = b.reproduce(k);
          if (child != null) population.add(child);
        }
      }
    }
  }
}
