package ru.ifmo.ctddev.shaykhutdinov.regularExpressionsParser;

import org.StructureGraphic.v1.DSTreeNode;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaykhutdinov Timur on 23.03.16.
 */
public class Tree implements DSTreeNode {

    private String node;
    private List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }

    @Override
    public DSTreeNode[] DSgetChildren() {
        return children != null ? children.toArray(new DSTreeNode[children.size()]) : new DSTreeNode[0];
    }

    @Override
    public Object DSgetValue() {
        return node;
    }

    @Override
    public Color DSgetColor() {
        if (node.equals("S") && children != null) {
            return Color.green;
        } else if (node.equals("C")) {
            return Color.blue;
        } else if (node.equals("C'")) {
            return Color.cyan;
        } else if (node.equals("S'") && children != null) {
            return Color.orange;
        } else return Color.red;
    }
}
