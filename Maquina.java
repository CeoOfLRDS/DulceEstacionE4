public abstract class Maquina {

    private String codigo;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponible;

    public Maquina(String codigo, String marca, String modelo, double tarifaDiaria) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public abstract double calcularCosto(int dias);

    public boolean alquilar(int dias) {
        if (disponible && dias > 0) {
            disponible = false;
            return true;
        }

        return false;
    }

    public boolean devolver() {
        if (!disponible) {
            disponible = true;
            return true;
        }

        return false;
    }

    public double getTarifaDiaria(){
        return tarifaDiaria;
    }

    public String getDatosGenerales(){
        return "Código: " + codigo
            + "\nMarca: " + marca
            + "\nModelo: " + modelo
            + "\nTarifa diaria: Q" + tarifaDiaria
            + "\nDisponible: " + disponible;
    }
    public abstract String getDescripcion();

}