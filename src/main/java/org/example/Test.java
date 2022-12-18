package org.example;

import org.example.model.Produit;
import org.example.services.ProduitService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class Test {

    public static void main(String[] args) {


        ProduitService ps = new ProduitService();
        ps.begin();

        for (int i = 0; i < 5; i++) {
            ps.create(new Produit(
                    "Je le vaux bien",
                    String.format("n%d", i),
                    new Date(2022, i, 01),
                    420.69 * i,
                    2 * i + 1
            ));
        }
        ps.envoie();

        ps.begin();
        Produit p = ps.findById(2);
        ps.envoie();
        System.out.println(p);

        ps.begin();
        ps.delete(ps.findById(3));
        ps.envoie();

        ps.begin();
        Produit produitWithID1 = ps.findById(1);
        produitWithID1.setStock(produitWithID1.getStock() - 1);
        ps.envoie();

        System.out.println("filter by price");
        ps.begin();
        for (Produit cheapProduit : ps.filterByPrice(1200)
        ) {
            System.out.println(cheapProduit);
        }
        ps.envoie();

        System.out.println("filter by date");
        ps.begin();
        for (Produit cheapProduit : ps.fromDateToDate(new Date(2022, 01, 20), new Date(2022, 04, 10))
        ) {
            System.out.println(cheapProduit);
        }
        ps.envoie();

        ps.close();
    }

}
