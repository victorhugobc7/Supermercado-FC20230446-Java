public class Terminal {

    public int showMainMenu() {
        System.out.println("MENU PRINCIPAL\n\n");
        System.out.println("1 - Realizar Venda");
        System.out.println("2 - Cadastrar produtos");
        System.out.println("3 - Remover Produtos");
        System.out.println("4 - Gerar Relatórios");
        System.out.println("5 - Buscar produto");
        System.out.println("6 - Fechar o caixa hoje");
        return Console.readNumber("Escolha a sua opcao: ");
    }

    void showSaleMenu() {
        //Realizar sistema de compra aqui
    }

    void registerProduct() {
        System.out.println("CADASTRAR PRODUTOS\n\n");
    }

    public void finishSalesToday(int totalAcquired){
        System.out.println("O total de ganhos de hoje foi de " + totalAcquired +"R$");
    }


}