package org.example.services;

import org.example.interfaces.IDao;
import org.example.model.Produit;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    private EntityManagerFactory emf;

    private EntityManager em;

    public ProduitService() {
        emf = Persistence.createEntityManagerFactory("demojpa");

        em = emf.createEntityManager();
    }


    @Override
    public void begin() {
        em.getTransaction().begin();
        System.out.println("Demarrrage de la persistance");

    }

    @Override
    public boolean create(Produit o) {
        em.persist(o);
        return true;
    }

    @Override
    public boolean update(Produit o) {
        em.persist(o);
        return true;
    }

    @Override
    public boolean delete(Produit o) {
        em.remove(o);
        return true;
    }

    @Override
    public Produit findById(int id) {
        return em.find(Produit.class,id);
    }

    @Override
    public void envoie() {
        em.getTransaction().commit();
    }

    @Override
    public void close() {
        em.close();
        emf.close();
    }

    @Override
    public List<Produit> findAll() {
        return em.createQuery("select p from Produit p").getResultList();
    }

    public List<Produit> filterByPrice(double min){
        Query query = em.createQuery("select p from Produit p where p.prix <= :min");
        query.setParameter("min",min);
        return query.getResultList();
    }

    public List<Produit> fromDateToDate(Date min, Date max){
        Query query = em.createQuery("select p from Produit p where p.dateAchat between :min and :max");
        query.setParameter("min",min);
        query.setParameter("max",max);

        return query.getResultList();
    }
}
