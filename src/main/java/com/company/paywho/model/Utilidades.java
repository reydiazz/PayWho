package com.company.paywho.model;

import java.util.LinkedList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class Utilidades {
    
    public static LinkedList<Button> recojerBotonesLinkedList(Parent root, Parent evitar) {
        LinkedList<Button> btns = new LinkedList<>();
        for (Node node : root.getChildrenUnmodifiable()) {  
            if (node == evitar) {
                continue;
            }
            if (node instanceof Button) {
                btns.add((Button)node);
            }
            if (node instanceof Parent) {
                btns.addAll(recojerBotonesLinkedList((Parent) node, evitar));
            }
        }
        return btns;
    }
}
