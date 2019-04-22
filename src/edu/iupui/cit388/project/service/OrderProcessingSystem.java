package edu.iupui.cit388.project.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.iupui.cit388.project.model.Item;
import edu.iupui.cit388.project.model.OnlineOrder;

public class OrderProcessingSystem {

	private static long storeOrderId = 1000;
	
	private static long onlineOrderId = 1000;
	
	private static Map<String, Item> items = new HashMap<>(); 
		
	private List<OnlineOrder> orders = new ArrayList<>();
	
	public OrderProcessingSystem(Path pathToItemDataFile) {
	
		try (Scanner input = new Scanner(pathToItemDataFile)) {
			
			while(input.hasNext()) {
				String name = input.next();
				double price = input.nextDouble();
				Item item = new Item(name, price);
				items.put(name, item);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		for (String name : items.keySet()) {
			System.out.println(items.get(name));
		}
		System.out.println("Items Load completed! \n\n");
	}
	
	public OnlineOrder createOnlineOrder() {
		OnlineOrder storeOrder = new OnlineOrder(onlineOrderId++, Calendar.getInstance());
		orders.add(storeOrder);
		return storeOrder;
	}

	public OnlineOrder createOnlineOrderOnly() {
		OnlineOrder storeOrder = new OnlineOrder(onlineOrderId++, Calendar.getInstance());
		return storeOrder;
	}

	public double getPrice(String itemName) {
		return items.get(itemName).getPrice();
	}

	public Map<String, Item> getItems() {
		return items;
	}
	
	public List<OnlineOrder> getOrders() {
		return orders;
	}

}
