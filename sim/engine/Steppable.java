package sim.engine;

import java.io.Serializable;

public abstract interface Steppable extends Serializable
{
  public abstract void step(SimState paramSimState);
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.Steppable
 * JD-Core Version:    0.6.2
 */