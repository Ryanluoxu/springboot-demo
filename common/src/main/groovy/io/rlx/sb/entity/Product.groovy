package io.rlx.sb.entity

class Product implements Serializable {

    String id
    String name

    @Override
    public String toString() {
        return "Product{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
