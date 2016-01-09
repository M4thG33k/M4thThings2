package com.m4thg33k.m4ththings.utility;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Stack;

public class CubicSplineCreation {

    private Stack<LocVec> path; //this path consists only of the block locations we travel (not midpoints)
    private ForgeDirection side;
    //private int attachedSide;
    private LocVec initLoc;
    private Vec3[] Points;
    private Vec3[] Slopes;
    private int n; //the number of points in our data set (the range of t is (0,n-1))

    public CubicSplineCreation(Stack<LocVec> p, int aSide,LocVec location)
    {
        path = p;
        side = ForgeDirection.VALID_DIRECTIONS[aSide];
        //attachedSide = aSide;
        initLoc = location;
        n = 2*path.size()-1;
        Points = new Vec3[n];
        Slopes = new Vec3[n];

        createPoints();
        createSlopes();
    }

    //takes the internal stack of relative locations and creates an array of vectors that correspond to the midpoints
    //and sides of each block the fluid will be traversing
    private void createPoints()
    {
        LocVec temp;
        ForgeDirection between;
        Points[0] = Vec3.createVectorHelper(initLoc.getX()+0.5*(1+side.offsetX),initLoc.getY()+0.5*(1+side.offsetY),initLoc.getZ()+0.5*(1+side.offsetZ));
        Points[1] = Vec3.createVectorHelper(initLoc.getX()+0.5,initLoc.getY()+0.5,initLoc.getZ()+0.5);
        for (int i=1;i<path.size()-1;i++)
        {
            temp = path.elementAt(i);
            Points[2*i+1] = Vec3.createVectorHelper(initLoc.getX()+0.5+temp.getX(),initLoc.getY()+0.5+temp.getY(),initLoc.getZ()+0.5+temp.getZ()); //gets the midpoints of each block (sans the last one)
            between = (temp.difference(path.elementAt(i-1))).direction();
            Points[2*i] = Points[2*i+1].addVector(-0.5*between.offsetX,-0.5*between.offsetY,-0.5*between.offsetZ);
        }
        between = (path.elementAt(path.size()-2).difference(path.lastElement())).direction();
        Points[2*path.size()-2] = Points[2*path.size()-3].addVector(-0.5*between.offsetX,-0.5*between.offsetY,-0.5*between.offsetZ);
    }

    //takes the internal data and calculates the slopes throughout the path
    private void createSlopes()
    {
        Slopes[0] = Vec3.createVectorHelper(side.getOpposite().offsetX,side.getOpposite().offsetY,side.getOpposite().offsetZ);
        ForgeDirection between = ((path.lastElement()).difference(path.elementAt(path.size()-2))).direction();
        Slopes[2*path.size()-2] = Vec3.createVectorHelper(between.offsetX,between.offsetY,between.offsetZ);

        for (int i=1;i<2*path.size()-2;i++)
        {
            Slopes[i] = BasicTools.scaleVector(Points[i+1].subtract(Points[i-1]),0.5);
        }
    }

    //this takes in a value for t between 0 and 1 and produces four H values used in the cubic hermite interpolation
    private double[] HFunctions(double t)
    {
        double[] H = new double[4];

        H[0] = (1+2*t)*(1-t)*(1-t);
        H[1] = t*(1-t)*(1-t);
        H[2] = t*t*(3-2*t);
        H[3] = t*t*(t-1);

        return H;
    }

    //this is one of the pieces of the piecewise polynomial where m = floor(t) (t is the original t value)
    private Vec3 qSubM(double t,int m)
    {
        double[] H = HFunctions(t);

        Vec3[] vecs = new Vec3[4];
        vecs[0] = BasicTools.scaleVector(Points[m],H[0]);
        vecs[1] = BasicTools.scaleVector(Slopes[m],H[1]);
        vecs[2] = BasicTools.scaleVector(Points[m+1],H[2]);
        vecs[3] = BasicTools.scaleVector(Slopes[m+1],H[3]);

        return BasicTools.addVectors(vecs);
    }

    //returns the location of the spline at time t, where t is between 0 and n-1 (inclusive)
    public Vec3 locationAtTime(double t)
    {
        if (t<0 || t>n-1)
        {
            return Vec3.createVectorHelper(0,0,0); //if we are outside the range, return the zero vector
        }

        int m = (int)t; //the first integer leq t

        if (m==n-1) //if we are at the very last location, we must use the piecewise portion just before it
        {
            return qSubM(1,m-1);
        }


        return qSubM(t-m,m);
    }

    public Vec3[] locations(double start, double end, double step)
    {
        double temp;
        temp = start;
        int numT = 0;
        while (temp<=end)
        {
            numT += 1;
            temp += step;
        }

        temp = start;

        Vec3[] locs = new Vec3[numT];
        for (int i=0;i<numT;i++)
        {
            locs[i] = locationAtTime(temp);
            temp += step;
        }

        return locs;
    }

    public Vec3[] allLocations(double step)
    {
        return locations(0,n-1,step);
    }

    public static Vec3[] generateNewDerivatives(Vec3[] data)
    {
        Vec3[] derivs = new Vec3[data.length-1];
        for (int i=0;i<data.length-1;i++)
        {
            derivs[i] = BasicTools.scaleVector(data[i].subtract(data[i+1]),0.25);
        }

        return derivs;
    }
}
