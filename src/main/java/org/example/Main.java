package org.example;

import org.example.model.Personne;
import org.example.model.PersonneWithPK;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demojpa");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transac = em.getTransaction();

        transac.begin();

        Personne personne;

        for (int i = 0; i < 20; i++) {
            personne = new Personne();
            personne.setNom(String.format("%d of his name", i));
            personne.setPrenom("toto");
            em.persist(personne);
            transac.commit();
            transac.begin();
            personne = null;
        }

        PersonneWithPK newPKp = new PersonneWithPK();
        newPKp.setPrenom("totowithpk");
        newPKp.setNom("Le seul, l'unique");

        em.merge(newPKp);

        transac.commit();

        transac.begin();

        Personne p2 = em.find(Personne.class, 1);
        System.out.println("found this person with ID==1 : " + p2);

        Personne p3 = em.getReference(Personne.class, 1);
        System.out.println("getReferenced this person with ID==1 : " + p3);

        transac.commit();

        Query query1 = em.createQuery("select p from Personne p where p.nom='2 of his name'");
        Personne p4 = (Personne) query1.getSingleResult();
        System.out.println("got 2 with query : " + p4);

        Query query2 = em.createQuery("select p from Personne p where p.id > 5");
        List noms = query2.getResultList();

        for (Object nom : noms
        ) {
            Personne tmp = (Personne) nom;
            System.out.println("nom=" + tmp.getNom());
        }

        Query query3 = em.createQuery("select p from Personne p where p.id > :id");
        query3.setParameter("id", 15);
        List noms2 = query3.getResultList();

        for (Object nom: noms2
             ) {
            Personne tmp = (Personne) nom;
            System.out.println("nom = "+tmp.getNom());
        }

        transac.begin();
        Personne p5 = em.find(Personne.class, 1);
        p5.setNom("the changed");
        p5.setPrenom("changedToto");
        em.flush();
        transac.commit();

        // em.remove()
        transac.begin();
        Personne p6 = em.find(Personne.class, 6); // 5 of his name
        em.remove(p6);
        transac.commit();

        em.close();
        emf.close();

    }
}