package OrderingSystem;

import java.util.TreeMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import models.OrderDetail;
import models.Product;
import models.ReadCategoryProduct;

public class AppPosVerticle extends Application {

    private TilePane menuJuice = getProductCategoryMenu("零食");
    TilePane menuTea = getProductCategoryMenu("飲料");
    TilePane menuCoffee = getProductCategoryMenu("文具");
    TilePane menuOthers = getProductCategoryMenu("其他");
    TilePane menuBook = getProductCategoryMenu("書本");
    TilePane menufruit = getProductCategoryMenu("水果");

    private ObservableList<OrderDetail> order_list;
    private TableView<OrderDetail> table;
    private final TreeMap<String, Product> product_dict = ReadCategoryProduct.readProduct();
    private final TextArea display = new TextArea();
    private TextField discountField;
    private Button applyDiscountButton;

    VBox menuContainerPane = new VBox();

    public TilePane getProductCategoryMenu(String category) {
        TreeMap<String, Product> product_dict = ReadCategoryProduct.readProduct();
        TilePane category_menu = new TilePane();
        category_menu.setVgap(10);
        category_menu.setHgap(10);
        category_menu.setPrefColumns(4);

        for (String item_id : product_dict.keySet()) {
            if (product_dict.get(item_id).getCategory().equals(category)) {
                Button btn = new Button();
                btn.setPrefSize(150, 150);
                Image img = new Image("/imgs/" + product_dict.get(item_id).getPhoto());
                ImageView imgview = new ImageView(img);
                imgview.setFitHeight(100);
                imgview.setPreserveRatio(true);
                btn.setGraphic(imgview);
                category_menu.getChildren().add(btn);

                btn.setOnAction(event -> {
                    addToCart(item_id);
                    System.out.println(product_dict.get(item_id).getName());
                });
            }
        }
        return category_menu;
    }

    public TilePane getMenuSelectionContainer() {
        Button btnJuice = new Button("零食");
        btnJuice.setOnAction(event -> {
            menuContainerPane.getChildren().clear();
            menuContainerPane.getChildren().add(menuJuice);
        });
        Button btnTea = new Button("飲料");
        btnTea.setOnAction(this::select_category_menu);
        Button btnCoffee = new Button("文具");
        btnCoffee.setOnAction(this::select_category_menu);
        Button btnOthers = new Button("其他");
        btnOthers.setOnAction(this::select_category_menu);
        Button btnBook = new Button("書本");
        btnBook.setOnAction(this::select_category_menu);
        Button btnfruit = new Button("水果");
        btnfruit.setOnAction(this::select_category_menu);

        TilePane conntainerCategoryMenuBtn = new TilePane();
        conntainerCategoryMenuBtn.setVgap(10);
        conntainerCategoryMenuBtn.setHgap(10);
        conntainerCategoryMenuBtn.getChildren().addAll(btnJuice, btnTea, btnCoffee, btnOthers, btnBook, btnfruit);
        return conntainerCategoryMenuBtn;
    }

    public void select_category_menu(ActionEvent event) {
        String category = ((Button) event.getSource()).getText();
        menuContainerPane.getChildren().clear();
        switch (category) {
            case "零食":
                menuContainerPane.getChildren().add(menuJuice);
                break;
            case "飲料":
                menuContainerPane.getChildren().add(menuTea);
                break;
            case "文具":
                menuContainerPane.getChildren().add(menuCoffee);
                break;
            case "其他":
                menuContainerPane.getChildren().add(menuOthers);
                break;
            case "水果":
                menuContainerPane.getChildren().add(menufruit);
                break;
            case "書本":
                menuContainerPane.getChildren().add(menuBook);
                break;
            default:
                break;
        }
    }

    public TilePane getOrderOperationContainer() {
        Button btnAdd = new Button("新增一筆");
        btnAdd.setOnAction(event -> {
            addToCart("p-j-101");
            System.out.println("購入");
        });

        Button btnDelete = new Button("刪除一筆");
        btnDelete.setOnAction(event -> {
            OrderDetail selectedItem = table.getSelectionModel().getSelectedItem();
            order_list.remove(selectedItem);
            checkTotal();
            System.out.println("執行刪除訂單!");
            System.out.println(selectedItem.getProduct_name());
        });

        TilePane operationBtnTile = new TilePane();
        operationBtnTile.setVgap(10);
        operationBtnTile.setHgap(10);
        operationBtnTile.getChildren().addAll(btnAdd, btnDelete);

        return operationBtnTile;
    }

