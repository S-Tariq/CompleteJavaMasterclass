package stariq.sortedcollections;

import java.util.Map;

public class Main {

    private static StockList stockList = new StockList();

    public static void main(String[] args) {
	    addStockListAndBasket();
    }

    public static void addStockListAndBasket(){

        StockItem temp = new StockItem("bread", 0.86, 100);
        stockList.addStock(temp);

        temp = new StockItem("cake", 1.10, 7);
        stockList.addStock(temp);

        temp = new StockItem("car", 12.50, 2);
        stockList.addStock(temp);

        temp = new StockItem("chair", 62.0, 10);
        stockList.addStock(temp);

        temp = new StockItem("cup", 0.50, 200);
        stockList.addStock(temp);
        temp = new StockItem("cup", 0.45,7);
        stockList.addStock(temp);

        temp = new StockItem("door", 72.95, 4);
        stockList.addStock(temp);

        temp = new StockItem("juice", 2.50, 36);
        stockList.addStock(temp);

        temp = new StockItem("phone", 96.99, 35);
        stockList.addStock(temp);

        temp = new StockItem("towel", 2.40, 80);
        stockList.addStock(temp);

        temp = new StockItem("vase", 8.76, 40);
        stockList.addStock(temp);

        System.out.println(stockList);

        for(String s : stockList.Items().keySet()){
            System.out.println(s);
        }

        Basket annBasket = new Basket("Ann");
        sellItem(annBasket, "car", 1);
//        System.out.println(annBasket);

        sellItem(annBasket, "car", 1);
//        System.out.println(annBasket);

        if(sellItem(annBasket, "car", 1) != 1){
            System.out.println("There are no more cars in stock");
        }

        sellItem(annBasket, "spanner", 5);
//        System.out.println(annBasket);

        sellItem(annBasket, "juice", 4);
        sellItem(annBasket, "cup", 12);
        sellItem(annBasket, "bread", 1);
//        System.out.println(annBasket);

//        System.out.println(stockList);

        Basket basket = new Basket("Customer");
        sellItem(basket,"cup", 100);
        sellItem(basket, "juice", 5);
        removeItem(basket, "cup", 1);
        System.out.println(basket);

        removeItem(annBasket, "car", 1);
        removeItem(annBasket, "cup", 9);
        removeItem(annBasket, "car", 1);
        System.out.println("Cars removed: " + removeItem(annBasket, "car", 1)); // Should not remove any
        System.out.println(annBasket);

        // Removes all items from annBasket.
        removeItem(annBasket, "bread", 1);
        removeItem(annBasket, "cup", 3);
        removeItem(annBasket, "juice", 4);
        removeItem(annBasket,"cup", 3);
        System.out.println(annBasket);

        System.out.println("\nDisplay stock list before and after checkout");
        System.out.println(basket);
        System.out.println(stockList);
        checkOut(basket);
        System.out.println(basket);
        System.out.println(stockList);

        checkOut(annBasket);
        System.out.println(annBasket);

        // Throws an exception since items cannot be added to the unmodifiable stariq.map.
//        temp = new StockItem("pen", 1.12);
//        stockList.Items().put(temp.getName(), temp);

        // This works as changes are being made to the individual object rather than the stariq.map.
        StockItem car = stockList.Items().get("car");
        if (car != null){
            car.adjustStock(2000);
        }
        if(car != null){
            stockList.get("car").adjustStock(-1000);
        }
        System.out.println(stockList);

//        for(Map.Entry<String, Double> price : stockList.PriceList().entrySet()){
//            System.out.println(price.getKey() + " costs " + price.getValue());
//        }
    }

    public static int sellItem(Basket basket, String item, int quantity){
        // Retrieve the item from the stock list.
        StockItem stockItem = stockList.get(item);
        if(stockItem == null){
            System.out.println("Item " + item + " is not sold here.");
            return 0;
        }
        if(stockList.reserveStock(item, quantity) != 0){
            return basket.addToBasket(stockItem, quantity);
        }
        return 0;
    }

    public static int removeItem(Basket basket, String item, int quantity){
        // Retrieve the item from the stock list.
        StockItem stockItem = stockList.get(item);
        if(stockItem == null){
            System.out.println("Item " + item + " is not sold here.");
            return 0;
        }
        if(basket.removeFromBasket(stockItem, quantity) == quantity){
            return stockList.unreserveStock(item, quantity);
        }
        return 0;
    }

    public static void checkOut(Basket basket){
        for(Map.Entry<StockItem, Integer> item : basket.Items().entrySet()){
            stockList.sellStock(item.getKey().getName(), item.getValue());
        }
        basket.clearBasket();
    }
}
