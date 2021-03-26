/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2011 Andreas Maschke

  This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser 
  General Public License as published by the Free Software Foundation; either version 2.1 of the 
  License, or (at your option) any later version.
 
  This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this software; 
  if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jwildfire.create.tina.variation;

import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;

import static org.jwildfire.base.mathlib.MathLib.*;

import org.jwildfire.base.Tools;

public class OctagonFunc extends VariationFunc {
  private static final long serialVersionUID = 1L;

  private static final String PARAM_X = "x";
  private static final String PARAM_Y = "y";
  private static final String PARAM_Z = "z";
  private static final String PARAM_MODE = "mode";
  private static final String[] paramNames = {PARAM_X, PARAM_Y, PARAM_Z, PARAM_MODE};

  private double x = 0.0;
  private double y = 0.0;
  private double z = 0.0;
  private int mode = 1;

  @Override
  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
    /* octagon from FracFx, http://fracfx.deviantart.com/art/FracFx-Plugin-Pack-171806681 */
    double r = sqr(sqr(pAffineTP.x)) + sqr(sqr(pAffineTP.y)) + 2 * sqr(pAffineTP.z);
    if (r == 0.0) r = SMALL_EPSILON;
    double t = fabs(pAffineTP.x) + fabs(pAffineTP.y) + 2 * sqrt((mode > 1) ? fabs(pAffineTP.z) :  pAffineTP.z);
    if (t == 0.0) t = SMALL_EPSILON;
    
    double m = 1.0;
    boolean splits = false;
    
    switch (mode) {
    case 0:
      if (r <= pAmount / 2.0) {
        m = 1.0 + 1.0 / t;
        splits = true;
      } else {
        m = 1.0 / r;
        splits = false;
      }
      break;
    case 1:
      if (r <= pAmount / 2.0) {
        m = 1.0 + 1.0 / t;
        splits = true;
      } else if (pAmount >= 0) {
        m = 1.0 / r + 1.0 / t;
        splits = true;
      } else {
        m = 1.0 + 1.0 / r;
        splits = true;
      }
      break;
    case 2:
      if (r <= pAmount / 2.0) {
        m = 1.0 + 1.0 / t;
        splits = true;
      } else {
        m = 1.0 + 1.0 / r;
        splits = false;
      }
      break;
    case 3:
      if (r <= pAmount / 2.0) {
        m = 1.0 / t;
        splits = true;
      } else {
        m = 1.0 / r;
        splits = false;
      }
      break;
    case 4:
      if (r <= pAmount / 2.0) {
        m = 1.0 / r;
        splits = true;
      } else {
        m = 1.0 / r;
        splits = false;
      }
      break;
    case 5:
      if (t <= pAmount / 2.0) {
        m = 1.0 / t;
        splits = true;
      } else {
        m = 1.0 / r;
        splits = false;
      }
      break;
    case 6:
      if (t <= pAmount / 2.0) {
        m = 1.0 + 1.0 / t;
        splits = true;
      } else {
        m = 1.0 + 1.0 / r;
        splits = false;
      }
      break;
    }
    
    pVarTP.x += pAmount * m * pAffineTP.x;
    pVarTP.y += pAmount * m * pAffineTP.y;
    pVarTP.z += pAmount * m * pAffineTP.z;

    if (splits) {
      if (pAffineTP.x >= 0.0)
        pVarTP.x += pAmount * (pAffineTP.x + this.x);
      else
        pVarTP.x += pAmount * (pAffineTP.x - this.x);
  
      if (pAffineTP.y >= 0.0)
        pVarTP.y += pAmount * (pAffineTP.y + this.y);
      else
        pVarTP.y += pAmount * (pAffineTP.y - this.y);
  
      if (pAffineTP.z >= 0.0)
        pVarTP.z += pAmount * (pAffineTP.z + this.z);
      else
        pVarTP.z += pAmount * (pAffineTP.z - this.z);
    }
  }

  @Override
  public String[] getParameterNames() {
    return paramNames;
  }

  @Override
  public Object[] getParameterValues() {
    return new Object[]{x, y, z, mode};
  }

  @Override
  public void setParameter(String pName, double pValue) {
    if (PARAM_X.equalsIgnoreCase(pName))
      x = pValue;
    else if (PARAM_Y.equalsIgnoreCase(pName))
      y = pValue;
    else if (PARAM_Z.equalsIgnoreCase(pName))
      z = pValue;
    else if (PARAM_MODE.equalsIgnoreCase(pName))
      mode = limitIntVal(Tools.FTOI(pValue), 0, 6);
    else
      throw new IllegalArgumentException(pName);
  }

  @Override
  public String getName() {
    return "octagon";
  }

  @Override
  public String[] getParameterAlternativeNames() {
    return new String[]{"octa_x", "octa_y", "octa_z", "octa_mode"};
  }

}
