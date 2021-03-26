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
package org.jwildfire.create.tina.variation.mesh;

import org.jwildfire.base.Tools;
import org.jwildfire.base.mathlib.MathLib;
import org.jwildfire.base.mathlib.VecMathLib.VectorD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleMesh {
  private List<Vertex> vertices = new ArrayList<>();
  private Map<String, Integer> verticesMap = new HashMap<>();
  private List<Face> faces = new ArrayList<>();
  private BoundingBox boundingBox;

  public class BoundingBox {
    private final double xmin, xmax, xcentre;
    private final double ymin, ymax, ycentre;
    private final double zmin, zmax, zcentre;

    public BoundingBox(double xmin, double xmax, double ymin, double ymax, double zmin, double zmax) {
      super();
      this.xmin = xmin;
      this.xmax = xmax;
      this.xcentre = this.xmin + (this.xmax - this.xmin) / 2.0;
      this.ymin = ymin;
      this.ymax = ymax;
      this.ycentre = this.ymin + (this.ymax - this.ymin) / 2.0;
      this.zmin = zmin;
      this.zmax = zmax;
      this.zcentre = this.zmin + (this.zmax - this.zmin) / 2.0;
    }

    public double getXmin() {
      return xmin;
    }

    public double getXmax() {
      return xmax;
    }

    public double getYmin() {
      return ymin;
    }

    public double getYmax() {
      return ymax;
    }

    public double getZmin() {
      return zmin;
    }

    public double getZmax() {
      return zmax;
    }

    public double getXcentre() {
      return xcentre;
    }

    public double getYcentre() {
      return ycentre;
    }

    public double getZcentre() {
      return zcentre;
    }

  }

  private String calculateVertexKey(double x, double y, double z) {
    final double prec = 10000.0;
    return String.valueOf(Tools.FTOI(x * prec)) + "#" + String.valueOf(Tools.FTOI(y * prec)) + "#" + String.valueOf(Tools.FTOI(z * prec));
  }

  public int addVertex(double x, double y, double z) {
    String key = calculateVertexKey(x, y, z);
    Integer idx = verticesMap.get(key);
    if (idx != null) {
      return idx.intValue();
    }
    Vertex p = new Vertex();
    p.x = (float) x;
    p.y = (float) y;
    p.z = (float) z;
    int res = vertices.size();
    vertices.add(p);
    verticesMap.put(key, Integer.valueOf(res));
    boundingBox = null;
    return res;
  }

  public int addVertex(double x, double y, double z, double u, double v) {
    String key = calculateVertexKey(x, y, z);
    Integer idx = verticesMap.get(key);
    if (idx != null) {
      return idx.intValue();
    }
    VertexWithUV p = new VertexWithUV();
    p.x = (float) x;
    p.y = (float) y;
    p.z = (float) z;
    p.u = (float) u;
    p.v = (float) v;
    int res = vertices.size();
    vertices.add(p);
    verticesMap.put(key, Integer.valueOf(res));
    boundingBox = null;
    return res;
  }

  public void addFace(int p1, int p2, int p3) {
    Face f = new Face();
    f.v1 = p1;
    f.v2 = p2;
    f.v3 = p3;
    faces.add(f);
  }

  public void addFace(int p1, int p2, int p3, int p4) {
    Face f1 = new Face();
    f1.v1 = p1;
    f1.v2 = p2;
    f1.v3 = p3;
    faces.add(f1);
    Face f2 = new Face();
    f2.v1 = p1;
    f2.v2 = p3;
    f2.v3 = p4;
    faces.add(f2);
  }
  
  public void addColoredFace(int p1, int p2, int p3, double c) {
    FaceWithColor f = new FaceWithColor();
    f.v1 = p1;
    f.v2 = p2;
    f.v3 = p3;
    f.rgbColor = false;
    f.redColor = c;
    faces.add(f);
  }

  public void addColoredFace(int p1, int p2, int p3, double r, double g, double b) {
    FaceWithColor f = new FaceWithColor();
    f.v1 = p1;
    f.v2 = p2;
    f.v3 = p3;
    f.rgbColor = true;
    f.redColor = r;
    f.greenColor = g;
    f.blueColor = b;
    faces.add(f);
  }

  public void addColoredFace(int p1, int p2, int p3, int p4, double c) {
    FaceWithColor f1 = new FaceWithColor();
    f1.v1 = p1;
    f1.v2 = p2;
    f1.v3 = p3;
    faces.add(f1);
    f1.rgbColor = false;
    f1.redColor = c;
    FaceWithColor f2 = new FaceWithColor();
    f2.v1 = p1;
    f2.v2 = p3;
    f2.v3 = p4;
    f2.rgbColor = false;
    f2.redColor = c;
    faces.add(f2);
  }

  public void addColoredFace(int p1, int p2, int p3, int p4, double r, double g, double b) {
    FaceWithColor f1 = new FaceWithColor();
    f1.v1 = p1;
    f1.v2 = p2;
    f1.v3 = p3;
    faces.add(f1);
    f1.rgbColor = true;
    f1.redColor = r;
    f1.greenColor = g;
    f1.blueColor = b;
    FaceWithColor f2 = new FaceWithColor();
    f2.v1 = p1;
    f2.v2 = p3;
    f2.v3 = p4;
    f2.rgbColor = true;
    f2.redColor = r;
    f2.greenColor = g;
    f2.blueColor = b;
    faces.add(f2);
  }

  public int getFaceCount() {
    return faces.size();
  }

  public Face getFace(int idx) {
    return faces.get(idx);
  }

  public Vertex getVertex(int idx) {
    return vertices.get(idx);
  }

  public void distributeFaces() {
    //System.out.println("VERTICES: " + vertices.size());

    List<Double> areaLst = new ArrayList<>();
    double areaMin = Double.MAX_VALUE, areaMax = 0.0;
    for (Face face : faces) {
      Vertex p1 = getVertex(face.v1);
      Vertex p2 = getVertex(face.v2);
      Vertex p3 = getVertex(face.v3);
      VectorD a = new VectorD(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
      VectorD b = new VectorD(p3.x - p1.x, p3.y - p1.y, p3.z - p1.z);
      double area = VectorD.cross(a, b).length();

      areaLst.add(area);
      if (area < areaMin) {
        areaMin = area;
      }
      if (area > areaMax) {
        areaMax = area;
      }
    }
    if (MathLib.fabs(areaMin - areaMax) > MathLib.EPSILON) {
      int maxFaces = faces.size() < 5000 ? 5000 : 500;
      List<Face> newFaces = new ArrayList<>();
      for (int i = 0; i < faces.size(); i++) {
        Face face = faces.get(i);
        int count = Math.min(Tools.FTOI(areaLst.get(i) / areaMin), maxFaces);
        for (int j = 0; j < count; j++) {
          newFaces.add(face);
        }
      }
      faces = newFaces;
    }
  }

  public void laplaceSmooth(NeightboursList neighbours, double strength) {
    List<Vertex> displacements = new ArrayList<>();
    // Calc displacements
    for (int i = 0; i < vertices.size(); i++) {
      Vertex d = new Vertex();
      displacements.add(d);
      List<Integer> nList = neighbours.getNeighbours(i);
      if (nList.size() > 1) {
        double weight = 1.0 / (double) nList.size();
        Vertex v = getVertex(i);
        for (Integer n : nList) {
          Vertex vn = getVertex(n);
          d.x += (vn.x - v.x) * weight;
          d.y += (vn.y - v.y) * weight;
          d.z += (vn.z - v.z) * weight;
        }
      }
    }

    // Apply displacements
    for (int i = 0; i < vertices.size(); i++) {
      Vertex v = getVertex(i);
      Vertex d = displacements.get(i);
      v.x += d.x * strength;
      v.y += d.y * strength;
      v.z += d.z * strength;
    }
  }

  public void taubinSmooth(int passes, double lambda, double mu) {
    NeightboursList neightbours = new NeightboursList(this);
    for (int i = 0; i < passes; i++) {
      laplaceSmooth(neightbours, lambda);
      laplaceSmooth(neightbours, mu);
    }
  }

  public List<Face> getFaces() {
    return faces;
  }

  public SimpleMesh interpolate() {
    SimpleMesh newMesh = new SimpleMesh();
    for (Face face : getFaces()) {
      Vertex v1 = getVertex(face.v1);
      Vertex v2 = getVertex(face.v2);
      Vertex v3 = getVertex(face.v3);
      int nv1, nv2, nv3, nv4, nv5, nv6;
      if (v1 instanceof VertexWithUV && v2 instanceof VertexWithUV && v3 instanceof VertexWithUV) {
        VertexWithUV v4 = intersect((VertexWithUV) v1, (VertexWithUV) v2);
        VertexWithUV v5 = intersect((VertexWithUV) v2, (VertexWithUV) v3);
        VertexWithUV v6 = intersect((VertexWithUV) v3, (VertexWithUV) v1);
        nv1 = newMesh.addVertex(v1.x, v1.y, v1.z, ((VertexWithUV) v1).u, ((VertexWithUV) v1).v);
        nv2 = newMesh.addVertex(v2.x, v2.y, v2.z, ((VertexWithUV) v2).u, ((VertexWithUV) v2).v);
        nv3 = newMesh.addVertex(v3.x, v3.y, v3.z, ((VertexWithUV) v3).u, ((VertexWithUV) v3).v);
        nv4 = newMesh.addVertex(v4.x, v4.y, v4.z, v4.u, v4.v);
        nv5 = newMesh.addVertex(v5.x, v5.y, v5.z, v5.u, v5.v);
        nv6 = newMesh.addVertex(v6.x, v6.y, v6.z, v6.u, v6.v);
      } else {
        Vertex v4 = intersect(v1, v2);
        Vertex v5 = intersect(v2, v3);
        Vertex v6 = intersect(v3, v1);

        nv1 = newMesh.addVertex(v1.x, v1.y, v1.z);
        nv2 = newMesh.addVertex(v2.x, v2.y, v2.z);
        nv3 = newMesh.addVertex(v3.x, v3.y, v3.z);
        nv4 = newMesh.addVertex(v4.x, v4.y, v4.z);
        nv5 = newMesh.addVertex(v5.x, v5.y, v5.z);
        nv6 = newMesh.addVertex(v6.x, v6.y, v6.z);
      }

      newMesh.addFace(nv1, nv4, nv6);
      newMesh.addFace(nv4, nv2, nv5);
      newMesh.addFace(nv5, nv3, nv6);

      newMesh.addFace(nv4, nv5, nv6);

    }
    return newMesh;
  }

  private Vertex intersect(Vertex v1, Vertex v2) {
    Vertex v = new Vertex();
    v.x = (float) (v1.x + (v2.x - v1.x) * 0.5);
    v.y = (float) (v1.y + (v2.y - v1.y) * 0.5);
    v.z = (float) (v1.z + (v2.z - v1.z) * 0.5);
    return v;
  }

  private VertexWithUV intersect(VertexWithUV v1, VertexWithUV v2) {
    VertexWithUV v = new VertexWithUV();
    v.x = (float) (v1.x + (v2.x - v1.x) * 0.5);
    v.y = (float) (v1.y + (v2.y - v1.y) * 0.5);
    v.z = (float) (v1.z + (v2.z - v1.z) * 0.5);
    v.u = (float) (v1.u + (v2.u - v1.u) * 0.5);
    v.v = (float) (v1.v + (v2.v - v1.v) * 0.5);
    return v;
  }

  public BoundingBox getBoundingBox() {
    if (boundingBox == null) {
      double xmin = Double.MAX_VALUE, xmax = Double.MIN_VALUE;
      double ymin = Double.MAX_VALUE, ymax = Double.MIN_VALUE;
      double zmin = Double.MAX_VALUE, zmax = Double.MIN_VALUE;
      for (int i = 0; i < vertices.size(); i++) {
        Vertex v = getVertex(i);
        if (v.x < xmin) {
          xmin = v.x;
        }
        if (v.x > xmax) {
          xmax = v.x;
        }
        if (v.y < ymin) {
          ymin = v.y;
        }
        if (v.y > ymax) {
          ymax = v.y;
        }
        if (v.z < zmin) {
          zmin = v.z;
        }
        if (v.z > zmax) {
          zmax = v.z;
        }
      }
      boundingBox = new BoundingBox(xmin, xmax, ymin, ymax, zmin, zmax);
    }
    return boundingBox;
  }
}
