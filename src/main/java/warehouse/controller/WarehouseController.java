package warehouse.controller;

import org.springframework.web.bind.annotation.*;
import warehouse.model.ProductData;
import warehouse.repository.WarehouseRepository;

import java.util.List;

@RestController
public class WarehouseController {

    private final WarehouseRepository repository;

    public WarehouseController(WarehouseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/product")
    public List<ProductData> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("/product/{id}")
    public ProductData getProductById(@PathVariable String id) {
        return repository.findByProductID(id);
    }

    @PostMapping("/product")
    public ProductData addProduct(@RequestBody ProductData productData) {
        return repository.save(productData);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable String id) {
        ProductData product = repository.findByProductID(id);
        if (product != null) {
            repository.delete(product);
        }
    }

    @GetMapping("/warehouse")
    public List<ProductData> getAllWarehouses() {
        return repository.findAll();
    }

    @GetMapping("/warehouse/{id}")
    public List<ProductData> getWarehouseById(@PathVariable String id) {
        return repository.findByWarehouseID(id);
    }
}