package com.example.berclazmayskiseller.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * https://developer.android.com/reference/android/arch/persistence/room/Entity.html
 * <p>
 * interesting: owner column references a foreign key, that's why this column is indexed.
 * If not indexed, it might trigger full table scans whenever parent table is modified so you are
 * highly advised to create an index that covers this column.
 * <p>
 * Further information to Parcelable:
 * https://developer.android.com/reference/android/os/Parcelable
 * Why we use Parcelable over Serializable:
 * https://android.jlelse.eu/parcelable-vs-serializable-6a2556d51538
 */
@Entity(tableName = "orders",
        foreignKeys = {
                @ForeignKey(
                        entity = ClientEntity.class,
                        parentColumns = "email",
                        childColumns = "client_email"
                ),
                @ForeignKey(
                        entity = ProductEntity.class,
                        parentColumns = "idProduct",
                        childColumns = "product_id"
                )
        },
        indices = {@Index(value = {"client_email"}),
                @Index(value = {"product_id"})}
)
//@Entity(tableName = "Products",
//        foreignKeys = [ForeignKey(entity = ImagesEntity::class,
//        parentColumns = arrayOf("imageId"), childColumns = arrayOf("cityId")),
//        ForeignKey(entity = PricesEntity::class,
//        parentColumns = arrayOf("skuId"), childColumns = arrayOf("idProduct"))],
//        indices = [Index(value = ["idProduct", "skuId"])])
public class OrderEntity {
    /**
     * https://stackoverflow.com/questions/45988446/how-to-create-a-table-with-a-two-or-more-foreign-keys-using-android-room
     */
    /* Primary key */
    @PrimaryKey(autoGenerate = true)
    private int idOrder;

    /* Columns */
    @ColumnInfo(name = "order_date")
    private String orderDate;

    @ColumnInfo(name = "client_email")
    private String clientEmail;

    @ColumnInfo(name = "product_id")
    private int product_id;


    /* Constructors */
    @Ignore
    public OrderEntity() {
    }

    public OrderEntity(@NonNull String orderDate, String clientEmail, int product_id) {
        this.orderDate = orderDate;
        this.clientEmail = clientEmail;
        this.product_id = product_id;
    }

    /* Methods */
    /* Getters + Setters */
    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }


    @Override
    public String toString() {
        return idOrder + ", " + orderDate + ", " + clientEmail + ", " + product_id;
    }
}
