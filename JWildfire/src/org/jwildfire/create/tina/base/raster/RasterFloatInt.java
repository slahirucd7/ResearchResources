/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2017 Andreas Maschke

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
package org.jwildfire.create.tina.base.raster;

import java.io.Serializable;

import org.jwildfire.create.tina.base.Flame;
import org.jwildfire.create.tina.render.LightViewCalculator;
import org.jwildfire.create.tina.render.PlotSample;
import org.jwildfire.create.tina.render.ZBufferSample;
import org.jwildfire.create.tina.render.filter.FilterKernel;

public class RasterFloatInt implements AbstractRaster, Serializable {
  private static final long serialVersionUID = 1L;
  protected float red[][];
  protected float green[][];
  protected float blue[][];
  protected int count[][];
  protected int rasterWidth, rasterHeight;
  private int oversample;
  private double sampleDensity;
  private FilterKernel filterKernel;
  private final int filterOversample = 1;
  private Flame flame;

  @Override
  public void incCount(int pX, int pY) {
    count[pX][pY]++;
  }

  @Override
  public void allocRaster(Flame pFlame, int pWidth, int pHeight, int pOversample, double pSampleDensity) {
    flame = pFlame;
    rasterWidth = pWidth;
    rasterHeight = pHeight;
    oversample = pOversample;
    sampleDensity = pSampleDensity;
    red = new float[pWidth][pHeight];
    green = new float[pWidth][pHeight];
    blue = new float[pWidth][pHeight];
    count = new int[pWidth][pHeight];
  }

  @Override
  public void readRasterPoint(int pX, int pY, RasterPoint pDestRasterPoint) {
    pDestRasterPoint.clear();
    pDestRasterPoint.count = count[pX][pY];
    if(pDestRasterPoint.count < 0) {
      pDestRasterPoint.clear();
    }
    else {
      pDestRasterPoint.red = red[pX][pY];
      pDestRasterPoint.green = green[pX][pY];
      pDestRasterPoint.blue = blue[pX][pY];
    }
  }

  @Override
  public void readRasterPointSafe(int pX, int pY, RasterPoint pDestRasterPoint) {
    if (pX >= 0 && pX < rasterWidth && pY >= 0 && pY < rasterHeight)
      readRasterPoint(pX, pY, pDestRasterPoint);
    else
      pDestRasterPoint.clear();
  }

  @Override
  public /*synchronized*/ void addSamples(PlotSample[] pPlotBuffer, int pCount) {
    for (int i = 0; i < pCount; i++) {
      PlotSample sample = pPlotBuffer[i];
      int x = sample.screenX, y = sample.screenY;
      red[x][y] += (float) sample.r;
      green[x][y] += (float) sample.g;
      blue[x][y] += (float) sample.b;
      count[x][y]++;
    }
  }

  @Override
  public void finalizeRaster() {
    // EMPTY
  }

  @Override
  public void addShadowMapSamples(int pShadowMapIdx, PlotSample[] pPlotBuffer, int pCount) {
    // EMPTY
  }

  @Override
  public void notifyInit(LightViewCalculator lightViewCalculator) {
    // EMPTY    
  }

  @Override
  public void readZBuffer(int pX, int pY, ZBufferSample pDest) {
    // EMPTY    
  }

  @Override
  public void readZBufferSafe(int pX, int pY, ZBufferSample pDest) {
    if (pX >= 0 && pX < rasterWidth && pY >= 0 && pY < rasterHeight)
      readZBuffer(pX, pY, pDest);
    else
      pDest.clear();
  }

  @Override
  public LightViewCalculator getLightViewCalculator() {
    // EMPTY    
    return null;
  }

  @Override
  public int getRasterWidth() {
    return rasterWidth;
  }

  @Override
  public int getRasterHeight() {
    return rasterHeight;
  }

  @Override
  public int getOversample() {
    return oversample;
  }

  @Override
  public double getSampleDensity() {
    return sampleDensity;
  }

}
