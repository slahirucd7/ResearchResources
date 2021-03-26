package org.jwildfire.create.tina.variation;


import java.util.Random;

import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;
import org.jwildfire.create.tina.palette.RGBColor;
import org.jwildfire.create.tina.palette.RGBPalette;

import js.glsl.G;
import js.glsl.mat2;
import js.glsl.vec2;
import js.glsl.vec3;



public class CutCircleDesignFunc  extends VariationFunc {

	/*
	 * Variation :cut_circdes
	 * Date: august 30, 2019
	 * Author: Jesus Sosa
	 * Reference & Credits: https://www.shadertoy.com/view/ldfBRN
	 * 
	 */


	private static final long serialVersionUID = 1L;



	private static final String PARAM_SEED = "seed";	
	private static final String PARAM_MODE = "mode";	
	private static final String PARAM_TIME = "time";	
	private static final String PARAM_ZOOM = "zoom";
	private static final String PARAM_INVERT = "invert";
	

	int seed=1000;
	int mode=1;
	double time=0.;
    double zoom=10.0;
    int invert=0;


	Random randomize=new Random(seed);
	
 	long last_time=System.currentTimeMillis();
 	long elapsed_time=0;
 	
    
	private static final String[] additionalParamNames = { PARAM_SEED,PARAM_MODE,PARAM_TIME,PARAM_ZOOM,PARAM_INVERT};



	  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
		    double x,y,px_center,py_center;
		    
		    if(mode==0)
		    {
		      x= pAffineTP.x;
		      y =pAffineTP.y;
		      px_center=0.0;
		      py_center=0.0;
		    }else
		    {
		     x=pContext.random();
		     y=pContext.random();
		      px_center=0.5;
		      py_center=0.5;		     
		    }
		    	    
		    vec2 u = new vec2(x*zoom,y*zoom);	
            u=G.fract(u).minus(0.5);
		    double r = .8, d = G.step(G.length(u),r);
		    for (int i = 0; i < 9; i++)
		    {
		        r *= .5 + .1 * Math.sin((double)(i) + time);
		        if ((i % 2) == 0.)
		        {
		        	d -= G.step(G.length(u.plus( new vec2(0, r))), r);
		        	d -= G.step(G.length(u.plus(new vec2(r, 0))), r);
		        	d -= G.step(G.length(u.minus(new vec2(0, r))), r);
		        	d -= G.step(G.length(u.minus(new vec2(r, 0))), r);
		        }
		        else
		        {
		            d += G.step(G.length(u.plus(new vec2(0, r))), r);
		        	d += G.step(G.length(u.plus(new vec2(r, 0))), r);
		        	d += G.step(G.length(u.minus(new vec2(0, r))), r);
		        	d += G.step(G.length(u.minus(new vec2(r, 0))), r);
		        }
		        
		    }
		    double color=d;
		    
		    pVarTP.doHide=false;
		    if(invert==0)
		    {
		      if (color>0.5)
		      { x=0;
		        y=0;
		        pVarTP.doHide = true;	        
		      }
		    } else
		    {
			      if (color<=0.5)
			      { x=0;
			        y=0;
			        pVarTP.doHide = true;
			      }
		    }
		    pVarTP.x = pAmount * (x-px_center);
		    pVarTP.y = pAmount * (y-py_center);
		    if (pContext.isPreserveZCoordinate()) {
		      pVarTP.z += pAmount * pAffineTP.z;
		    }
		  }
	  
	  
	  @Override
	  public void init(FlameTransformationContext pContext, Layer pLayer, XForm pXForm, double pAmount) {
		  randomize=new Random(seed);
	   }

	  
	public String getName() {
		return "cut_circdes";
	}

	public String[] getParameterNames() {
		return (additionalParamNames);
	}

	public Object[] getParameterValues() { //
		return (new Object[] {  seed,mode,time, zoom,invert});
	}

	public void setParameter(String pName, double pValue) {
		if(pName.equalsIgnoreCase(PARAM_SEED))
		{
			   seed =   (int)pValue;
		       randomize=new Random(seed);
		          long current_time = System.currentTimeMillis();
		          elapsed_time += (current_time - last_time);
		          last_time = current_time;
		          time = (double) (elapsed_time / 1000.0);
		}
		else if (pName.equalsIgnoreCase(PARAM_MODE)) {
			mode =(int)Tools.limitValue(pValue, 0 , 1);
		}
		else if (pName.equalsIgnoreCase(PARAM_TIME)) {
			time =pValue;
		}
		else if (pName.equalsIgnoreCase(PARAM_ZOOM)) {
			zoom =pValue;
		}
		else if (pName.equalsIgnoreCase(PARAM_INVERT)) {
			invert =(int)Tools.limitValue(pValue, 0 , 1);
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