    public void initializeOrderTable() {
        discountField = new TextField();
        discountField.setPromptText("Enter discount % or code");

        applyDiscountButton = new Button("Apply discount");
        applyDiscountButton.setOnAction(event -> applyDiscount());

        TilePane discountPane = new TilePane();
        discountPane.setVgap(10);
        discountPane.setHgap(10);
        discountPane.getChildren().addAll(discountField, applyDiscountButton);

        order_list = FXCollections.observableArrayList();
        table = new TableView<>();
        table.setEditable(true);
        table.setPrefHeight(300);

        TableColumn<OrderDetail, String> order_item_name = new TableColumn<>("品名");
        order_item_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        order_item_name.setPrefWidth(100);
        order_item_name.setMinWidth(100);

        TableColumn<OrderDetail, Integer> order_item_price = new TableColumn<>("價格");
        order_item_price.setCellValueFactory(new PropertyValueFactory<>("product_price"));

        TableColumn<OrderDetail, Integer> order_item_qty = new TableColumn<>("數量");
        order_item_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        order_item_qty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        order_item_qty.setOnEditCommit(event -> {
            int row_num = event.getTablePosition().getRow();
            int new_val = event.getNewValue();
            OrderDetail target = event.getTableView().getItems().get(row_num);
            target.setQuantity(new_val);
            checkTotal();
            System.out.println("哪個產品被修改數量:" + order_list.get(row_num).getProduct_name());
            System.out.println("數量被修改為:" + order_list.get(row_num).getQuantity());
        });

        table.setItems(order_list);
        table.getColumns().addAll(order_item_name, order_item_price, order_item_qty);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void checkTotal() {
        double total = 0;
        for (OrderDetail od : order_list) {
            total += od.getProduct_price() * od.getQuantity();
        }

        double discount = 0;
        String discountCode = discountField.getText();
        try {
            if (!discountCode.isEmpty()) {
                if (discountCode.equals("C112118212")) {
                    discount = 30; // Apply 30% discount
                } 
                total -= total * (discount / 100);
            }
        } catch (NumberFormatException e) {
            display.setText("Invalid discount value. Please enter a valid percentage or discount code.");
            return;
        }

        String totalmsg = String.format("總金額: %.2f", total);
        display.setText(totalmsg);
    }

    public void applyDiscount() {
        checkTotal();
    }

    public void addToCart(String item_id) {
        boolean duplication = false;
        for (OrderDetail od : order_list) {
            if (od.getProduct_id().equals(item_id)) {
                od.setQuantity(od.getQuantity() + 1);
                duplication = true;
                table.refresh();
                checkTotal();
                System.out.println(item_id + "此筆已經加入購物車，數量+1");
                break;
            }
        }
        if (!duplication) {
            OrderDetail new_ord = new OrderDetail(item_id, product_dict.get(item_id).getName(), product_dict.get(item_id).getPrice(), 1);
            order_list.add(new_ord);
            checkTotal();
        }
    }

    @Override
public void start(Stage stage) throws Exception {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(20, 20, 20, 20));
    root.getStylesheets().add("/css/bootstrap3.css");

    root.getChildren().add(getMenuSelectionContainer());
    menuContainerPane.getChildren().add(menuJuice);
    root.getChildren().add(menuContainerPane);

    root.getChildren().add(getOrderOperationContainer());
    initializeOrderTable();
    root.getChildren().add(table);
   
    // Discount input and apply button
    discountField = new TextField();
    discountField.setPromptText("輸入優惠碼");
    applyDiscountButton = new Button("使用優惠碼");//使用後取得三折
    applyDiscountButton.setOnAction(event -> applyDiscount());

    TilePane discountPane = new TilePane();
    discountPane.setVgap(10);
    discountPane.setHgap(10);
    discountPane.getChildren().addAll(discountField, applyDiscountButton);
    root.getChildren().add(discountPane);

    display.setPrefColumnCount(10);
    display.setPrefHeight(100);
    root.getChildren().add(display);

    Scene scene = new Scene(root, 800, 600);
    stage.setTitle("小飛國小福利社訂購系統");
    stage.setScene(scene);
    stage.show();
}



    public static void main(String[] args) {
        launch(args);
    }
}
