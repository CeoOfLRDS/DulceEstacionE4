import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DulceEstacion {
    private List<Maquina> maquinas;
    private double ingresos;

    public DulceEstacion() {
        this.maquinas = new ArrayList<>();
        this.ingresos = 0.0;
    }

    public boolean registrarMaquina(Maquina maquina) {
        if (buscar(maquina.getCodigo()) == null) {
            maquinas.add(maquina);
            return true;
        }
        return false;
    }

    private Maquina buscar(String codigo) {
        for (Maquina m : maquinas) {
            if (m.getCodigo().equals(codigo)) {
                return m;
            }
        }
        return null;
    }

    public String cotizar(String codigo, int dias) {
        Maquina m = buscar(codigo);
        if (m == null) {
            return "Error: La máquina no existe.";
        }
        String disponibilidad = m.isDisponible() ? "Disponible" : "Alquilada";
        return String.format("Cotización -> %s | Estado: %s | Días: %d | Total: Q%.2f", m.getDescripcion(), disponibilidad, dias, m.calcularCosto(dias));
    }

    public boolean alquilar(String codigo, int dias) {
        Maquina m = buscar(codigo);
        if (m != null && m.alquilar(dias)) {
            ingresos += m.calcularCosto(dias);
            return true;
        }
        return false;
    }

    public boolean devolver(String codigo) {
        Maquina m = buscar(codigo);
        return m != null && m.devolver();
    }

    public String reporte() {
        int pDisp = 0, pAlq = 0, aDisp = 0, aAlq = 0, fDisp = 0, fAlq = 0;

        for (Maquina m : maquinas) {
            boolean disp = m.isDisponible();
            if (m instanceof MaquinaPalomitas) { if (disp) pDisp++; else pAlq++; }
            else if (m instanceof MaquinaAlgodon) { if (disp) aDisp++; else aAlq++; }
            else if (m instanceof FuenteChocolate) { if (disp) fDisp++; else fAlq++; }
        }

        return String.format("Total: %d | Palomitas(Disp:%d,Alq:%d) | Algodón(Disp:%d,Alq:%d) | Fuentes(Disp:%d,Alq:%d) | Ingresos: Q%.2f", maquinas.size(), pDisp, pAlq, aDisp, aAlq, fDisp, fAlq, ingresos);
    }

    public static void main(String[] args) {
        DulceEstacion sistema = new DulceEstacion();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("1. Registrar | 2. Cotizar | 3. Alquilar | 4. Devolver | 5. Reporte | 6. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Código de prueba: ");
                    if (sistema.registrarMaquina(new FuenteChocolate(scanner.nextLine(), "Oster", "X1", 100.0, 5.0))) {
                        System.out.println("Registrada correctamente.");
                    } else {
                        System.out.println("Error: Código duplicado.");
                    }
                    break;
                case 2:
                    System.out.print("Código: ");
                    String codC = scanner.nextLine();
                    System.out.print("Días: ");
                    System.out.println(sistema.cotizar(codC, scanner.nextInt()));
                    break;
                case 3:
                    System.out.print("Código: ");
                    String codA = scanner.nextLine();
                    System.out.print("Días: ");
                    int diasA = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println(sistema.cotizar(codA, diasA));
                    System.out.print("¿Confirmar alquiler? (S/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("S")) {
                        System.out.println(sistema.alquilar(codA, diasA) ? "Alquiler exitoso." : "Error en alquiler.");
                    }
                    break;
                case 4:
                    System.out.print("Código a devolver: ");
                    System.out.println(sistema.devolver(scanner.nextLine()) ? "Devolución exitosa." : "Error en devolución.");
                    break;
                case 5:
                    System.out.println(sistema.reporte());
                    break;
            }
        } while (opcion != 6);

        scanner.close();
    }
}
