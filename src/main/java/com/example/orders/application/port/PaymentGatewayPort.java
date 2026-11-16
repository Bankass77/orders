package com.example.orders.application.port;

import java.util.UUID;

public interface PaymentGatewayPort {

    /**
     *  Débit le client pour la commande et renvoie une référence de paiement
     * @param orderId  id de la commande
     * @return une chaîne de caractère
     */
    String change(UUID orderId);
}
