package sim.field;

import sim.util.Double3D;

public abstract interface SparseField3D
{
  public abstract Double3D getDimensions();

  public abstract Double3D getObjectLocationAsDouble3D(Object paramObject);
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.SparseField3D
 * JD-Core Version:    0.6.2
 */