package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.Product;

public class ProductRepository implements IProductRepository{

    private final List<Product> productCollection;
    
    public ProductRepository(){
        productCollection = new ArrayList<>();
    }

    @Override
    public List<Product> findAll() {
        //Envio una copia para que no se tenga acceso a modificar la lista original desde afuera
        return new ArrayList<>(productCollection);
    }

    @Override
    public void save(Product product) {
        productCollection.add(product);
    }

    @Override
    public void update(Product product) {
        for(int i = 0; i < productCollection.size(); i++)
        {
            if(productCollection.get(i).getId().equals(product.getId()))
            {
                productCollection.set(i, product);
                return;
            }
        }
    }

    @Override
    public boolean delete(UUID id) {
        //for(int i = 0; i < productCollection.size(); i++)
        //{
        //    if(productCollection.get(i).getId() == id())
        //    {
        //        productCollection.remove(i);
        //    }
        //}

        boolean removed = productCollection.removeIf(p -> p.getId() == id);
        return removed;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productCollection.stream().filter(item -> item.getID().equals(id)).findFirst();
    }
}
