package repository;

import model.Sale;

import java.util.List;

public interface SaleRepository {
    List<Sale> getAllSales();
    boolean addSale(Sale sale);
    // alte metode necesare pentru gestionarea vânzărilor
}

