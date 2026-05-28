package com.squaregames.api.catalog;

import java.util.Collection;
import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameCatalogController {

    private final GameCatalog gameCatalog;

    public GameCatalogController(GameCatalog gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    @GetMapping("/games")
    public Collection<GameCatalogItemResponse> getAvailableGames(Locale locale) {
        return gameCatalog.getAvailableGames(locale);
    }
}