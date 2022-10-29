package com.application.application.services;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class IdGenrator {
    public static long generate() {
        return Math.abs(ThreadLocalRandom.current().nextInt());
    }
}
