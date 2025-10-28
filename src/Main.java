import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//COMENTARIO PARA HACER PUSH

public class Main {

    public static void main(String[] args) {
        ArrayList<Pokemon> pokemons = leerPokemonsDesdeCSV("resources/PokemonList.csv");

        if (pokemons.isEmpty()) {
            System.out.println("No se pudieron cargar los Pokémon.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String username;

        System.out.print("Bienvenido al mundo pokemon! Escribe tu nombre de usuario: ");
        username = sc.nextLine();
        System.out.println("Genial " + username + ". Pues comencemos!");
        System.out.println("");

        int opcionMenu = 0;

        System.out.println("========MENU DE OPCIONES========");
        System.out.println("1. Jugar batalla");
        System.out.println("2. Salir");
        System.out.println("================================");
        System.out.print("Escoja una opcion: ");
        opcionMenu = sc.nextInt();

        do {
            if (opcionMenu != 1 && opcionMenu != 2) {
                System.out.print("Opcion incorrecta. Introduzca una opcion valida:");
                opcionMenu = sc.nextInt();
            }
        } while (opcionMenu != 1 && opcionMenu != 2);

        System.out.println("=== LISTA DE POKÉMON DISPONIBLES ===");
        for (int i = 0; i < pokemons.size(); i++) {
            System.out.println((i + 1) + ". " + pokemons.get(i).nombre);
        }

        System.out.print("\nElige el número de tu Pokémon (1, 2, 3...): ");
        Pokemon jugador = pokemons.get(sc.nextInt() - 1);

        Pokemon rival;
        do {
            rival = pokemons.get(random.nextInt(pokemons.size()));
        } while (rival == jugador);

        System.out.println("\n🔥 COMIENZA LA BATALLA ENTRE " + jugador.nombre + " Y " + rival.nombre + " 🔥\n");

        boolean turnoJugador = true;


        while (jugador.vida > 0 && rival.vida > 0) {
            System.out.println("-------------------------------------");
            imprimirEstado(jugador);
            imprimirEstado(rival);
            System.out.println("-------------------------------------");

            if (turnoJugador) {
                System.out.println("\nTu turno (" + jugador.nombre + ")");
                int opcion = elegirAtaque(sc);

                if (opcion == 1) {
                    rival.vida = realizarAtaque(jugador.danhoGolpe, rival.vida, rival.defensa);
                    System.out.println(jugador.nombre + " le hace " + (jugador.danhoGolpe - rival.defensa) + " puntos de daño a " + rival.nombre + ".");
                } else {
                    if (jugador.mp >= jugador.mpGolpeEspecial) {
                        jugador.mp -= jugador.mpGolpeEspecial;
                        rival.vida = realizarAtaque(jugador.danhoEspecial, rival.vida, rival.defensa);
                        System.out.println(jugador.nombre + " usa " + jugador.golpeEspecial + " y le hace " + (jugador.danhoEspecial - rival.defensa) + " puntos de daño a " + rival.nombre + "!");
                    } else {
                        System.out.println(jugador.nombre + " no tiene suficientes MP y pierde el turno.");
                    }
                }

            } else {
                System.out.println("\nTurno del rival (" + rival.nombre + ")");
                int opcionRival = random.nextInt(2) + 1; // 1 o 2

                if (opcionRival == 1) {
                    jugador.vida = realizarAtaque(rival.danhoGolpe, jugador.vida, jugador.defensa);
                    System.out.println(rival.nombre + " usa ataque normal y le hace " + (rival.danhoGolpe - jugador.defensa) + " puntos de daño a " + jugador.nombre + ".");
                } else {
                    if (rival.mp >= rival.mpGolpeEspecial) {
                        rival.mp -= rival.mpGolpeEspecial;
                        jugador.vida = realizarAtaque(rival.danhoEspecial, jugador.vida, jugador.defensa);
                        System.out.println(rival.nombre + " usa " + rival.golpeEspecial + " y le hace " + (rival.danhoEspecial - jugador.defensa) + " puntos de daño a " + jugador.nombre + "!");
                    } else {
                        System.out.println(rival.nombre + " intenta usar " + rival.golpeEspecial + " pero no tiene suficientes MP. Pierde el turno.");
                    }
                }
            }

            if (jugador.vida <= 0) {
                System.out.println("\n💀 " + jugador.nombre + " se ha debilitado.");
                System.out.println("🏆 " + rival.nombre + " gana la batalla!");
                break;
            } else if (rival.vida <= 0) {
                System.out.println("\n💀 " + rival.nombre + " se ha debilitado.");
                System.out.println("🏆 " + jugador.nombre + " gana la batalla!");
                break;
            }

            turnoJugador = !turnoJugador;
        }

        sc.close();
    }

    public static void imprimirEstado(Pokemon p) {
        System.out.println(p.nombre + " ➜ Vida: " + p.vida + " | MP: " + p.mp);
    }

    public static int elegirAtaque(Scanner sc) {
        int opcion;
        do {
            System.out.println("\nElige tu ataque:");
            System.out.println("1. Ataque normal");
            System.out.println("2. Ataque especial");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

            if (opcion != 1 && opcion != 2) {
                System.out.println("❌ Opción no válida, intenta de nuevo.");
            }
        } while (opcion != 1 && opcion != 2);

        return opcion;
    }

    public static int realizarAtaque(int danho, int vidaEnemigo, int defensaEnemigo) {
        int danhoReal = danho - defensaEnemigo;
        if (danhoReal < 0) danhoReal = 0;
        int nuevaVida = vidaEnemigo - danhoReal;
        if (nuevaVida < 0) nuevaVida = 0;
        return nuevaVida;
    }

    public static ArrayList<Pokemon> leerPokemonsDesdeCSV(String archivoCSV) {
        ArrayList<Pokemon> pokemons = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] datos = linea.split(",");
                Pokemon p = new Pokemon(
                        datos[0],
                        Integer.parseInt(datos[1]),
                        Integer.parseInt(datos[2]),
                        Integer.parseInt(datos[3]),
                        Integer.parseInt(datos[4]),
                        datos[5],
                        Integer.parseInt(datos[6]),
                        Integer.parseInt(datos[7])
                );
                pokemons.add(p);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        return pokemons;
    }
}
