import java.util.ArrayList;
import java.util.Objects;

public class CashierManager {
    Terminal terminal = new Terminal();
    ArrayList<Product> productList = new ArrayList<Product>();
    double initialCashBalance = 0;
    double cashBalance = 0;

    void startNewCashier(){
        initialCashBalance = terminal.showAmountProfitedBefore();
        cashBalance = initialCashBalance;
        loop();
    }

    void loop() {
        int opcao = 0;
        for (; ; ) {
            opcao = terminal.showMainMenu();
            if (opcao == 1) {
                handleProductPurchase();
            }
            if (opcao == 2) {
                handleRegisterProduct();
            }
            if (opcao == 3) {
                handleRemoveProduct();
            }
            if (opcao == 4) {
                handleGenerateReport();
            }
            if (opcao == 5) {
                handleSearchProductByName();
            } else if (opcao == 6) {
                finishSalesToday();
                break;
            }

            else if (opcao == 999) {
                handleRegisterProductList();
            }
        }
    }

    private void handleProductPurchase() {
        ArrayList<Product> productsAddedToCart = new ArrayList<Product>();
        ArrayList<Integer> productAmountInCart = new ArrayList<Integer>();
        double total = 0;
        for (int indexNumber = 0; ; ) {
            Product currentProduct = handleSearchProductByCode(Console.readText("Código do produto: "));
            if (currentProduct == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            int quantity = Console.readNumber("Quantidade de produtos: ");
            if (quantity > currentProduct.getStockQuantity()) {
                System.out.println("Quantidade em estoque insuficiente.");
                continue;
            }

            productsAddedToCart.add(currentProduct);
            productAmountInCart.add(quantity);
            total += currentProduct.getProductValuePerUnit() * quantity;

            currentProduct.reduceStock(quantity);  // Reduz a quantidade em estoque

            if (Objects.equals(Console.readText("Fechar caixa? (s/n): "), "s")) {
                terminal.showFinishedPurchaseMenu(total);
                cashBalance += total;
                displayGroupedProducts(productsAddedToCart, productAmountInCart);
                break;
            }
        }
    }

    private void displayGroupedProducts(ArrayList<Product> products, ArrayList<Integer> amounts) {
        // Lista para armazenar os agrupamentos dos produtos
        ArrayList<String> groupedProducts = new ArrayList<>();
        ArrayList<Integer> groupedQuantities = new ArrayList<>();
        double total = 0;

        // Iterar sobre os produtos e agrupá-los
        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = products.get(i);
            int currentAmount = amounts.get(i);
            String currentProductName = currentProduct.getProductName();
            double currentProductPrice = currentProduct.getProductValuePerUnit();

            boolean found = false;

            // Verificar se o produto já está na lista de agrupados
            for (int j = 0; j < groupedProducts.size(); j++) {
                // Se o nome do produto já existir na lista, somamos as quantidades
                if (groupedProducts.get(j).equals(currentProductName)) {
                    groupedQuantities.set(j, groupedQuantities.get(j) + currentAmount);  // Soma a quantidade
                    found = true;
                    break;
                }
            }

            // Se o produto não foi encontrado, adicionamos ele na lista de agrupados
            if (!found) {
                groupedProducts.add(currentProductName);
                groupedQuantities.add(currentAmount);
            }
        }

        // Exibir os produtos agrupados e calcular o total
        for (int i = 0; i < groupedProducts.size(); i++) {
            String productName = groupedProducts.get(i);
            int quantity = groupedQuantities.get(i);
            double productPrice = getProductPriceByName(products, productName);
            double productTotal = productPrice * quantity;

            // Exibe a quantidade, nome do produto e o total por produto
            System.out.println(quantity + "x " + productName + " - " + productTotal);
            total += productTotal;
        }

        System.out.println("Total: " + total);  // Exibe o total final
    }

    // Método auxiliar para buscar o preço do produto pelo nome
    private double getProductPriceByName(ArrayList<Product> products, String productName) {
        for (Product product : products) {
            if (product.getProductName().equals(productName)) {
                return product.getProductValuePerUnit();
            }
        }
        return 0;  // Retorna 0 caso o produto não seja encontrado
    }


