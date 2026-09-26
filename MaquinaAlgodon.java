public class MaquinaAlgodon extends Maquina {

    private int potencia;

    public MaquinaAlgodon(String codigo, String marca, String modelo, double tarifaDiaria, int potencia) {

        super(codigo, marca, modelo, tarifaDiaria);
        this.potencia = potencia;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = getTarifaDiaria() * dias;

        if (potencia > 1000) {
            costo = costo + 60;
        }

        return costo;
    }

    @Override
    public String getDescripcion() {
        return getDatosGenerales()
                + "\nTipo: Máquina de algodón de azúcar"
                + "\nPotencia: " + potencia + " vatios";
    }
}