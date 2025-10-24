package controller;

import model.Product;
import service.IProductService;
import view.ProductView;

public class ProductController {
    private final ProductView view;
    private final IProductService service;

    public ProductController(ProductView view, IProductService service){
        this.view = view;
        this.service = service;
        initController();
        loadTable();
    }

    public void initController(){
        view.btnAdd.addActionListener(e -> addProduct());
        view.btnUpdate.addActionListener(e -> updateProduct());
        view.btnDelete.addActionListener(e -> deleteProduct());
    }

    public void addProduct(){
        Product product = new Product(view.txtName.getText(), Double.parseDouble(view.txtPrice.getText()));
        service.save(product);
        loadTable();
        clearFields();
    }

    public void updateProduct(){
        System.out.println("update presionado!");
    }

    public void deleteProduct(){
        System.out.println("delete presionado!");
    }

    public void loadTable(){
        view.tableModel.setRowCount(0);
        for(Product item : service.findAll()){
            view.tableModel.addRow(new Object[]{item.getId(), item.getName(), item.getPrice()});
        }
    }

    public void clearFields(){
        view.txtName.setText("");
        view.txtPrice.setText("");
    }
}