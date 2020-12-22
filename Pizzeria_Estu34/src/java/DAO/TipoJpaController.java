/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Pizza;
import DTO.Tipo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author PC
 */
public class TipoJpaController implements Serializable {

    public TipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipo tipo) throws PreexistingEntityException, Exception {
        if (tipo.getPizzaList() == null) {
            tipo.setPizzaList(new ArrayList<Pizza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pizza> attachedPizzaList = new ArrayList<Pizza>();
            for (Pizza pizzaListPizzaToAttach : tipo.getPizzaList()) {
                pizzaListPizzaToAttach = em.getReference(pizzaListPizzaToAttach.getClass(), pizzaListPizzaToAttach.getIdPizza());
                attachedPizzaList.add(pizzaListPizzaToAttach);
            }
            tipo.setPizzaList(attachedPizzaList);
            em.persist(tipo);
            for (Pizza pizzaListPizza : tipo.getPizzaList()) {
                Tipo oldIdTipoOfPizzaListPizza = pizzaListPizza.getIdTipo();
                pizzaListPizza.setIdTipo(tipo);
                pizzaListPizza = em.merge(pizzaListPizza);
                if (oldIdTipoOfPizzaListPizza != null) {
                    oldIdTipoOfPizzaListPizza.getPizzaList().remove(pizzaListPizza);
                    oldIdTipoOfPizzaListPizza = em.merge(oldIdTipoOfPizzaListPizza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipo(tipo.getIdTipo()) != null) {
                throw new PreexistingEntityException("Tipo " + tipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipo tipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo persistentTipo = em.find(Tipo.class, tipo.getIdTipo());
            List<Pizza> pizzaListOld = persistentTipo.getPizzaList();
            List<Pizza> pizzaListNew = tipo.getPizzaList();
            List<String> illegalOrphanMessages = null;
            for (Pizza pizzaListOldPizza : pizzaListOld) {
                if (!pizzaListNew.contains(pizzaListOldPizza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pizza " + pizzaListOldPizza + " since its idTipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pizza> attachedPizzaListNew = new ArrayList<Pizza>();
            for (Pizza pizzaListNewPizzaToAttach : pizzaListNew) {
                pizzaListNewPizzaToAttach = em.getReference(pizzaListNewPizzaToAttach.getClass(), pizzaListNewPizzaToAttach.getIdPizza());
                attachedPizzaListNew.add(pizzaListNewPizzaToAttach);
            }
            pizzaListNew = attachedPizzaListNew;
            tipo.setPizzaList(pizzaListNew);
            tipo = em.merge(tipo);
            for (Pizza pizzaListNewPizza : pizzaListNew) {
                if (!pizzaListOld.contains(pizzaListNewPizza)) {
                    Tipo oldIdTipoOfPizzaListNewPizza = pizzaListNewPizza.getIdTipo();
                    pizzaListNewPizza.setIdTipo(tipo);
                    pizzaListNewPizza = em.merge(pizzaListNewPizza);
                    if (oldIdTipoOfPizzaListNewPizza != null && !oldIdTipoOfPizzaListNewPizza.equals(tipo)) {
                        oldIdTipoOfPizzaListNewPizza.getPizzaList().remove(pizzaListNewPizza);
                        oldIdTipoOfPizzaListNewPizza = em.merge(oldIdTipoOfPizzaListNewPizza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipo.getIdTipo();
                if (findTipo(id) == null) {
                    throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo tipo;
            try {
                tipo = em.getReference(Tipo.class, id);
                tipo.getIdTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pizza> pizzaListOrphanCheck = tipo.getPizzaList();
            for (Pizza pizzaListOrphanCheckPizza : pizzaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipo (" + tipo + ") cannot be destroyed since the Pizza " + pizzaListOrphanCheckPizza + " in its pizzaList field has a non-nullable idTipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipo> findTipoEntities() {
        return findTipoEntities(true, -1, -1);
    }

    public List<Tipo> findTipoEntities(int maxResults, int firstResult) {
        return findTipoEntities(false, maxResults, firstResult);
    }

    private List<Tipo> findTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tipo findTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipo> rt = cq.from(Tipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
