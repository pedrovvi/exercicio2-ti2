package ti2cc;

public class Fruit {
    private int id;
    private String name;
    private float price;

    public Fruit() {
        this.id = -1;
        this.name = "";
        this.price = -1f;
    }

    public Fruit(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public float getPrice() { return this.price; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(float price) { this.price = price; }

    @Override
    public String toString() {
        return "ID [" + this.id + "] -> " + this.name + ": " + this.price;
    }
}