    private void handleRegisterProductList() {
        // Script para registrar uma lista de produtos com quantidade
        Aliments aliment1 = new Aliments("Iogurte", "AL001", 8.99f, 100, 15);
        productList.add(aliment1);
        Aliments aliment2 = new Aliments("Cereal", "AL002", 5.49f, 50, 30);
        productList.add(aliment2);
        Aliments aliment3 = new Aliments("Leite", "AL003", 3.99f, 120, 7);
        productList.add(aliment3);
        Aliments aliment4 = new Aliments("Pão", "AL004", 2.49f, 200, 3);
        productList.add(aliment4);
        Aliments aliment5 = new Aliments("Queijo", "AL005", 10.99f, 70, 10);
        productList.add(aliment5);

        Electronics electronic1 = new Electronics("TV LED", "EL001", 1899.99f, 20, 365);
        productList.add(electronic1);
        Electronics electronic2 = new Electronics("Smartphone", "EL002", 1199.99f, 50, 365);
        productList.add(electronic2);
        Electronics electronic3 = new Electronics("Notebook", "EL003", 2999.99f, 30, 730);
        productList.add(electronic3);
        Electronics electronic4 = new Electronics("Câmera Digital", "EL004", 799.99f, 40, 365);
        productList.add(electronic4);
        Electronics electronic5 = new Electronics("Fone de Ouvido", "EL005", 199.99f, 100, 365);
        productList.add(electronic5);

        System.out.println("Lista de produtos registrada com sucesso!");
    }

    private Product handleRegisterProduct() {
        int escolha = terminal.registerProduct();
        switch (escolha) {
            case 1:
                productList.add(terminal.registerProductTypeAliment());
                break;
            case 2:
                productList.add(terminal.registerProductTypeElectronic());
                break;
            case 3:
                break;
            default:
                terminal.showInvalidAction();
        }
        return null;
    }

    private Product handleSearchProductByCode(String code){
        for(Product interatingProduct : productList){
            if(code.equals(interatingProduct.getProductCode())){
                return interatingProduct;
            }
        }
        return null;
    }

    private void handleRemoveProduct() {
        String productCode = terminal.showRemoveProductMenu();
        if (terminal.confirmActionMenu()) {
            for (Product product : productList) {
                if (product.getProductCode().equals(productCode)) {
                    productList.remove(product);
                    terminal.ShowProductRemovedSuccessfully();
                    break;
                }
            }
        }
    }

    private void handleSearchProductByName(){
        String productName = terminal.showSearchMenu();
            for(Product interatingProduct : productList){
                //Aqui o loop irá mostrar o produto encontrado e irá mostrar no console
                if(productName.equals(interatingProduct.getProductName())){
                    terminal.showProductInfo(interatingProduct);
                    return;
                }
            }
            terminal.showProductMissing();
    }

    public void handleGenerateReport() {
        int choice = terminal.generateReport();

        if(choice == 1) {
            System.out.println("Relatório de Produtos por validade:");
            generateValidityReport();
        }
        if(choice == 2) {
            System.out.println("\nrelatório de produtos por quantidade em estoque:");
            generateStockQuantityReport();
        }
        if(choice == 3){
            return;
        }
    }

    private void generateValidityReport() {
        int amountOfDays = terminal.showAmountOfDaysToSearch();

        for (Product product : productList) {
            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                if (electronics.getWarrantyDaysLeft() <= amountOfDays) {
                    System.out.println("Produto: " + electronics.getProductName() + " | Validade: " + electronics.getWarrantyDaysLeft() + " dias");
                }
            } else if (product instanceof Aliments) {
                Aliments aliments = (Aliments) product;
                if (aliments.getValidityDaysLeft() <= amountOfDays) {
                    System.out.println("Produto: " + aliments.getProductName() + " | Validade: " + aliments.getValidityDaysLeft() + " dias");
                }
            }
        }
    }

    private void generateStockQuantityReport() {
        int stockLimit = terminal.showAmountOfStockToSearch();

        for (Product product : productList) {
            if (product.getStockQuantity() <= stockLimit) {
                System.out.println("Produto: " + product.getProductName() + " | Quantidade em Estoque: " + product.getStockQuantity() + " unidades");
            }
        }
    }

    private void finishSalesToday(){
        terminal.showFinishSalesToday(initialCashBalance, cashBalance);
    }
}
