public class FuenteChocolate extends Maquina {
    private double capacidadKg;

    public FuenteChocolate(String codigo, String marca, String modelo, double tarifaDiaria, double capacidadKg) {
        super(codigo, marca, modelo, tarifaDiaria);
        this.capacidadKg = capacidadKg;
    }

    @Override
    public double calcularCosto(int dias) {
        return (getTarifaDiaria() * dias) + (20.0 * capacidadKg * dias);
    }

    @Override
    public String getDescripcion() {
        return getDatosGenerales()
                + "Tipo: Fuente de Chocolate"
                + "Capacidad Máxima: " + capacidadKg + " kg";
    }
}
