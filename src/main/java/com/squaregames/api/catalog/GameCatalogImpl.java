package com.squaregames.api.catalog;

import java.util.Collection;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.squaregames.api.game.GamePlugin;

@Service
public class GameCatalogImpl implements GameCatalog {

    private final Collection<GamePlugin> plugins;

    public GameCatalogImpl(Collection<GamePlugin> plugins) {
        this.plugins = plugins;
    }

    @Override
    public Collection<GameCatalogItemResponse> getAvailableGames(Locale locale) {
        return plugins.stream()
                .map(plugin -> new GameCatalogItemResponse(
                        plugin.getGameType(),
                        plugin.getName(locale)
                ))
                .toList();
    }
}