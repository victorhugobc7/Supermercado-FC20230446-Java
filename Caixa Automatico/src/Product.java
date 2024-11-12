abstract class Product {
    int productCode = 0;
    int productValuePerUnit = 0;
    String productName;

    @Override
    public String toString() {
        return super.toString() + " " + productName + " " + "Código: " + productCode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getProductValuePerUnit() {
        return productValuePerUnit;
    }

    public void setProductValuePerUnit(int productValuePerUnit) {
        this.productValuePerUnit = productValuePerUnit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}