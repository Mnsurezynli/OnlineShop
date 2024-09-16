package application.Dto;
import application.model.Cart;
import application.model.Product;
public class CartItemDto {
    private Long id;
    private int number;
    private Long cartId;
    private Long productId;

    private ProductDto product;
    private int quantity;

    public CartItemDto(Long id, int number) {
        this.id = id;
        this.number = number;
    }

    public CartItemDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
