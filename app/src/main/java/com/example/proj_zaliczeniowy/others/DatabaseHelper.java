package com.example.proj_zaliczeniowy.others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.proj_zaliczeniowy.models.OrderModel;
import com.example.proj_zaliczeniowy.models.ProductModel;
import com.example.proj_zaliczeniowy.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
    public static final String COLUMN_CUSTOMER_PHONE = "CUSTOMER_PHONE";
    public static final String COLUMN_CUSTOMER_PASSWORD = "CUSTOMER_PASSWORD";

    public static final String PRODUCTS_TABLE = "PRODUCTS";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_CATEGORY = "PRODUCT_CATEGORY";
    public static final String COLUMN_PRODUCT_CLASS = "PRODUCT_CLASS";
    public static final String COLUMN_PRODUCT_MAGNITUDE = "PRODUCT_MAGNITUDE";
    public static final String COLUMN_PRODUCT_TEMPERATURE = "PRODUCT_TEMPERATURE";
    public static final String COLUMN_PRODUCT_MASS = "PRODUCT_MASS";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION";
    public static final String COLUMN_PRODUCT_IMAGE_PATH = "PRODUCT_IMAGE_PATH";
    public static final String COLUMN_PRODUCT_PRICE = "PRODUCT_PRICE";

    public static final String ORDERS_TABLE = "ORDERS";
    public static final String COLUMN_ORDER_RECIPIENT = "ORDER_RECIPIENT";
    public static final String COLUMN_ORDER_DATE = "ORDER_DATE";
    public static final String COLUMN_ORDER_PRICE = "ORDER_PRICE";
    public static final String COLUMN_ORDER_CONTENT = "ORDER_CONTENT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "gwiazdy.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUsers = "CREATE TABLE " + USERS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_EMAIL + " TEXT, " + COLUMN_CUSTOMER_PHONE + " INTEGER, " + COLUMN_CUSTOMER_PASSWORD + " TEXT);";
        String createTableProduct = "CREATE TABLE " + PRODUCTS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCT_NAME + " TEXT, " + COLUMN_PRODUCT_CATEGORY + " TEXT, " + COLUMN_PRODUCT_CLASS + " TEXT, " + COLUMN_PRODUCT_MAGNITUDE + " FLOAT, " + COLUMN_PRODUCT_TEMPERATURE + " INTEGER, " + COLUMN_PRODUCT_MASS + " INTEGER, " + COLUMN_PRODUCT_DESCRIPTION + " TEXT, " + COLUMN_PRODUCT_IMAGE_PATH + " TEXT, " + COLUMN_PRODUCT_PRICE + " FLOAT);";
        String createTableOrders = "CREATE TABLE " + ORDERS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ORDER_CONTENT + " TEXT, " + COLUMN_ORDER_RECIPIENT + " TEXT, " + COLUMN_ORDER_PRICE + " INTEGER, " + COLUMN_ORDER_DATE + " DATE);";


        db.execSQL(createTableUsers);
        db.execSQL(createTableProduct);
        db.execSQL(createTableOrders);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addOne(UserModel userModel){

        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CUSTOMER_NAME, userModel.getName());
        contentValues.put(COLUMN_CUSTOMER_EMAIL, userModel.getEmail());
        contentValues.put(COLUMN_CUSTOMER_PHONE, userModel.getPhone());
        contentValues.put(COLUMN_CUSTOMER_PASSWORD, userModel.getPassword());

        long insert = db.insert(USERS_TABLE, null, contentValues);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }


    public boolean addProduct(ProductModel productModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PRODUCT_NAME, productModel.getName());
        contentValues.put(COLUMN_PRODUCT_CATEGORY, productModel.getCategory());
        contentValues.put(COLUMN_PRODUCT_CLASS, productModel.getObjClass());
        contentValues.put(COLUMN_PRODUCT_MAGNITUDE, productModel.getMagnitude());
        contentValues.put(COLUMN_PRODUCT_TEMPERATURE, productModel.getTemperature());
        contentValues.put(COLUMN_PRODUCT_MASS, productModel.getMass());
        contentValues.put(COLUMN_PRODUCT_DESCRIPTION, productModel.getDescription());
        contentValues.put(COLUMN_PRODUCT_IMAGE_PATH, productModel.getImagePath());
        contentValues.put(COLUMN_PRODUCT_PRICE, productModel.getPrice());

        long insert = db.insert(PRODUCTS_TABLE, null, contentValues);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addOrder(OrderModel orderModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ORDER_RECIPIENT, orderModel.getRecipient());
        contentValues.put(COLUMN_ORDER_DATE, orderModel.getOrderDate());
        contentValues.put(COLUMN_ORDER_PRICE, orderModel.getTotalPrice());
        contentValues.put(COLUMN_ORDER_CONTENT, orderModel.getContent());

        long insert = db.insert(ORDERS_TABLE, null, contentValues);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }


    public boolean selectOne(String email, String password){

        UserModel returnUser = new UserModel();

        String logInQuery = "SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_CUSTOMER_EMAIL + " = \"" + email + "\" AND " + COLUMN_CUSTOMER_PASSWORD + " = \"" + password + "\";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(logInQuery, null);

        if (cursor.moveToFirst()){
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public List<ProductModel> selectAllProducts(){

        String selectQuery = "SELECT * FROM " + PRODUCTS_TABLE;
        return recycling(selectQuery);
    }

    public List<ProductModel> selectStars(){

        String selectQuery = "SELECT * FROM " + PRODUCTS_TABLE + " WHERE " + COLUMN_PRODUCT_CATEGORY + " = \"star\"";
        return recycling(selectQuery);
    }

    public List<ProductModel> selectExoplanets(){

        String selectQuery = "SELECT * FROM " + PRODUCTS_TABLE + " WHERE " + COLUMN_PRODUCT_CATEGORY + " = \"exoplanet\"";
        return recycling(selectQuery);
    }

    public List<ProductModel> selectSatellites(){

        String selectQuery = "SELECT * FROM " + PRODUCTS_TABLE + " WHERE " + COLUMN_PRODUCT_CATEGORY + " = \"satellite\"";
        return recycling(selectQuery);
    }

    public List<ProductModel> selectAsteroids(){

        String selectQuery = "SELECT * FROM " + PRODUCTS_TABLE + " WHERE " + COLUMN_PRODUCT_CATEGORY + " = \"asteroid\"";
        return recycling(selectQuery);
    }

    public List<ProductModel> selectOneProduct(int id){

        String selectQuery = "SELECT * FROM " + PRODUCTS_TABLE + " WHERE ID = " + id;
        return  recycling(selectQuery);
    }

    public int selectPhoneNumber(String email, String password){
        String selectQuery = "SELECT "+ COLUMN_CUSTOMER_PHONE + " FROM " + USERS_TABLE + " WHERE " + COLUMN_CUSTOMER_EMAIL + " = \"" + email + "\" AND " + COLUMN_CUSTOMER_PASSWORD + " = \"" + password + "\";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int phone = 0;
        if (cursor.moveToFirst()){
            phone = cursor.getInt(0);
        } else {
            Log.e("DATABASE", "XD");
        }
        cursor.close();
        db.close();
        return phone;
    }

    public List<OrderModel> selectAllOrders(){
        String selectQuery = "SELECT * FROM " + ORDERS_TABLE;

        List<OrderModel> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                int orderID = cursor.getInt(0);
                String orderRecipient = cursor.getString(1);
                String orderDate = cursor.getString(2);
                int orderTotalPrice = cursor.getInt(3);
                String orderContent = cursor.getString(4);

                OrderModel newOrder = new OrderModel(orderID, orderRecipient, orderDate, orderTotalPrice, orderContent);
                returnList.add(newOrder);
            } while (cursor.moveToNext());
        } else {
            Log.e("DATABASE", "XD");
        }
        cursor.close();
        db.close();
        return returnList;
    }




    private List<ProductModel> recycling(String selectQuery){
        List<ProductModel> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                String productCategory = cursor.getString(2);
                String productClass = cursor.getString(3);
                float productMagnitude = cursor.getFloat(4);
                int productTemperature = cursor.getInt(5);
                int productMass = cursor.getInt(6);
                String productDescription = cursor.getString(7);
                int productImagePatch = cursor.getInt(8);
                float productPrice = cursor.getFloat(9);

                ProductModel newProduct = new ProductModel(productID, productName, productCategory, productClass, productMagnitude, productTemperature, productMass,productDescription, productImagePatch, productPrice);
                returnList.add(newProduct);
            } while (cursor.moveToNext());

        } else {
            Log.e("DATABASE", "XD");
        }
        cursor.close();
        db.close();
        return returnList;
    }


}
