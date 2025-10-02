package com.example;

// --- VERIFIQUE SE ESSES IMPORTS ESTÃO AQUI ---
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// -------------------------------------------

public class MusicService {
    private static final String MUSIC_FILE = "data.csv";
    private final List<String> songNames = new ArrayList<>();
    private final Random random = new Random();

    public MusicService() {
        loadSongsFromFile();
    }

    private void loadSongsFromFile() {
        // O try-with-resources garante que o reader será fechado
        try (CSVReader reader = new CSVReader(new FileReader(MUSIC_FILE))) {
            String[] headers = reader.readNext();
            if (headers == null) {
                System.err.println("Arquivo CSV está vazio ou corrompido.");
                return;
            }

            int nameColumnIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if ("name".equalsIgnoreCase(headers[i])) {
                    nameColumnIndex = i;
                    break;
                }
            }

            if (nameColumnIndex == -1) {
                System.err.println("Coluna 'name' não encontrada no arquivo " + MUSIC_FILE);
                return;
            }

            String[] line;
            while ((line = reader.readNext()) != null) {
                songNames.add(line[nameColumnIndex]);
            }

        } catch (IOException | CsvValidationException e) {
            System.err.println("Erro ao carregar as músicas do CSV: " + e.getMessage());
        }
    }

    public String getRandomSong() {
        if (songNames.isEmpty()) {
            return "Nenhuma música encontrada no dataset.";
        }
        int randomIndex = random.nextInt(songNames.isEmpty() ? 1 : songNames.size());
        return songNames.get(randomIndex);
    }
}