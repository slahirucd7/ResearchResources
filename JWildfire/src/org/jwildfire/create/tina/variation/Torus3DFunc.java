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

import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;

import js.glsl.G;
import js.glsl.vec2;
import js.glsl.vec3;

public class Torus3DFunc extends VariationFunc {
  private static final long serialVersionUID = 1L;

  
	private static final String PARAM_R1 = "r1";
	private static final String PARAM_R2 = "r2";


// torus3D with signed distance functions
// Author: Jesus Sosa
// Date:   17/july/ 2020
// Reference: 	https://www.iquilezles.org/www/articles/distfunctions/distfunctions.htm
//              https://www.shadertoy.com/view/Xds3zN
	
  double r1=0.50,r2=0.50;


  private static final String[] additionalParamNames = { PARAM_R1,PARAM_R2};
	 
  @Override
  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
    double x = (pContext.random() - 0.5);
    double y = (pContext.random() - 0.5);
    double z = (pContext.random() - 0.5);
    
    vec3 p=new vec3(x,y,z);

    double distance=sdTorus(p,new vec2(r1,r2));
    pVarTP.doHide=true;
    if(distance <0.0)
    {
 	    pVarTP.doHide=false;
    	pVarTP.x=pAmount*x;
    	pVarTP.y=pAmount*y;
    	pVarTP.z=pAmount*z;
    }
  }

  double sdTorus( vec3 p, vec2 t )
  {
      return G.length( new vec2(G.length(new vec2(p.x,p.z))-t.x,p.y) )-t.y;
  }
  
  @Override
  public String getName() {
    return "torus3D";
  }
  
	public String[] getParameterNames() {
		return additionalParamNames;
	}


	public Object[] getParameterValues() { 
		return new Object[] {r1,r2};
	}
//
	public void setParameter(String pName, double pValue) {
		if (pName.equalsIgnoreCase(PARAM_R1)) {
			r1 =Tools.limitValue(pValue, 0.0, 0.5);
		}
		else if (pName.equalsIgnoreCase(PARAM_R2)) {
			r2 =Tools.limitValue(pValue, 0.0, 0.5);
		}
		else 
		      throw new IllegalArgumentException(pName);
	}

	@Override
	public boolean dynamicParameterExpansion() {
		return true;
	}

	@Override
	public boolean dynamicParameterExpansion(String pName) {
		// preset_id doesn't really expand parameters, but it changes them; this will make them refresh
		return true;
	}	

}
