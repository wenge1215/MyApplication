package com.innolux.bean;


/**
 * Goods��Ϊʵ���࣬������ʹ��
 *
 * @author Administrator
 */
public class BarcodeDetail {

    private String EndGoodTime;
    private String GoodCode;
    private String GoodName;
    private int GoodNum;
    private double GoodPrice;
    private String GoodRack;
    private String OrderSpec;
    private int StockNum;
    private String Supplier;


    @Override
    public String toString() {
        return "BarcodeDetail{" +
                "EndGoodTime='" + EndGoodTime + '\'' +
                ", GoodCode='" + GoodCode + '\'' +
                ", GoodName='" + GoodName + '\'' +
                ", GoodNum=" + GoodNum +
                ", GoodPrice=" + GoodPrice +
                ", GoodRack='" + GoodRack + '\'' +
                ", OrderSpec='" + OrderSpec + '\'' +
                ", StockNum=" + StockNum +
                ", Supplier='" + Supplier + '\'' +
                '}';
    }

    public String getEndGoodTime() {
        return EndGoodTime;
    }

    public void setEndGoodTime(String endGoodTime) {
        EndGoodTime = endGoodTime;
    }

    public String getGoodCode() {
        return GoodCode;
    }

    public void setGoodCode(String goodCode) {
        GoodCode = goodCode;
    }

    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        GoodName = goodName;
    }

    public int getGoodNum() {
        return GoodNum;
    }

    public void setGoodNum(int goodNum) {
        GoodNum = goodNum;
    }

    public double getGoodPrice() {
        return GoodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        GoodPrice = goodPrice;
    }

    public String getGoodRack() {
        return GoodRack;
    }

    public void setGoodRack(String goodRack) {
        GoodRack = goodRack;
    }

    public String getOrderSpec() {
        return OrderSpec;
    }

    public void setOrderSpec(String orderSpec) {
        OrderSpec = orderSpec;
    }

    public int getStockNum() {
        return StockNum;
    }

    public void setStockNum(int stockNum) {
        StockNum = stockNum;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }
}
