package com.gilles.gestionDeStock.repository;


import com.gilles.gestionDeStock.model.MvtStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvtStockRepository extends JpaRepository<MvtStock, Integer> {
}
