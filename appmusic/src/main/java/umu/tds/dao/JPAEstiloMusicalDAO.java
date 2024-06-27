package umu.tds.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import umu.tds.model.EstiloMusical;

/**
 * Implementación de la interfaz EstiloMusicalDAO utilizando JPA.
 * Proporciona métodos para agregar, actualizar, eliminar y consultar estilos musicales en la base de datos.
 * 
 * @version 1.0
 */
public class JPAEstiloMusicalDAO implements EstiloMusicalDAO {

    private static JPAEstiloMusicalDAO instance;

    private JPAEstiloMusicalDAO() {
    }

    /**
     * Obtiene la instancia única de JPAEstiloMusicalDAO (patrón Singleton).
     * 
     * @return la instancia de JPAEstiloMusicalDAO.
     */
    public static JPAEstiloMusicalDAO getInstance() {
        if (instance == null) {
            instance = new JPAEstiloMusicalDAO();
        }
        return instance;
    }

    
    @Override
    public int addEstiloMusical(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        EstiloMusical estilo = new EstiloMusical();
        estilo.setNombre(nombre);
        em.persist(estilo);
        em.getTransaction().commit();
        em.close();
        return estilo.getId();
    }

   
    @Override
    public void updateEstiloMusical(int id, String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        EstiloMusical estilo = em.find(EstiloMusical.class, id);
        if (estilo != null) {
            estilo.setNombre(nombre);
            em.merge(estilo);
        }
        em.getTransaction().commit();
        em.close();
    }

    
    @Override
    public void removeEstiloMusical(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        EstiloMusical estilo = em.find(EstiloMusical.class, id);
        if (estilo != null) {
            em.remove(estilo);
        }
        em.getTransaction().commit();
        em.close();
    }

    
    @Override
    public EstiloMusical getEstiloMusicalByName(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<EstiloMusical> query = em.createQuery("SELECT e FROM EstiloMusical e WHERE e.nombre = :nombre", EstiloMusical.class);
        query.setParameter("nombre", nombre);
        EstiloMusical estilo = query.getSingleResult();
        em.close();
        return estilo;
    }

    
    @Override
    public List<EstiloMusical> getAllEstilosMusicales() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<EstiloMusical> query = em.createQuery("SELECT e FROM EstiloMusical e", EstiloMusical.class);
        List<EstiloMusical> estilos = query.getResultList();
        em.close();
        return estilos;
    }
}
