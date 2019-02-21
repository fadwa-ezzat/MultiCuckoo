package sim.util.gui;

import java.awt.Color;

public abstract interface ColorMap
{
  public abstract Color getColor(double paramDouble);

  public abstract int getRGB(double paramDouble);

  public abstract int getAlpha(double paramDouble);

  public abstract boolean validLevel(double paramDouble);

  public abstract double defaultValue();
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.ColorMap
 * JD-Core Version:    0.6.2
 */