package com.squaregames.api.game;

import java.util.Locale;

import fr.le_campus_numerique.square_games.engine.Game;

public interface GamePlugin {

    String getGameType();

    String getName(Locale locale);

    Game createGame(GameCreationParams params);
}