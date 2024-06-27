package umu.tds.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import umu.tds.model.Interprete;

/**
 * Implementación de la interfaz InterpreteDAO utilizando JPA.
 * Proporciona métodos para agregar, actualizar, eliminar y consultar intérpretes en la base de datos.
 * 
 * @version 1.0
 */
public class JPAInterpreteDAO implements InterpreteDAO {

    private static JPAInterpreteDAO instance;

    private JPAInterpreteDAO() {
    }

    /**
     * Obtiene la instancia única de JPAInterpreteDAO (patrón Singleton).
     * 
     * @return la instancia de JPAInterpreteDAO.
     */
    public static JPAInterpreteDAO getInstance() {
        if (instance == null) {
            instance = new JPAInterpreteDAO();
        }
        return instance;
    }

	@Override
	public int addInterprete(String nombre) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Interprete interprete = new Interprete();
		interprete.setNombre(nombre);

		em.persist(interprete);
		em.getTransaction().commit();
		em.close();

		return interprete.getId();
	}

	@Override
	public void updateInterprete(int id, String nombre) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Interprete interprete = em.find(Interprete.class, id);
		if (interprete != null) {
			interprete.setNombre(nombre);
			em.merge(interprete);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void removeInterprete(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Interprete interprete = em.find(Interprete.class, id);
		if (interprete != null) {
			em.remove(interprete);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Interprete getInterprete(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		Interprete interprete = em.find(Interprete.class, id);
		em.close();
		return interprete;
	}

	@Override
	public List<Interprete> getAllInterpretes() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Interprete> query = em.createQuery("SELECT i FROM Interprete i", Interprete.class);
		List<Interprete> interpretes = query.getResultList();
		em.close();
		return interpretes;
	}
}
