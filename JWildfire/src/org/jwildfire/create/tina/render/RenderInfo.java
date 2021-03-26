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
package org.jwildfire.create.tina.render;

import java.io.Serializable;

import org.jwildfire.create.tina.base.raster.AbstractRaster;
import org.jwildfire.create.tina.edit.Assignable;

public class RenderInfo implements Assignable<RenderInfo>, Serializable {
  private static final long serialVersionUID = 1L;
  private boolean renderImage = true;
  private boolean renderHDR;
  private boolean renderZBuffer;
  private boolean storeRaster;
  private transient AbstractRaster restoredRaster;
  private int imageWidth;
  private int imageHeight;
  private RenderMode renderMode = RenderMode.PRODUCTION;
  private double segmentRenderingCamXModifier = 0.0;
  private double segmentRenderingCamYModifier = 0.0;

  protected RenderInfo() {

  }

  // Legacy constructor, still used by scripts
  public RenderInfo(int pImageWidth, int pImageHeight) {
    this(pImageWidth, pImageHeight, RenderMode.PRODUCTION);
  }

  public RenderInfo(int pImageWidth, int pImageHeight, RenderMode pRenderMode) {
    imageWidth = pImageWidth;
    imageHeight = pImageHeight;
    renderMode = pRenderMode;
  }

  public boolean isRenderHDR() {
    return renderHDR;
  }

  public void setRenderHDR(boolean renderHDR) {
    this.renderHDR = renderHDR;
  }

  public int getImageWidth() {
    return imageWidth;
  }

  public void setImageWidth(int imageWidth) {
    this.imageWidth = imageWidth;
  }

  public int getImageHeight() {
    return imageHeight;
  }

  public void setImageHeight(int imageHeight) {
    this.imageHeight = imageHeight;
  }

  @Override
  public void assign(RenderInfo pSrc) {
    renderImage = pSrc.renderImage;
    renderHDR = pSrc.renderHDR;
    renderZBuffer = pSrc.renderZBuffer;
    renderMode = pSrc.renderMode;
    imageWidth = pSrc.imageWidth;
    imageHeight = pSrc.imageHeight;
    storeRaster = pSrc.storeRaster;
    restoredRaster = pSrc.restoredRaster;
  }

  @Override
  public RenderInfo makeCopy() {
    RenderInfo res = new RenderInfo();
    res.assign(this);
    return res;
  }

  @Override
  public boolean isEqual(RenderInfo pSrc) {
    if (renderImage != pSrc.renderImage || renderHDR != pSrc.renderHDR ||
        renderZBuffer != pSrc.renderZBuffer || renderMode != pSrc.renderMode ||
        imageWidth != pSrc.imageWidth || imageHeight != pSrc.imageHeight ||
        storeRaster != pSrc.storeRaster || restoredRaster != pSrc.restoredRaster) {
      return false;
    }
    return true;
  }

  public RenderMode getRenderMode() {
    return renderMode;
  }

  public void setRenderMode(RenderMode renderMode) {
    this.renderMode = renderMode;
  }

  public boolean isRenderZBuffer() {
    return renderZBuffer;
  }

  public void setRenderZBuffer(boolean renderZBuffer) {
    this.renderZBuffer = renderZBuffer;
  }

  public boolean isRenderImage() {
    return renderImage;
  }

  public void setRenderImage(boolean renderImage) {
    this.renderImage = renderImage;
  }

  public boolean isStoreRaster() {
    return storeRaster;
  }

  public void setStoreRaster(boolean storeRaster) {
    this.storeRaster = storeRaster;
  }

  public AbstractRaster getRestoredRaster() {
    return restoredRaster;
  }

  public void setRestoredRaster(AbstractRaster restoredRaster) {
    this.restoredRaster = restoredRaster;
  }

  public double getSegmentRenderingCamXModifier() {
    return segmentRenderingCamXModifier;
  }

  public void setSegmentRenderingCamXModifier(double segmentRenderingCamXModifier) {
    this.segmentRenderingCamXModifier = segmentRenderingCamXModifier;
  }

  public double getSegmentRenderingCamYModifier() {
    return segmentRenderingCamYModifier;
  }

  public void setSegmentRenderingCamYModifier(double segmentRenderingCamYModifier) {
    this.segmentRenderingCamYModifier = segmentRenderingCamYModifier;
  }
}
