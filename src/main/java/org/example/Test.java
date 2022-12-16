package org.example;

import org.example.model.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class Test {

    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demojpa");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transac = em.getTransaction();

        transac.begin();

        for (int i = 0; i < 5; i++) {
            Produit faveFiveProduit = new Produit(
                    "Je le vaux bien",
                    String.format("n%d",i),
                    new Date(),
                    420.69,
                    2*i +1
            );
            em.persist(faveFiveProduit);
        }
        transac.commit();


        transac.begin();
        Produit ProduitWithID2 = em.find(Produit.class,2);
        transac.commit();
        System.out.println(ProduitWithID2);

        transac.begin();
        Produit ProduitWithID3 = em.find(Produit.class,3);
        em.remove(ProduitWithID3);
        transac.commit();

        transac.begin();
        Produit ProduitWithID1 = em.find(Produit.class,1);
        ProduitWithID1.setStock(ProduitWithID1.getStock()-1);
        em.flush();
        transac.commit();

        em.close();
        emf.close();
    }


}
