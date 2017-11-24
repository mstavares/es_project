package pt.ulusofona.es.g6.data;


public class StartUpUtils {

    private static final String[] categoriasPredefinidas = {"Transportes", "Alimentacao",
            "Propinas", "Renda", "Indefinida"};

    private static final String[][] agregadoFamiliar = {{"user1","user3"}};

    private StartUpUtils() {}

    public static String[] getCategoriasPredefinidas() {
        return categoriasPredefinidas;
    }

    public static String[][] getAgregadoFamiliar() { return agregadoFamiliar; }

    public static boolean eUmaCategoriaPredefinida(String categoria) {
        boolean resultado = false;
        for(String categoriaPredefinida : categoriasPredefinidas) {
            if(categoriaPredefinida.equals(categoria)) {
                resultado = true;
                break;
            }
        }
        return resultado;
    }
}
