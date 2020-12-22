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
import DTO.Sabor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author PC
 */
public class SaborJpaController implements Serializable {

    public SaborJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sabor sabor) throws PreexistingEntityException, Exception {
        if (sabor.getPizzaList() == null) {
            sabor.setPizzaList(new ArrayList<Pizza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pizza> attachedPizzaList = new ArrayList<Pizza>();
            for (Pizza pizzaListPizzaToAttach : sabor.getPizzaList()) {
                pizzaListPizzaToAttach = em.getReference(pizzaListPizzaToAttach.getClass(), pizzaListPizzaToAttach.getIdPizza());
                attachedPizzaList.add(pizzaListPizzaToAttach);
            }
            sabor.setPizzaList(attachedPizzaList);
            em.persist(sabor);
            for (Pizza pizzaListPizza : sabor.getPizzaList()) {
                Sabor oldIdSaborOfPizzaListPizza = pizzaListPizza.getIdSabor();
                pizzaListPizza.setIdSabor(sabor);
                pizzaListPizza = em.merge(pizzaListPizza);
                if (oldIdSaborOfPizzaListPizza != null) {
                    oldIdSaborOfPizzaListPizza.getPizzaList().remove(pizzaListPizza);
                    oldIdSaborOfPizzaListPizza = em.merge(oldIdSaborOfPizzaListPizza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSabor(sabor.getIdSabor()) != null) {
                throw new PreexistingEntityException("Sabor " + sabor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sabor sabor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sabor persistentSabor = em.find(Sabor.class, sabor.getIdSabor());
            List<Pizza> pizzaListOld = persistentSabor.getPizzaList();
            List<Pizza> pizzaListNew = sabor.getPizzaList();
            List<String> illegalOrphanMessages = null;
            for (Pizza pizzaListOldPizza : pizzaListOld) {
                if (!pizzaListNew.contains(pizzaListOldPizza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pizza " + pizzaListOldPizza + " since its idSabor field is not nullable.");
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
            sabor.setPizzaList(pizzaListNew);
            sabor = em.merge(sabor);
            for (Pizza pizzaListNewPizza : pizzaListNew) {
                if (!pizzaListOld.contains(pizzaListNewPizza)) {
                    Sabor oldIdSaborOfPizzaListNewPizza = pizzaListNewPizza.getIdSabor();
                    pizzaListNewPizza.setIdSabor(sabor);
                    pizzaListNewPizza = em.merge(pizzaListNewPizza);
                    if (oldIdSaborOfPizzaListNewPizza != null && !oldIdSaborOfPizzaListNewPizza.equals(sabor)) {
                        oldIdSaborOfPizzaListNewPizza.getPizzaList().remove(pizzaListNewPizza);
                        oldIdSaborOfPizzaListNewPizza = em.merge(oldIdSaborOfPizzaListNewPizza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sabor.getIdSabor();
                if (findSabor(id) == null) {
                    throw new NonexistentEntityException("The sabor with id " + id + " no longer exists.");
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
            Sabor sabor;
            try {
                sabor = em.getReference(Sabor.class, id);
                sabor.getIdSabor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sabor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pizza> pizzaListOrphanCheck = sabor.getPizzaList();
            for (Pizza pizzaListOrphanCheckPizza : pizzaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sabor (" + sabor + ") cannot be destroyed since the Pizza " + pizzaListOrphanCheckPizza + " in its pizzaList field has a non-nullable idSabor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sabor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sabor> findSaborEntities() {
        return findSaborEntities(true, -1, -1);
    }

    public List<Sabor> findSaborEntities(int maxResults, int firstResult) {
        return findSaborEntities(false, maxResults, firstResult);
    }

    private List<Sabor> findSaborEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sabor.class));
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

    public Sabor findSabor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sabor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaborCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sabor> rt = cq.from(Sabor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
