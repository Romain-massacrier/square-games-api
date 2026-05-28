package com.squaregames.api.catalog;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Indique que cette classe gère des routes HTTP
@RestController
public class GameCatalogController {

    // Catalogue qui contient les jeux disponibles
    private final GameCatalog gameCatalog;

    // Le catalogue est donné au contrôleur automatiquement
    public GameCatalogController(GameCatalog gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    // Quand on va sur /games, on récupère les jeux disponibles
    @GetMapping("/games")
    public Collection<String> getAvailableGames() {
        return gameCatalog.getAvailableGames();
    }
}

