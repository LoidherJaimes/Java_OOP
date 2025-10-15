import model.Product;
import repository.IProductRepository;
import repository.ProductRepository;
import service.IProductService;
import service.ProductService;
import controller.ProductController;

public class App {
    public static void main(String[] args) throws Exception {
        Product product1 = new Product("Fresas", 2500);
        Product product2 = new Product("Aguacates", 5000);
        IProductRepository repository = new ProductRepository();

        ProductService productService = new ProductService(repository);
        productService.save(product1);
        productService.save(product2);

        for (Product product : productService.findAll())
        {
            System.out.println("Primer for " + product.toString());
        }
        System.out.println("=========================================================");
        productService.findById(null);

        SwingUtilities.invoceLater(() -> {
            IProductRepository repository = new ProductRepository();
            ProductService productService = new ProductService(repository);
            ProductView view = new ProductView();
            ProductController controller = new ProductController(view, productService);
            view.setVisible(true);
        });
    }
}