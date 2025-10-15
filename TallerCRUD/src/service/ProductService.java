package service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import model.Product;
import repository.IProductRepository;


public class ProductService {

    priavte final IProductRepository repository;

    @Override
    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Product product) {
        repository.save(product);
    }

    @Override
    public void update(Product product) {
        repository.update(product);
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }

    @Override
    public Optional<Product> findByID(UUID id) {
        return repository.findById(id);
    }

    
}
