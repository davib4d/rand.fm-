package com.example; // <-- CORRIGIDO

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final MusicService musicService = new MusicService();

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    handleCreateProfile();
                    break;
                case 2:
                    handleLogin();
                    break;
                case 3:
                    System.out.println("O rand.fm() agradece a preferência! até logo.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- Rand.fm() ---");
        System.out.println("1. Criar Perfil");
        System.out.println("2. Entrar");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void handleCreateProfile() {
        System.out.print("Digite um nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Digite uma senha: ");
        String password = scanner.nextLine();

        if (userService.createUser(username, password)) {
            System.out.println("Perfil criado com sucesso!");
        } else {
            System.out.println("Erro: Nome de usuário já existe.");
        }
    }

    private static void handleLogin() {
        System.out.print("Usuário: ");
        String username = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();

        userService.login(username, password).ifPresentOrElse(
            user -> {
                System.out.println("Login bem-sucedido! Bem-vindo(a), " + user.getUsername());
                loggedInMenu(user);
            },
            () -> System.out.println("Usuário ou senha inválidos.")
        );
    }

    private static void loggedInMenu(User currentUser) {
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Obter recomendação de música");
            System.out.println("2. Ver meu perfil");
            System.out.println("3. Logout");
            System.out.print("Escolha uma opção: ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    recommendMusic(currentUser);
                    break;
                case 2:
                    showProfile(currentUser);
                    break;
                case 3:
                    System.out.println("Fazendo logout...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void recommendMusic(User currentUser) {
        String song = musicService.getRandomSong();
        System.out.println("\n-------------------------------------------");
        System.out.println("Música recomendada para você: " + song);
        System.out.println("-------------------------------------------");

        System.out.print("Gostou da música e quer salvá-la no seu perfil? (s/n): ");
        String choice = scanner.nextLine();
        if ("s".equalsIgnoreCase(choice)) {
            currentUser.addLikedSong(song);
            userService.updateUser(currentUser);
            System.out.println("'" + song + "' foi salva no seu perfil!");
        }
    }

    private static void showProfile(User currentUser) {
        System.out.println("\n--- PERFIL DE " + currentUser.getUsername().toUpperCase() + " ---");
        System.out.println("Número de músicas salvas: " + currentUser.getLikedSongs().size());

        if (!currentUser.getLikedSongs().isEmpty()) {
            System.out.print("Deseja ver a lista de músicas gostadas? (s/n): ");
            String choice = scanner.nextLine();
            if ("s".equalsIgnoreCase(choice)) {
                System.out.println("\nSuas músicas salvas:");
                currentUser.getLikedSongs().forEach(song -> System.out.println("- " + song));
            }
        }
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}