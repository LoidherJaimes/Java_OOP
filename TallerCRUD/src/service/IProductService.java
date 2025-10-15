package service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Product;

public interface IProductService {
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    void delete(UUID id);
    Optional<Product> findById(UUID id);
}