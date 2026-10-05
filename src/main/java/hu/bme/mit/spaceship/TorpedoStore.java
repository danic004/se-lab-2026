package hu.bme.mit.spaceship;

import java.util.Random;

/**
* Class storing and managing the torpedoes of a ship
*
* (Deliberately contains bugs.)
*/
public class TorpedoStore {

  // rate of failing to fire torpedos [0.0, 1.0]
  private double FAILURE_RATE = 0.0; //NOSONAR

  private int torpedoCount = 0;

  //Declaring the random generator at class level. Thus reducing the chance of Seed Collision in which case it would generate the same random if called in rapid succession
  //Private so that it isn't accessible from other objects and final so that it can't be reassigned later down the road (generator = new Random()) - once again mitigating the chance of seed collision
  private final Random generator = new Random();
  
  public TorpedoStore(int numberOfTorpedos){
    this.torpedoCount = numberOfTorpedos;

    // update failure rate if it was specified in an environment variable
    String failureEnv = System.getenv("IVT_RATE");
    if (failureEnv != null){
      try {
        FAILURE_RATE = Double.parseDouble(failureEnv);
      } catch (NumberFormatException nfe) {
        FAILURE_RATE = 0.0;
      }
    }
  }

  public boolean fire(int numberOfTorpedos){
    if(numberOfTorpedos < 1 || numberOfTorpedos > this.torpedoCount){
      new IllegalArgumentException("numberOfTorpedos");
    }

    boolean success = false;

    // simulate random overheating of the launcher bay which prevents firing
    double r = generator.nextDouble();

    if (r >= FAILURE_RATE) {
      // successful firing
      this.torpedoCount -= numberOfTorpedos;
      success = true;
    } else {
      // simulated failure
      success = false;
    }

    return success;
  }

  public boolean isEmpty(){
    return this.torpedoCount <= 0;
  }

  public int getTorpedoCount() {
    return this.torpedoCount;
  }
}
