package sim.field.grid;

import java.io.Serializable;
import java.util.Map;
import sim.util.IntBag;

public abstract interface Grid3D extends Serializable
{
  public static final int BOUNDED = 0;
  public static final int UNBOUNDED = 1;
  public static final int TOROIDAL = 2;
  public static final int CENTER = 1024;
  public static final int ALL = 1025;
  public static final int ANY = 1026;
  public static final int ANY_SIZE = 0;

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract int getLength();

  public abstract int tx(int paramInt);

  public abstract int ty(int paramInt);

  public abstract int tz(int paramInt);

  public abstract int stx(int paramInt);

  public abstract int sty(int paramInt);

  public abstract int stz(int paramInt);

  /** @deprecated */
  public abstract void getNeighborsMaxDistance(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2, IntBag paramIntBag3);

  public abstract void getMooreLocations(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2, IntBag paramIntBag3);

  /** @deprecated */
  public abstract void getNeighborsHamiltonianDistance(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2, IntBag paramIntBag3);

  public abstract void getVonNeumannLocations(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2, IntBag paramIntBag3);

  public abstract void getRadialLocations(int paramInt1, int paramInt2, int paramInt3, double paramDouble, int paramInt4, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2, IntBag paramIntBag3);

  public abstract void getRadialLocations(int paramInt1, int paramInt2, int paramInt3, double paramDouble, int paramInt4, boolean paramBoolean1, int paramInt5, boolean paramBoolean2, IntBag paramIntBag1, IntBag paramIntBag2, IntBag paramIntBag3);

  public abstract Map buildMap(Map paramMap);

  public abstract Map buildMap(int paramInt);
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.Grid3D
 * JD-Core Version:    0.6.2
 */