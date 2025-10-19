package com.company.paywho.model;

import java.security.MessageDigest;
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

    public static String sha256(final String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void visibilidadComponentes(LinkedList<Node> componentes, boolean visibilidad) {
        for (Node componente : componentes) {
            if (visibilidad) {
                componente.setVisible(true);
                componente.setManaged(true);
            } else {
                componente.setVisible(false);
                componente.setManaged(false);
            }
        }
    }

    public static void visibilidadComponente(Node componente, boolean visibilidad) {
        if (visibilidad) {
            componente.setVisible(true);
            componente.setManaged(true);
        } else {
            componente.setVisible(false);
            componente.setManaged(false);
        }

    }
}
