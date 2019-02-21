package sim.engine;

public abstract interface MakesSimState
{
  public abstract SimState newInstance(long paramLong, String[] paramArrayOfString);

  public abstract Class simulationClass();
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.MakesSimState
 * JD-Core Version:    0.6.2
 */