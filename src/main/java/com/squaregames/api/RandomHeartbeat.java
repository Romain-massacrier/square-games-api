package com.squaregames.api;

import org.springframework.stereotype.Service;
import java.util.Random;


@Service
public class RandomHeartbeat implements HeartbeatSensor {

    private final Random random = new Random();

    @Override
    public int get() {
        return 60 + random.nextInt(41);
    }
}