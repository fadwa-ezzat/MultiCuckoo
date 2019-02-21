package sim.field;

import sim.util.Double2D;

public abstract interface SparseField2D
{
  public abstract Double2D getDimensions();

  public abstract Double2D getObjectLocationAsDouble2D(Object paramObject);
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.SparseField2D
 * JD-Core Version:    0.6.2
 */