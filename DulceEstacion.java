import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DulceEstacion {
    private List<Maquina> maquinas;
    private double ingresos;

    public DulceEstacion() {
        this.maquinas = new ArrayList<>();
        this.ingresos = 0.0;
        
        registrarMaquina(new MaquinaPalomitas("P01", "Nostalgia", "Pop1", 50.0, 100, true));
        registrarMaquina(new MaquinaPalomitas("P02", "Cuisinart", "Pop2", 40.0, 80, false));
        registrarMaquina(new MaquinaAlgodon("A01", "VIVO", "Cot1", 60.0, 1050));
        registrarMaquina(new MaquinaAlgodon("A02", "Candery", "Cot2", 55.0, 900));
        registrarMaquina(new FuenteChocolate("F01", "Sephra", "Choc1", 150.0, 2.5));
        registrarMaquina(new FuenteChocolate("F02", "Wilton", "Choc2", 120.0, 1.5));
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

    private boolean estaDisponible(Maquina maquina) {
        return maquina.getDatosGenerales().contains("Disponible: true");
    }

    public String cotizar(String codigo, int dias) {
        Maquina m = buscar(codigo);
        if (m == null) return "Error: La máquina no existe.";
        
        String disponibilidad = estaDisponible(m) ? "Disponible" : "Alquilada";
        
        return String.format("Cotización -> %s | Estado: %s | Días: %d | Total: Q%.2f",
                m.getDescripcion(), disponibilidad, dias, m.calcularCosto(dias));
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
            boolean disp = estaDisponible(m);
            if (m instanceof MaquinaPalomitas) { if (disp) pDisp++; else pAlq++; }
            else if (m instanceof MaquinaAlgodon) { if (disp) aDisp++; else aAlq++; }
            else if (m instanceof FuenteChocolate) { if (disp) fDisp++; else fAlq++; }
        }

        return String.format(
                "Total: %d | Palomitas(Disp:%d,Alq:%d) | Algodón(Disp:%d,Alq:%d) | Fuentes(Disp:%d,Alq:%d) | Ingresos: Q%.2f",
                maquinas.size(), pDisp, pAlq, aDisp, aAlq, fDisp, fAlq, ingresos
        );
    }

    public static void main(String[] args) {
        DulceEstacion sistema = new DulceEstacion();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            try {
                System.out.println("\n1. Registrar | 2. Cotizar | 3. Alquilar | 4. Devolver | 5. Reporte | 6. Salir");
                System.out.print("Opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Categoría (1.Palomitas 2.Algodón 3.Fuente): ");
                        int cat = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Código: ");
                        String cod = scanner.nextLine().trim();
                        if (cod.isEmpty()) { System.out.println("Error: El código no puede estar vacío."); break; }
                        if (sistema.buscar(cod) != null) { System.out.println("Error: El código ya existe."); break; }

                        System.out.print("Marca: "); String mar = scanner.nextLine();
                        System.out.print("Modelo: "); String mod = scanner.nextLine();
                        
                        System.out.print("Tarifa Diaria: "); double tar = scanner.nextDouble();
                        if (tar <= 0) { System.out.println("Error: La tarifa debe ser mayor a cero."); break; }

                        if (cat == 1) {
                            System.out.print("Porciones por hora: "); int porc = scanner.nextInt();
                            if (porc <= 0) { System.out.println("Error: Porciones deben ser mayor a cero."); break; }
                            System.out.print("Carrito integrado (true/false): "); boolean carr = scanner.nextBoolean();
                            sistema.registrarMaquina(new MaquinaPalomitas(cod, mar, mod, tar, porc, carr));
                            System.out.println("Máquina de palomitas registrada.");
                            
                        } else if (cat == 2) {
                            System.out.print("Potencia (vatios): "); int pot = scanner.nextInt();
                            if (pot <= 0) { System.out.println("Error: La potencia debe ser mayor a cero."); break; }
                            sistema.registrarMaquina(new MaquinaAlgodon(cod, mar, mod, tar, pot));
                            System.out.println("Máquina de algodón registrada.");
                            
                        } else if (cat == 3) {
                            System.out.print("Capacidad (kg): "); double cap = scanner.nextDouble();
                            if (cap <= 0) { System.out.println("Error: La capacidad debe ser mayor a cero."); break; }
                            sistema.registrarMaquina(new FuenteChocolate(cod, mar, mod, tar, cap));
                            System.out.println("Fuente de chocolate registrada.");
                            
                        } else {
                            System.out.println("Error: Categoría inválida.");
                        }
                        break;

                    case 2:
                        System.out.print("Código: ");
                        String codC = scanner.nextLine();
                        System.out.print("Días: ");
                        int diasC = scanner.nextInt();
                        if (diasC <= 0) { System.out.println("Error: Los días deben ser mayores a cero."); break; }
                        
                        System.out.println(sistema.cotizar(codC, diasC));
                        break;

                    case 3:
                        System.out.print("Código: ");
                        String codA = scanner.nextLine();
                        System.out.print("Días: ");
                        int diasA = scanner.nextInt();
                        scanner.nextLine();

                        if (diasA <= 0) { System.out.println("Error: Los días deben ser mayores a cero."); break; }

                        System.out.println(sistema.cotizar(codA, diasA));
                        System.out.print("¿Confirmar alquiler? (S/N): ");
                        
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            System.out.println(sistema.alquilar(codA, diasA) ? "Alquiler exitoso." : "Error: Máquina no disponible o código inválido.");
                        }
                        break;

                    case 4:
                        System.out.print("Código a devolver: ");
                        System.out.println(sistema.devolver(scanner.nextLine()) ? "Devolución exitosa." : "Error: Máquina no alquilada o código inválido.");
                        break;

                    case 5:
                        System.out.println(sistema.reporte());
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Formato de entrada incorrecto. Ingrese un valor válido.");
                scanner.nextLine();
            }
        } while (opcion != 6);

        scanner.close();
    }
}
