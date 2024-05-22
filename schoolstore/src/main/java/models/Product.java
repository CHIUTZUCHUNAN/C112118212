package models;

public class Product {

    private String product_id;
    private String category;
    private String name;
    private int price;
    private String photo;
    private String description;
    private double discount; // New field for discount percentage

    public Product(String id, String category, String name, int price, String photo, String description, double discount) {
        this.product_id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.description = description;
        this.discount = discount;
    }

    public Product() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountedPrice() {
        return price * (1 - discount / 100);
    }

    @Override
    public String toString() {
        String discountInfo = discount > 0 ? ", discounted price=" + getDiscountedPrice() : "";
        return "Product [id=" + product_id + ", name=" + name + ", price=" + price + discountInfo + "]";
    }

}
