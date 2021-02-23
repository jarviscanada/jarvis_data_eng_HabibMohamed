package ca.jrvs.apps.jdbc.DTOs;

import ca.jrvs.apps.jdbc.util.DataTransferObject;

public class OrderItem implements DataTransferObject {

  private long id;
  private String code;
  private String name;
  private int size;
  private String variety;
  private int quantity;
  private float price;

  @Override
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getVariety() {
    return variety;
  }

  public void setVariety(String variety) {
    this.variety = variety;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", size=" + size +
        ", variety='" + variety + '\'' +
        ", quantity=" + quantity +
        ", price=" + price +
        '}';
  }
}
