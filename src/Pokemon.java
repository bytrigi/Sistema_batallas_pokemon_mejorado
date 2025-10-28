public class Pokemon {
    String nombre;
    int vida;
    int mp;
    int danhoGolpe;
    int defensa;
    String golpeEspecial;
    int danhoEspecial;
    int mpGolpeEspecial;

    public Pokemon(String nombre, int vida, int mp, int danhoGolpe, int defensa, String golpeEspecial, int danhoEspecial, int mpGolpeEspecial) {
        this.nombre = nombre;
        this.vida = vida;
        this.mp = mp;
        this.danhoGolpe = danhoGolpe;
        this.defensa = defensa;
        this.golpeEspecial = golpeEspecial;
        this.danhoEspecial = danhoEspecial;
        this.mpGolpeEspecial = mpGolpeEspecial;
    }

    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Vida: " + vida);
        System.out.println("MP: " + mp);
        System.out.println("Daño Golpe: " + danhoGolpe);
        System.out.println("Defensa: " + defensa);
        System.out.println("Golpe Especial: " + golpeEspecial);
        System.out.println("Daño Especial: " + danhoEspecial);
        System.out.println("MP Golpe Especial: " + mpGolpeEspecial);
        System.out.println("------------------------");
    }
}
