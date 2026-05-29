package com.squaregames.api.game;

import java.util.Collection;

public interface GameService {

    GameResponse createGame(GameCreationParams params);

    GameResponse getGame(String gameId);

    Collection<GameResponse> getAllGames();

    String getPossibleMoves(String gameId, String tokenId);

    String playMove(String gameId, MoveParams params);

    void deleteGame(String gameId);
}