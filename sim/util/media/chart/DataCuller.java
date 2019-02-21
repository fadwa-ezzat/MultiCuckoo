package sim.util.media.chart;

import sim.util.IntBag;

public abstract interface DataCuller
{
  public abstract boolean tooManyPoints(int paramInt);

  public abstract IntBag cull(double[] paramArrayOfDouble, boolean paramBoolean);
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.DataCuller
 * JD-Core Version:    0.6.2
 */