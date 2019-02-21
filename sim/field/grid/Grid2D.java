package sim.field.grid;

import java.io.Serializable;
import java.util.Map;
import sim.util.IntBag;

public abstract interface Grid2D extends Serializable
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

  public abstract int tx(int paramInt);

  public abstract int ty(int paramInt);

  public abstract int stx(int paramInt);

  public abstract int sty(int paramInt);

  public abstract int ulx(int paramInt1, int paramInt2);

  public abstract int uly(int paramInt1, int paramInt2);

  public abstract int urx(int paramInt1, int paramInt2);

  public abstract int ury(int paramInt1, int paramInt2);

  public abstract int dlx(int paramInt1, int paramInt2);

  public abstract int dly(int paramInt1, int paramInt2);

  public abstract int drx(int paramInt1, int paramInt2);

  public abstract int dry(int paramInt1, int paramInt2);

  public abstract int upx(int paramInt1, int paramInt2);

  public abstract int upy(int paramInt1, int paramInt2);

  public abstract int downx(int paramInt1, int paramInt2);

  public abstract int downy(int paramInt1, int paramInt2);

  public abstract boolean trb(int paramInt1, int paramInt2);

  public abstract boolean trt(int paramInt1, int paramInt2);

  /** @deprecated */
  public abstract void getNeighborsMaxDistance(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2);

  public abstract void getMooreLocations(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2);

  /** @deprecated */
  public abstract void getNeighborsHamiltonianDistance(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2);

  public abstract void getVonNeumannLocations(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2);

  /** @deprecated */
  public abstract void getNeighborsHexagonalDistance(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2);

  public abstract void getHexagonalLocations(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2);

  public abstract void getRadialLocations(int paramInt1, int paramInt2, double paramDouble, int paramInt3, boolean paramBoolean, IntBag paramIntBag1, IntBag paramIntBag2);

  public abstract void getRadialLocations(int paramInt1, int paramInt2, double paramDouble, int paramInt3, boolean paramBoolean1, int paramInt4, boolean paramBoolean2, IntBag paramIntBag1, IntBag paramIntBag2);

  public abstract Map buildMap(Map paramMap);

  public abstract Map buildMap(int paramInt);
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.Grid2D
 * JD-Core Version:    0.6.2
 */