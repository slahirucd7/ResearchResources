package org.jwildfire.create.tina.variation;

import static org.jwildfire.base.mathlib.MathLib.M_2PI;
import static org.jwildfire.base.mathlib.MathLib.cos;
import static org.jwildfire.base.mathlib.MathLib.sin;

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
import js.glsl.vec4;



public class  CutHexTruchetFlowFunc  extends VariationFunc  {

	/*
	 * Variation : cut_hextruchetflow
	 * Autor: Jesus Sosa
	 * Date: September 25, 2019
	 * Reference & Credits: https://www.shadertoy.com/view/ltVBDw
	 */



	private static final long serialVersionUID = 1L;

	private static final String PARAM_SEED = "randomize";
	private static final String PARAM_MODE = "mode";
	private static final String PARAM_GRID = "grid";
	private static final String PARAM_ZOOM = "zoom";
	private static final String PARAM_INVERT = "invert";


	int mode=1;
	double zoom=10.0;
	private int invert = 0;
	double tol=0.9;


    int seed=0;
    int grid=0;
    double uvx =0.0;
    double uvy =0.0;
    
	private static final String[] additionalParamNames = { PARAM_SEED,PARAM_MODE,PARAM_GRID,PARAM_ZOOM,PARAM_INVERT};

	double pi = 3.14159, sqrt3 = 1.73205;
	double cHashM = 43758.54;
	
	public vec2 PixToHex (vec2 p)
	{
	  vec3 c = new vec3(0.0), r, dr;
	  vec2 t0=new vec2 ((1./sqrt3) * p.x - (1./3.) * p.y, (2./3.) * p.y);
	  
	  c.x =t0.x;
	  c.z= t0.y;
	  c.y = - c.x - c.z;
	  
	  r = G.floor (c.plus( 0.5));
	  dr = G.abs (r.minus(c));
	  vec3 t1=new vec3(dr.y,dr.z,dr.x);
	  vec3 t2=new vec3(dr.z,dr.x,dr.y);
	  	  r = r.minus(G.step (t1, dr).multiply( G.step (t2, dr)).multiply( G.dot (r, new vec3 (1.))));
	  
	  return new vec2(r.x,r.z);
	}

	public vec2 HexToPix (vec2 h)
	{
	  return new vec2 (sqrt3 * (h.x + 0.5 * h.y), 1.5 * h.y);
	}

	public double HexEdgeDist (vec2 p)
	{
	  p = G.abs (p);
	  return (sqrt3/2.) - p.x + 0.5 * G.min (p.x - sqrt3 * p.y, 0.);
	}

	vec2 Rot2D (vec2 q, float a)
	{
	  vec2 cs;
	  cs = G.sin (new vec2 (0.5 * pi, 0.).plus(a));
	  return new vec2 (G.dot (q, new vec2 (cs.x, - cs.y)), G.dot (new vec2(q.y,q.x), cs));
	}



	double Hashfv2 (vec2 p)
	{
	  return G.fract (G.sin (G.dot (p, new vec2 (37., 39.))) * cHashM);
	}

	
	public vec3 ShowScene (vec2 p,int grid)
	{
	  vec3 col=new vec3(0.0), w=new vec3(0.0);
	  vec2 cId, pc;
	  vec2 q;
	  double dir, a, d;
	  cId = PixToHex (p);
	  pc = HexToPix (cId);
	  dir = 2. * G.step (Hashfv2 (cId), 0.5) - 1.;
	  vec2 t0=pc.plus(new vec2 (0., - dir));
	  w.x=t0.x;
	  w.y=t0.y;
	  w.z = G.dot (new vec2(w.x,w.y).minus(p) , new vec2(w.x,w.y).minus(p) );
	  
	  q = pc.plus(new vec2 (sqrt3/2., 0.5 * dir));
	  d = G.dot (q.minus( p), q.minus(p));
	  if (d < w.z) w = new vec3 (q, d);
	  q = pc.plus(new vec2 (- sqrt3/2., 0.5 * dir));
	  d = G.dot (q.minus(p) , q.minus(p));
	  if (d < w.z) w = new vec3 (q, d);
	  w.z = Math.abs (Math.sqrt (w.z) - 0.5);
	  d = HexEdgeDist (p.minus( pc));
	  if(grid==1)
	    col = new vec3 (1., 1., 1.).multiply( G.mix (1., G.smoothstep (1.0, 1., d),G.smoothstep (0.01, 0.02, d)));	  
	  if (w.z < 0.25) {
	    col = new vec3 (1., 1., 1.); 
	  }
	  return col;
	}
	
	public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
		  double x,y,cx,cy;  
		  if(mode==0)
		    {
		      x= pAffineTP.x;
		      y =pAffineTP.y;
		      cx=0.0;
		      cy=0.0;
		    }else
		    {
		     x=pContext.random()-0.5;
		     y=pContext.random()-0.5;
		     cx=0.0;
		     cy=0.0;
		    }

	    uvx= seed*G.sin(seed*180./Math.PI);
	    uvy= seed*G.sin(seed*180.0/Math.PI); //*0.1+time*0.05;
			    
        vec2 uv=new vec2(x*zoom-uvx,y*zoom-uvy);
        vec3 color=new vec3(0.0);
	    color =  color.plus( ShowScene ( (uv.plus( G.step (1.5, 1.0)) ),grid));
	    double col=color.x;   
	    
		pVarTP.doHide=false;
		if(invert==0)
		{
			if (col>tol)
			{ x=0.;
			y=0.;
			pVarTP.doHide = true;
			}
		} else
		{
			if (col<=tol)
			{ x=0.;
			y=0.;
			pVarTP.doHide = true;
			}
		}
		pVarTP.x = pAmount * (x-cx);
		pVarTP.y = pAmount * (y-cy);
		if (pContext.isPreserveZCoordinate()) {
			pVarTP.z += pAmount * pAffineTP.z;
		}
	}

	public String getName() {
		return "cut_hextruchetflow";
	}

	public String[] getParameterNames() {
		return additionalParamNames;
	}


	public Object[] getParameterValues() { 
		return new Object[] { seed,mode,grid,zoom,invert};
	}

	public void setParameter(String pName, double pValue) {
		 if (pName.equalsIgnoreCase(PARAM_SEED)) {
			 seed=(int)pValue;
		}
			else if (pName.equalsIgnoreCase(PARAM_MODE)) {
				   mode =   (int)Tools.limitValue(pValue, 0 , 1);
			}
			else if (pName.equalsIgnoreCase(PARAM_GRID)) {
				grid = (int)Tools.limitValue(pValue, 0 , 1);
			}
		else if (pName.equalsIgnoreCase(PARAM_ZOOM)) {
			zoom = Tools.limitValue(pValue, 0.1 , 50.0);
		}
//		else if (pName.equalsIgnoreCase(PARAM_TOL)) {
//				   tol =   Tools.limitValue(pValue, 0.01 , 0.99);
//		}
		else if (pName.equalsIgnoreCase(PARAM_INVERT)) {
			   invert =   (int)Tools.limitValue(pValue, 0 , 1);
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


