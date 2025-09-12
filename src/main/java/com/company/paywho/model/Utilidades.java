package com.company.paywho.model;

import java.util.LinkedList;
import javafx.scene.Node;
import javafx.scene.Parent;

public class Utilidades {

    public static <T extends Node> LinkedList<T> recojerNodos(Parent root, Parent evitar, Class<T> tipo) {
        LinkedList<T> lista = new LinkedList<>();
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node == evitar) {
                continue;
            }
            if (tipo.isInstance(node)) {
                lista.add(tipo.cast(node));
            }
            if (node instanceof Parent) {
                lista.addAll(recojerNodos((Parent) node, evitar, tipo));
            }
        }
        return lista;
    }
    
}
