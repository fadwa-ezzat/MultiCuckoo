package sim.portrayal3d;

import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.TransformGroup;
import sim.display.GUIState;
import sim.display3d.Display3D;
import sim.portrayal.Portrayal;

public abstract interface Portrayal3D extends Portrayal
{
  public abstract TransformGroup getModel(Object paramObject, TransformGroup paramTransformGroup);

  public abstract PolygonAttributes polygonAttributes();

  public abstract void setCurrentDisplay(Display3D paramDisplay3D);

  public abstract Display3D getCurrentDisplay();

  public abstract GUIState getCurrentGUIState();
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.Portrayal3D
 * JD-Core Version:    0.6.2
 */