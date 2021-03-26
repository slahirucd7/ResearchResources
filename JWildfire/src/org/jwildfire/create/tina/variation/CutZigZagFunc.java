package org.jwildfire.create.tina.variation;


import java.util.Random;

import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;
import org.jwildfire.create.tina.palette.RGBColor;
import org.jwildfire.create.tina.palette.RGBPalette;

import js.glsl.G;
import js.glsl.vec2;
import js.glsl.vec4;



public class CutZigZagFunc  extends VariationFunc {

	/*
	 * Variation :cut_zigzag
	 * Date: august 29, 2019
	 * Author: Jesus Sosa
	 * Reference:  https://thebookofshaders.com/edit.php#09/zigzag.frag
	 */


	private static final long serialVersionUID = 1L;




	private static final String PARAM_MODE = "mode";
	private static final String PARAM_XPAR = "x_par";	
	private static final String PARAM_YPAR = "y_par";
	private static final String PARAM_ZOOM = "zoom";
	private static final String PARAM_INVERT = "invert";
	
	int mode=1;
	double xpar=1.0;
	double ypar=2.0;
    double zoom=2.0;
    int invert=0;


    
	private static final String[] additionalParamNames = { PARAM_MODE,PARAM_XPAR,PARAM_YPAR,PARAM_ZOOM,PARAM_INVERT};
	 
	
	public vec2 mirrorTile(vec2 _st, double _zoom){
	    _st = _st.multiply(zoom);
	    if (G.fract(_st.y * 0.5) > 0.5){
	        _st.x = _st.x+0.5;
	        _st.y = 1.0-_st.y;
	    }
	    return G.fract(_st);
	}

	public double fillY(vec2 _st, double _pct,double _antia){
	  return  G.smoothstep( _pct-_antia, _pct, _st.y);
	}
	
	
	  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
		    double xp,yp;
		    
		    if(mode==0)
		    {
		      xp= pAffineTP.x;
		      yp =pAffineTP.y;
		    }else
		    {
		     xp=pContext.random()-0.5;
		     yp=pContext.random()-0.5; 		     
		    }
		    
		    
		    vec2 st =new vec2(xp,yp);
		    st = mirrorTile(st.multiply(new vec2(xpar,ypar)),zoom);
		    double x = st.x*2.;
		    double a = G.floor(1.+Math.sin(x*Math.PI));
		    double b = G.floor(1.+Math.sin((x+1.)*Math.PI));
		    double f = G.fract(x);

		    double color = fillY(st,G.mix(a,b,f),0.01) ;
            
             
		    pVarTP.doHide=false;
		    if(invert==0)
		    {
		      if (color>=0.5)
		      { xp=0;
		        yp=0;
		        pVarTP.doHide = true;	        
		      }
		    } else
		    {
			      if (color<0.5)
			      { xp=0;
			        yp=0;
			        pVarTP.doHide = true;
			      }
		    }
		    pVarTP.x = pAmount * (xp);
		    pVarTP.y = pAmount * (yp);
		    if (pContext.isPreserveZCoordinate()) {
		      pVarTP.z += pAmount * pAffineTP.z;
		    }
		  }
	  
	  
	  @Override
	  public void init(FlameTransformationContext pContext, Layer pLayer, XForm pXForm, double pAmount) {
	   }

	  
	public String getName() {
		return "cut_zigzag";
	}

	public String[] getParameterNames() {
		return (additionalParamNames);
	}

	public Object[] getParameterValues() { //
		return (new Object[] {   mode,xpar,ypar,zoom,invert});
	}

	public void setParameter(String pName, double pValue) {

        if (pName.equalsIgnoreCase(PARAM_MODE)) {
			mode =(int)Tools.limitValue(pValue, 0 , 1);
		}
        else if (pName.equalsIgnoreCase(PARAM_XPAR)) {
			xpar = pValue;
		}
		else if(pName.equalsIgnoreCase(PARAM_YPAR))
		{
			   ypar =   pValue;
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

