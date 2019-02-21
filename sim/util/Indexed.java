package sim.util;

public abstract interface Indexed
{
  public abstract Class componentType();

  public abstract int size();

  public abstract Object setValue(int paramInt, Object paramObject)
    throws IndexOutOfBoundsException, IllegalArgumentException;

  public abstract Object getValue(int paramInt)
    throws IndexOutOfBoundsException;
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Indexed
 * JD-Core Version:    0.6.2
 */