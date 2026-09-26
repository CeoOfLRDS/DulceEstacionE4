public class MaquinaPalomitas extends Maquina {

    private int porcionesPorHora;
    private boolean carritoIntegrado;

    public MaquinaPalomitas(String codigo, String marca, String modelo, double tarifaDiaria, int porciones, boolean carrito) {

        super(codigo, marca, modelo, tarifaDiaria);
        this.porcionesPorHora = porciones;
        this.carritoIntegrado = carrito;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = getTarifaDiaria() * dias;

        if (carritoIntegrado) {
            costo = costo + (40 * dias);
        }

        return costo;
    }

    @Override
    public String getDescripcion() {
        return getDatosGenerales()
                + "\nTipo: Máquina de palomitas"
                + "\nPorciones por hora: " + porcionesPorHora
                + "\nCarrito integrado: " + carritoIntegrado;
    }
}