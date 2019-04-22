package edu.iupui.cit388.project.controller;

import java.awt.color.CMMException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import edu.iupui.cit388.project.model.CreditCard;
import edu.iupui.cit388.project.model.Item;
import edu.iupui.cit388.project.model.OnlineOrder;
import edu.iupui.cit388.project.model.OrderLine;
import edu.iupui.cit388.project.service.OrderProcessingSystem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

// fx:controller="edu.iupui.cit388.project.controller.OrderProcessingController"
public class OrderProcessingController {

	// your @FXML goes here
	@FXML
	private ListView<OnlineOrder> orderList;

	@FXML
	private TextArea orderDetailTextArea;
	
	@FXML
	private TextArea currentOrderDetailTextArea;

	@FXML
	private Button newOrderBtn;
	
	@FXML
	private Button addItemBtn;

	@FXML
	private Button confirmBtn;
	
	@FXML
	private TextField addressTxt;
	
	@FXML
	private TextField quantityTxt;
	
	@FXML
	private ComboBox<Item> comboItem;
	
	private OrderProcessingSystem system;
	private ObservableList<OnlineOrder> listViewData = FXCollections.observableArrayList();
	private ObservableList<Item> itemComboBoxData = FXCollections.observableArrayList();
	
	private OnlineOrder currentOrder;
	
	@FXML
	void initialize() {

		Path itemDataFile = Paths.get(".\\resource\\Item.txt");
		system = new OrderProcessingSystem(itemDataFile);

		Path orderDataFile = Paths
				.get(".\\resource\\OnlineOrder.txt");
		loadOnlineOrder(system, orderDataFile);

		listViewData.addAll(system.getOrders());
		orderList.setItems(listViewData);
		
		orderList.setCellFactory(new Callback<ListView<OnlineOrder>, ListCell<OnlineOrder>>() {
			@Override
			public ListCell<OnlineOrder> call(ListView<OnlineOrder> param) {
				
				return new ListCell<OnlineOrder>() {
					@Override
					protected void updateItem(OnlineOrder order, boolean empty) {
						super.updateItem(order, empty);

						if (order == null || empty) {
							setText(null);
						} else {
							// what will show in the UI
							setText(order.getId() + "\n" + order.getShipAddress());
						}
					}
				};
			}
		});

		orderList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OnlineOrder>() {

			@Override
			public void changed(ObservableValue<? extends OnlineOrder> observable, OnlineOrder oldValue,
					OnlineOrder newValue) {
				// what to do when an item is selected
				orderDetailTextArea.setText(newValue.receiptDetails());
			}

		});

		newOrderBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				currentOrder = system.createOnlineOrderOnly();
			}
		});
		
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (currentOrder == null) {
					currentOrder = system.createOnlineOrderOnly();
				}
				currentOrder.setShipAddress(addressTxt.getText());
				system.getOrders().add(currentOrder);
				listViewData.add(currentOrder);
				currentOrder = system.createOnlineOrderOnly();
			}
		});
		
		
		addItemBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Item item = comboItem.getSelectionModel().getSelectedItem();
				
				int quantity = getQuantity();
				if (quantity > 0) {
					currentOrder.setShipAddress(addressTxt.getText());
					OrderLine orderLine = new OrderLine(item.getName(), quantity, item.getPrice());
					currentOrder.addOrderLine(orderLine);
					currentOrderDetailTextArea.setText(orderDetails(currentOrder));
					currentOrder = system.createOnlineOrderOnly();
				}
			}
		});
		
		itemComboBoxData.addAll(system.getItems().values());
		comboItem.setItems(itemComboBoxData);
		
		// what to show on drop down
		comboItem.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
			
			@Override
			public ListCell<Item> call(ListView<Item> param) {
				return new ListCell<Item>() {
					@Override
					protected void updateItem(Item item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
						} else {
							setText(item.getName() + " - $" + item.getPrice());
						}
					}
				};
			}
		});

		// what to show when the item is selected
		comboItem.setConverter(new StringConverter<Item>() {
			@Override
			public String toString(Item item) {
				if (item == null) {
					return null;
				} else {
					return item.getName() + " - $" + item.getPrice();
				}
			}

			@Override
			public Item fromString(String personString) {
				return null; // No conversion fromString needed.
			}
		});
	}
	
	private int getQuantity() {
		try {
			return Integer.parseInt(quantityTxt.getText());
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Quantity, please try again.");
			return 0;
		}
	}

	private void loadOnlineOrder(OrderProcessingSystem system, Path orderDataFile) {

		try (Scanner input = new Scanner(orderDataFile)) {

			while (input.hasNext()) {

				OnlineOrder order = system.createOnlineOrder();

				String number = input.next();
				int expireMonth = input.nextInt();
				int expireYear = input.nextInt();
				input.nextLine(); // finish reading the current line
				CreditCard creditCard = new CreditCard(number, expireMonth, expireYear);
				order.setCreditCardInfo(creditCard);

				String shipAddress = input.nextLine();
				order.setShipAddress(shipAddress);

				int numberOfOrderLine = input.nextInt();
				input.nextLine(); // finish reading the current line

				for (int i = 0; i < numberOfOrderLine; i++) {
					String itemDescription = input.next();
					int quantity = input.nextInt();
					double unitPrice = system.getPrice(itemDescription);
					OrderLine orderLine = new OrderLine(itemDescription, quantity, unitPrice);
					order.addOrderLine(orderLine);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
