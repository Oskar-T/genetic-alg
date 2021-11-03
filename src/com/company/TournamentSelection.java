package com.company;

import java.util.Arrays;
import java.util.Random;

public class TournamentSelection extends Selection{
    public TournamentSelection (int amountOfTournaments, int amountOfParticipants) {
        this.amountOfTournaments = amountOfTournaments;
        this.amountOfParticipants = amountOfParticipants;
    }

    public char[][] select (char[][] population,
                            int[] fitnesses) {

        final int POOL_SIZE = amountOfTournaments;
        char[][] selected = new char[POOL_SIZE][];
        int selectedParticipantIndex = 0;
        int[] participants = new int[amountOfParticipants];

        for(int x = 0; x < amountOfTournaments; x++) {
            for (int i = 0; i < amountOfParticipants; i++) {
                Random rnd = new Random();
                selectedParticipantIndex = rnd.nextInt(amountOfTournaments);
                // because we know that fitnesses[] stores fitnesses
                // from highest to lowest we look for the index closest
                // to 0 and it gets selected
                participants[i] = selectedParticipantIndex;
            }

            int min = participants[0];
            for (int j = 0; j < participants.length; j++) {
                if (participants[j] < min) {
                    min = participants[j];
                }
            }
            selected[x] = population[min];
        }
        return selected;
    }

    private int amountOfTournaments;
    private int amountOfParticipants;
}

