package pojo;

public class Product {

    private String title;
    private double price;
    private String description;
    private String Image;
    private String Category;

    public Product(String title,double price,String description,String Image,String Category)
    {
        this.title=title;
        this.price=price;
        this.description=description;
        this.Image=Image;
        this.Category=Category;

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }



}
