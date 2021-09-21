package com.loct.appetite.models;

import java.util.Vector;

public class DishTree {

    /**
     * Node class define a node of a tee
     * @attrib dish -> holds an instance of a Dish
     * @attrib children -> holds children of this node
     */
    private class Node{
        private Dish dish = null;
        protected Vector<Node> children = new Vector<>();
        private Node parent = null;

        /**
         * retruns children of a node
         * @return Vector<Node>
         */
        public Vector<Node> children() {
            return children;
        }

        /**
         * adds a child node to the node
         * @param node -> node to be inserted as child
         */
        public void addChild(Node node) {
            node.parent(Node.this);
            children.add(node);
        }

        public Node parent(){
            return parent;
        }

        public Node parent(Node node){
            return parent;
        }
    }

    private Node root;
    private Vector<Node> leafNodes = new Vector<>();
    protected Vector<Vector<Dish>> mealsDishes = new Vector<>();



    public DishTree(){
        root = new Node();
    }

    public Vector<Node> leafNodes(){
        getLeafNodes(root);
        return leafNodes;
    }

    private void getLeafNodes (Node root){
        if(root.children().size() == 0){
            leafNodes.add(root);
        }
        else{
            for (Node node: root.children()) {
                getLeafNodes(node);
            }
        }

    }

    /**
     * This method returns meal by enumerating all root-to-leaf paths of the DishTree
     * @return Vector<Meal>
     */
    public Vector<Meal> meals(){
        getMealsDishes(root);
        return null;
    }

    private void getMealsDishes(Node root){
        for (Node node : this.leafNodes()) {
            Vector<Dish> dishes = new Vector<>();
            getDishes(node, dishes);
        }
    }

    private void getDishes(Node node, Vector<Dish> dishes){
        dishes.add(node.dish);
        if(node.parent == null){
            mealsDishes.add(dishes);
            return;
        }

        getDishes(node.parent, dishes);
    }
}
