// Orginally made by Daniel Shiffman
//Edited by Byambaa Bayarmandakh: added complexity to the genes, which is type of gene.
class DNA {

  // The genetic sequence
  float[] genes;

  // Constructor (makes a random DNA)
  DNA() {
    // DNA is random floating point values between 0 and 1 (!!)
    genes = new float[3];
    /* 
     this.genes[0] is strength
     this.genes[1] is agility
     this.genes[2] is intellect
     */
    for (int i = 0; i < genes.length; i++) {
      genes[i] = random(0, 1);
    }
  }

  DNA(float[] newgenes) {
    genes = newgenes;
  }

  DNA copy(DNA dna_) {
    float[] newgenes = new float[genes.length];
    //arraycopy(genes,newgenes);
    // JS mode not supporting arraycopy
    for (int i = 0; i < newgenes.length; i++) {
      newgenes[i] = (genes[i]+dna_.genes[i])/2;
      
    }
 
    return new DNA(newgenes);
  }

  // Based on a mutation probability, picks a new random character in array spots
  void mutate(float m) {
    for (int i = 0; i < genes.length; i++) {
      if (random(1) < m) {
        genes[i] = random(0, 1);
      }
    }
  }
}
