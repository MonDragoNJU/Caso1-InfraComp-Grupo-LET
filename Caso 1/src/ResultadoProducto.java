public class ResultadoProducto {
    private boolean exito;
    private Producto producto;

    public ResultadoProducto(boolean exito, Producto producto) {
        this.exito = exito;
        this.producto = producto;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
