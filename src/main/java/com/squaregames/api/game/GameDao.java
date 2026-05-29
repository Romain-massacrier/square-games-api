package com.squaregames.api.game;

import java.util.Optional;
import java.util.stream.Stream;

import fr.le_campus_numerique.square_games.engine.Game;

public interface GameDao {

    Stream<Game> findAll();

    Optional<Game> findById(String gameId);

    Game upsert(Game game);

    void delete(String gameId);
}