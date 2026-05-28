package com.squaregames.api.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;

@Service
public class GameServiceImpl implements GameService {

    private final Map<String, GamePlugin> gamePlugins;

    private final Map<String, Game> games = new HashMap<>();

    public GameServiceImpl(Collection<GamePlugin> plugins) {
        this.gamePlugins = plugins.stream()
                .collect(Collectors.toMap(
                        GamePlugin::getGameType,
                        plugin -> plugin
                ));
    }

    @Override
    public GameResponse createGame(GameCreationParams params) {
        GamePlugin plugin = gamePlugins.get(params.getGameType());

        if (plugin == null) {
            throw new IllegalArgumentException("Type de jeu inconnu : " + params.getGameType());
        }

        Game game = plugin.createGame(params);

        String gameId = game.getId().toString();

        games.put(gameId, game);

        return new GameResponse(
                gameId,
                game.getFactoryId(),
                game.getPlayerIds().size(),
                game.getBoardSize(),
                game.getStatus().toString(),
                game.getCurrentPlayerId() == null ? null : game.getCurrentPlayerId().toString(),
                game.getBoard().toString()
        );
    }

    @Override
    public GameResponse getGame(String gameId) {
        Game game = games.get(gameId);

        if (game == null) {
            throw new IllegalArgumentException("Aucune partie trouvée avec l'ID : " + gameId);
        }

        return new GameResponse(
                game.getId().toString(),
                game.getFactoryId(),
                game.getPlayerIds().size(),
                game.getBoardSize(),
                game.getStatus().toString(),
                game.getCurrentPlayerId() == null ? null : game.getCurrentPlayerId().toString(),
                game.getBoard().toString()
        );
    }

    @Override
    public String getPossibleMoves(String gameId, String tokenId) {
        Game game = games.get(gameId);

        if (game == null) {
            return "Aucune partie trouvée avec l'ID : " + gameId;
        }

        if (!game.getFactoryId().equals("tictactoe")) {
            return "Les coups possibles ne sont disponibles que pour tictactoe pour le moment.";
        }

        Token currentToken = game.getRemainingTokens()
                .stream()
                .filter(Token::canMove)
                .findFirst()
                .orElse(null);

        if (currentToken == null) {
            return "Aucun jeton ne peut être joué.";
        }

        return "Coups possibles : " + currentToken.getAllowedMoves();
    }

    @Override
    public String playMove(String gameId, MoveParams params) {
        Game game = games.get(gameId);

        if (game == null) {
            return "Aucune partie trouvée avec l'ID : " + gameId;
        }

        if (!game.getFactoryId().equals("tictactoe")) {
            return "Seul tictactoe est jouable pour le moment.";
        }

        Token currentToken = game.getRemainingTokens()
                .stream()
                .filter(Token::canMove)
                .findFirst()
                .orElse(null);

        if (currentToken == null) {
            return "Aucun jeton ne peut être joué.";
        }

        CellPosition targetPosition = new CellPosition(
                params.getTargetx(),
                params.getTargety()
        );

        try {
            currentToken.moveTo(targetPosition);
        } catch (InvalidPositionException e) {
            return "Coup invalide : " + e.getMessage();
        }

        return "Coup joué en x="
                + params.getTargetx()
                + ", y="
                + params.getTargety()
                + ". Statut de la partie : "
                + game.getStatus()
                + ". Joueur courant : "
                + game.getCurrentPlayerId();
    }
}