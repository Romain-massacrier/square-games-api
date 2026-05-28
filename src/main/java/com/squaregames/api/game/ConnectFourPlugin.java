package com.squaregames.api.game;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;

@Component
public class ConnectFourPlugin implements GamePlugin {

    private final ConnectFourGameFactory factory;
    private final MessageSource messageSource;

    @Value("${game.connect4.default-player-count}")
    private int defaultPlayerCount;

    @Value("${game.connect4.default-board-size}")
    private int defaultBoardSize;

    public ConnectFourPlugin(
            ConnectFourGameFactory factory,
            MessageSource messageSource
    ) {
        this.factory = factory;
        this.messageSource = messageSource;
    }

    @Override
    public String getGameType() {
        return factory.getGameFactoryId();
    }

    @Override
    public String getName(Locale locale) {
        return messageSource.getMessage(
                "game.connect4.name",
                null,
                locale
        );
    }

    @Override
    public Game createGame(GameCreationParams params) {
        int playerCount = params.getPlayerCount() == 0
                ? defaultPlayerCount
                : params.getPlayerCount();

        int boardSize = params.getBoardSize() == 0
                ? defaultBoardSize
                : params.getBoardSize();

        return factory.createGame(playerCount, boardSize);
    }
}