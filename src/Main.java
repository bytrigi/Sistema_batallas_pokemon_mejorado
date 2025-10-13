import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Pokemon> pokemons = leerPokemonsDesdeCSV("resources/PokemonList.csv");

        if (pokemons.isEmpty()) {
            System.out.println("No se pudieron cargar los Pokémon.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("=== LISTA DE POKÉMON DISPONIBLES ===");
        for (int i = 0; i < pokemons.size(); i++) {
            System.out.println((i + 1) + ". " + pokemons.get(i).nombre);
        }

        System.out.print("\nElige el número de tu Pokémon: ");
        Pokemon jugador = pokemons.get(sc.nextInt() - 1);

        Pokemon rival;
        do {
            rival = pokemons.get(random.nextInt(pokemons.size()));
        } while (rival == jugador);

        System.out.println("\n🔥 COMIENZA LA BATALLA ENTRE " + jugador.nombre + " Y " + rival.nombre + " 🔥\n");

        boolean turnoJugador = true;

        while (jugador.vida > 0 && rival.vida > 0) {
            System.out.println("-------------------------------------");
            System.out.println(jugador.nombre + " ➜ Vida: " + jugador.vida + " | MP: " + jugador.mp);
            System.out.println(rival.nombre + " ➜ Vida: " + rival.vida + " | MP: " + rival.mp);
            System.out.println("-------------------------------------");

            if (turnoJugador) {
                // Turno del jugador
                System.out.println("\nTu turno (" + jugador.nombre + ")");
                System.out.print("¿Qué ataque quieres usar? (1 = Normal, 2 = Especial): ");
                int opcion = sc.nextInt();

                int danho = 0;

                if (opcion == 1) {
                    danho = jugador.danhoGolpe - rival.defensa;
                    if (danho < 0) danho = 0;
                    rival.vida -= danho;
                    System.out.println(jugador.nombre + " le hace " + danho + " puntos de daño a " + rival.nombre + ".");
                } else if (opcion == 2) {
                    if (jugador.mp >= jugador.mpGolpeEspecial) {
                        jugador.mp -= jugador.mpGolpeEspecial;
                        danho = jugador.danhoEspecial - rival.defensa;
                        if (danho < 0) danho = 0;
                        rival.vida -= danho;
                        System.out.println(jugador.nombre + " usa " + jugador.golpeEspecial + " y le hace " + danho + " puntos de daño a " + rival.nombre + "!");
                    } else {
                        System.out.println(jugador.nombre + " no tiene suficientes MP y pierde el turno.");
                    }
                } else {
                    System.out.println("Opción no válida. Pierdes el turno.");
                }
            } else {
                // Turno del rival (automático)
                System.out.println("\nTurno del rival (" + rival.nombre + ")");
                int opcionRival = random.nextInt(2) + 1; // 1 o 2

                int danho = 0;
                if (opcionRival == 1) {
                    danho = rival.danhoGolpe - jugador.defensa;
                    if (danho < 0) danho = 0;
                    jugador.vida -= danho;
                    System.out.println(rival.nombre + " usa ataque normal y le hace " + danho + " puntos de daño a " + jugador.nombre + ".");
                } else {
                    if (rival.mp >= rival.mpGolpeEspecial) {
                        rival.mp -= rival.mpGolpeEspecial;
                        danho = rival.danhoEspecial - jugador.defensa;
                        if (danho < 0) danho = 0;
                        jugador.vida -= danho;
                        System.out.println(rival.nombre + " usa " + rival.golpeEspecial + " y le hace " + danho + " puntos de daño a " + jugador.nombre + "!");
                    } else {
                        System.out.println(rival.nombre + " intenta usar " + rival.golpeEspecial + " pero no tiene suficientes MP. Pierde el turno.");
                    }
                }
            }

            // Verificar si alguien perdió
            if (jugador.vida <= 0) {
                System.out.println("\n💀 " + jugador.nombre + " se ha debilitado.");
                System.out.println("🏆 " + rival.nombre + " gana la batalla!");
                break;
            } else if (rival.vida <= 0) {
                System.out.println("\n💀 " + rival.nombre + " se ha debilitado.");
                System.out.println("🏆 " + jugador.nombre + " gana la batalla!");
                break;
            }

            turnoJugador = !turnoJugador; // cambiar turno
        }

        sc.close();
    }

    // Leer Pokémon desde CSV
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
