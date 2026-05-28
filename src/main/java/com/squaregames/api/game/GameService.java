package com.squaregames.api.game;

public interface GameService {

    GameResponse createGame(GameCreationParams params);

    GameResponse getGame(String gameId);

    String getPossibleMoves(String gameId, String tokenId);

    String playMove(String gameId, MoveParams params);
}