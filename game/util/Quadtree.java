package com.byuh.cistutors.game.util;

import android.graphics.RectF;

import com.byuh.cistutors.game.Enemy.Enemies;

import java.util.ArrayList;
import java.util.List;

public class Quadtree {
    private int MAX_OBJECTS=4;
    private int MAX_LEVELS=5;

    private int level;
    private List objects;
    private RectF bounds;
    private Quadtree[] nodes;

    public Quadtree(int pLevel, RectF pBounds){
        level = pLevel;
        objects = new ArrayList<Enemies>();
        bounds = pBounds;
        nodes = new Quadtree[4];
    }

    public void clear(){
        objects.clear();

        for (int i=0; i<nodes.length;i++){
            if (nodes[i] != null){
                nodes[i].clear();
                nodes[i]=null;
            }
        }
    }

    private void split(){

        int subWidth = (int)((bounds.right - bounds.left)/2);
        int subHeight = (int)((bounds.bottom - bounds.top)/2);
        int x = (int)(bounds.left);
        int y = (int)(bounds.top);

        nodes[0] = new Quadtree(level+1,new RectF(x+subWidth, y, subWidth, subHeight));
        nodes[1] = new Quadtree(level+1,new RectF(x, y, subWidth, subHeight));
        nodes[2] = new Quadtree(level+1,new RectF(x, y+subHeight, subWidth, subHeight));
        nodes[3] = new Quadtree(level+1,new RectF(x+subWidth, y+subHeight, subWidth, subHeight));


    }

    private int getIndex(Enemies enemy) {
        RectF pRect = enemy.getBounds();
        int index = -1;
        double verticalMidpoint = bounds.left + ((bounds.right-bounds.left)/2);
        double horizontalMidPoint = bounds.top + ((bounds.bottom-bounds.top)/2);

        boolean topQuadrant = (pRect.top< horizontalMidPoint && pRect.top+(pRect.bottom-pRect.top)< horizontalMidPoint);
        boolean bottomQuadrant = (pRect.top > horizontalMidPoint);

        if (pRect.left<verticalMidpoint && pRect.left + (pRect.right-pRect.left)<verticalMidpoint){
            if (topQuadrant){
                index = 1;
            }
            else if (bottomQuadrant){
                index = 2;
            }
        }
        else if (pRect.left > verticalMidpoint){
            if (topQuadrant){
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }
        return index;
    }
    private int getIndex(RectF pRect) {

        int index = -1;
        double verticalMidpoint = bounds.left + ((bounds.right-bounds.left)/2);
        double horizontalMidPoint = bounds.top + ((bounds.bottom-bounds.top)/2);

        boolean topQuadrant = (pRect.top+(pRect.bottom-pRect.top)< horizontalMidPoint);
        boolean bottomQuadrant = (pRect.top > horizontalMidPoint);

        if (pRect.left + (pRect.right-pRect.left)<verticalMidpoint){
            if (topQuadrant){
                index = 1;
            }
            else if (bottomQuadrant){
                index = 2;
            }
        }
        else if (pRect.left > verticalMidpoint){
            if (topQuadrant){
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }
        return index;
    }

    public void insert(Enemies enemy){
        if(nodes[0]!= null){
            int index = this.getIndex(enemy);

            if (index != -1) {
                this.nodes[index].insert(enemy);

                return;
            }
        }
        objects.add(enemy);

        if (objects.size()>MAX_OBJECTS && level < MAX_LEVELS) {
            if(nodes[0]==null){
                split();
            }

            int i = 0;
            while (i<objects.size()){
                int index = getIndex((Enemies) objects.get(i));
                if (index != -1){
                    nodes[index].insert((Enemies) objects.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }
    public List retrieve(List returnObjects, RectF pRect){
        int index = getIndex(pRect);
        if (index != -1 && nodes[0]!= null){
            nodes[index].retrieve (returnObjects, pRect);
        }
        returnObjects.addAll(objects);

        return  returnObjects;

    }

}

