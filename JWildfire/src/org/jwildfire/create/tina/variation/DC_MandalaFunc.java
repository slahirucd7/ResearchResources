package org.jwildfire.create.tina.variation;

import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;
import org.jwildfire.create.tina.palette.RGBColor;
import org.jwildfire.create.tina.palette.RGBPalette;

import js.glsl.G;
import js.glsl.vec2;
import js.glsl.vec3;



public class DC_MandalaFunc  extends DC_BaseFunc {

	/*
	 * Variation : dc_mandala
	 * Date: February 13, 2019
	 * Autor: Jesus Sosa
	 */


	private static final long serialVersionUID = 1L;


	private static final String PARAM_MX = "mX";

	private static final String PARAM_MY = "mY"; 
	private static final String PARAM_SCALE = "scale"; 
	private static final String PARAM_SIDES = "sides"; 
	private static final String PARAM_MULTIPLY = "multiply"; 
	private static final String PARAM_LOOPS = "loops"; 
	private static final String PARAM_IR = "iR"; 
	private static final String PARAM_IG = "iG"; 
	private static final String PARAM_IB = "iB"; 




	double mX=0.025;
    double mY=-0.001245675;
    double scale=2.;
    int sides=12;
    int multiply=1;
    int loops=64;
    
    int iR=0;
    int iG=0;
    int iB=1;


	private static final String[] additionalParamNames = { PARAM_MX,PARAM_MY,PARAM_SCALE,PARAM_SIDES,PARAM_MULTIPLY,PARAM_LOOPS,PARAM_IR,PARAM_IG,PARAM_IB};

	    



/*	@Override
	public void initOnce(FlameTransformationContext pContext, Layer pLayer, XForm pXForm, double pAmount) {
		super.initOnce(pContext, pLayer, pXForm, pAmount);

	}	 

	@Override
	public void init(FlameTransformationContext pContext, Layer pLayer, XForm pXForm, double pAmount) 
	{

	}*/
	

 	public vec3 getRGBColor(double xp,double yp)
 	{

        
        
        double rcpi=0.318309886183791;	
     
        vec2 uv=new vec2(0.0,0.0);
     	uv.x = (5.5-scale)*(xp);
     	uv.y = (5.5-scale)*( yp );
     		      	
    	double k = Math.PI / (double) sides;
      	vec2 s = G.Kscope(uv,k);
      	vec2 t = G.Kscope(s,k);
      	double v = G.dot(t,s);
    	vec2 u = G.mix(s,t,Math.cos(v));
    	
    	if (multiply>0)
    	{
          vec2 t1=new vec2(u.y,u.x);
          vec2 t2=G.mod(t1,(double) multiply);
          vec2 t3=new vec2(-u.x,-u.y);
          vec2 t4=new vec2(u.y,u.x);
          vec2 t5=t4.plus(G.mod(t2,t3));
          u=new vec2(t5.y,t5.x);
    	}
      	vec3 p = new vec3 (u, mX*v);
      	for (int l = 0; l < 73; l++) {
      		if ((double)l > loops)
      		{ 
      		  break;
      	    }
      		vec3 t1= new vec3(1.3,0.999, 0.678);
            vec3 t2= G.abs(p).division(G.dot(p,p)).minus(new vec3(1.0,1.02, mY*rcpi) );
        	vec3 t3= G.abs(t2);
        	vec3 t4=t1.multiply(t3);
            p=new vec3(t4.x,t4.z,t4.y);
      	}
      	if (iR==1)
      	{ 
      	  p.x = 1.0-p.x; 
      	}
      	if (iG==1)
      	{
      	  p.y = 1.0-p.y;
        }
      	if (iB==1)
      	{
      	 p.z = 1.0-p.z;
      	}      
        return p;
 	}

 	
	public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) 
	{

        vec3 color=new vec3(0.0); 
		 vec2 uV=new vec2(0.),p=new vec2(0.);
	       int[] tcolor=new int[3];  


		 
	     if(colorOnly==1)
		 {
			 uV.x=pAffineTP.x;
			 uV.y=pAffineTP.y;
		 }
		 else
		 {
	   			 uV.x=2.0*pContext.random()-1.0;
				 uV.y=2.0*pContext.random()-1.0;
		}
        
        color=getRGBColor(uV.x,uV.y);
        tcolor=dbl2int(color);
        
        //z by color (normalized)
        double z=greyscale(tcolor[0],tcolor[1],tcolor[2]);
        
        if(gradient==0)
        {
  	  	
    	  pVarTP.rgbColor  =true;;
    	  pVarTP.redColor  =tcolor[0];
    	  pVarTP.greenColor=tcolor[1];
    	  pVarTP.blueColor =tcolor[2];
    		
        }
        else if(gradient==1)
        {

            	Layer layer=pXForm.getOwner();
            	RGBPalette palette=layer.getPalette();      	  
          	    RGBColor col=findKey(palette,tcolor[0],tcolor[1],tcolor[2]);
          	    
          	  pVarTP.rgbColor  =true;;
          	  pVarTP.redColor  =col.getRed();
          	  pVarTP.greenColor=col.getGreen();
          	  pVarTP.blueColor =col.getBlue();

        }
        else 
        {
        	pVarTP.color=z;
        }

        pVarTP.x+= pAmount*(uV.x);
        pVarTP.y+= pAmount*(uV.y);
        
        
	    double dz = z * scale_z + offset_z;
	    if (reset_z == 1) {
	      pVarTP.z = dz;
	    }
	    else {
	      pVarTP.z += dz;
	    }
	}
	

	public String getName() {
		return "dc_mandala";
	}

	public String[] getParameterNames() {
		return joinArrays(additionalParamNames, paramNames);
	}


	public Object[] getParameterValues() { //re_min,re_max,im_min,im_max,
		return  joinArrays(new Object[] { mX,mY,scale,sides,multiply,loops,iR,iG,iB},super.getParameterValues());
	}

	public void setParameter(String pName, double pValue) {
		 if (pName.equalsIgnoreCase(PARAM_MX)) {
			mX = pValue;
		}
		else if (pName.equalsIgnoreCase(PARAM_MY)) {
			mY= pValue;;
		}  
		else if (pName.equalsIgnoreCase(PARAM_SCALE)) {
			scale= pValue;
		} 
		else if (pName.equalsIgnoreCase(PARAM_SIDES)) {
			sides= (int) pValue;
		} 
		else if (pName.equalsIgnoreCase(PARAM_MULTIPLY)) {
			multiply= (int) pValue;
		} 
		else if (pName.equalsIgnoreCase(PARAM_LOOPS)) {
			loops= (int) pValue;
		} 
		else if (pName.equalsIgnoreCase(PARAM_IR)) {
			iR= (int)Tools.limitValue(pValue, 0 , 1);
		} 
		else if (pName.equalsIgnoreCase(PARAM_IG)) {
			iG= (int)Tools.limitValue(pValue, 0 , 1);
		} 
		else if (pName.equalsIgnoreCase(PARAM_IB)) {
			iB= (int)Tools.limitValue(pValue, 0 , 1);
		} 
		else
			super.setParameter(pName, pValue);
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

