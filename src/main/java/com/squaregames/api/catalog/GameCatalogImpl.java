package com.squaregames.api.catalog;

import java.util.Collection;

import org.springframework.stereotype.Service;

import fr.le_campus_numerique.square_games.engine.GameFactory;

@Service
public class GameCatalogImpl implements GameCatalog {

    private final Collection<GameFactory> factories;

    public GameCatalogImpl(Collection<GameFactory> factories) {
        this.factories = factories;
    }

    @Override
    public Collection<String> getAvailableGames() {
        return factories.stream()
                .map(GameFactory::getGameFactoryId)
                .toList();
    }
}