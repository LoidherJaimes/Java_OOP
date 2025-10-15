package repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Product;

public interface IProductRepository {
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    boolean delete(UUID id);
    Optional<Product> findById(UUID id);
}
