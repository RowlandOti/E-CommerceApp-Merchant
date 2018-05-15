package com.rowland.delivery.utilities;

import com.rowland.delivery.features.dash.domain.models.order.OrderData;
import com.rowland.delivery.features.dash.domain.models.order.OrderItem;
import com.rowland.delivery.features.dash.domain.models.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Rowland on 5/9/2018.
 */

public class DumyDataUtility {

    public static Product createProduct() {
        Product product = new Product();
        product.setCreatedAt(new Date());
        product.setDeletedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setDescription("Brown cake simmered in mayoinase");
        product.setId(new Random().nextInt(100));
        product.setImageUrl("https://image.tmdb.org/t/p/w500/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg");
        product.setItemCode("tWd");
        product.setItemQuantity(new Random().nextInt(25));
        product.setMerchantCode("34dsdfEd");
        product.setName("Brown Cake");
        product.setSaleQuantity(3);
        product.setUnitsOfMeasure(4);
        product.setPrice(new Random().nextInt(2000));

        return product;
    }

    public static OrderData createOrderData() {

        OrderData data = new OrderData();
        data.setAddress("River Side Drive 44");
        data.setCreatedAt(new Date().toString());
        data.setLat("45.0");
        data.setLng("34.5");
        data.setId(23);
        data.setItemIds("TeUbdhD");
        data.setName(randomCustomerName());
        data.setDeliveryFee(250);
        data.setOrderItemsQuantity(new Random().nextInt(40));
        data.setOrderRef("Tydsgeb");
        data.setOrderSubTotal("34");
        data.setOrderTotal(245);
        data.setUpdatedAt(new Date().toString());
        data.setStatus(randomStatus());
        data.setOrderDescription("Hot and Sliced Hamper");
        data.setPhone("0712847884");

        List<OrderItem> items = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setForeign(false);
        item.setImageUrl("https://image.tmdb.org/t/p/w500/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg");
        item.setItemCode("rTs");
        item.setItemQuantity(15);
        item.setItemTotal(40);
        item.setPrice(1200);
        item.setTitle("McFries Delicacies");

        items.add(item);

        OrderItem item2 = new OrderItem();
        item2.setForeign(false);
        item2.setImageUrl("https://image.tmdb.org/t/p/w500/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg");
        item2.setItemCode("rWd");
        item2.setItemQuantity(3);
        item2.setItemTotal(5);
        item2.setPrice(200);
        item2.setTitle("Brown Cake");

        items.add(item2);

        data.setItems(items);

        return data;
    }

    public static String randomStatus() {
        String[] status = {"pending", "completed", "in_progress", "cancelled", "active", "failed", "delivered", "missed"};
        return status[new Random().nextInt(status.length - 1)];
    }

    public static String randomCustomerName() {
        String[] names = {"Rowland", "Pete", "Antony", "Mary", "Cleo", "Faith", "Ron", "Tim"};
        return names[new Random().nextInt(names.length - 1)];
    }
}
