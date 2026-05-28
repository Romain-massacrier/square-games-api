package com.squaregames.api.catalog;

import java.util.Collection;

public interface GameCatalog {

    // Retourne la liste des jeux disponibles
    Collection<String> getAvailableGames();
}