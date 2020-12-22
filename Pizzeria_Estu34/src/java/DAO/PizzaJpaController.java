/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Pizza;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Tipo;
import DTO.Sabor;
import DTO.Pizzaadicional;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author PC
 */
public class PizzaJpaController implements Serializable {

    public PizzaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pizza pizza) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo idTipo = pizza.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getIdTipo());
                pizza.setIdTipo(idTipo);
            }
            Sabor idSabor = pizza.getIdSabor();
            if (idSabor != null) {
                idSabor = em.getReference(idSabor.getClass(), idSabor.getIdSabor());
                pizza.setIdSabor(idSabor);
            }
            Pizzaadicional pizzaadicional = pizza.getPizzaadicional();
            if (pizzaadicional != null) {
                pizzaadicional = em.getReference(pizzaadicional.getClass(), pizzaadicional.getIdPizza());
                pizza.setPizzaadicional(pizzaadicional);
            }
            em.persist(pizza);
            if (idTipo != null) {
                idTipo.getPizzaList().add(pizza);
                idTipo = em.merge(idTipo);
            }
            if (idSabor != null) {
                idSabor.getPizzaList().add(pizza);
                idSabor = em.merge(idSabor);
            }
            if (pizzaadicional != null) {
                Pizza oldPizzaOfPizzaadicional = pizzaadicional.getPizza();
                if (oldPizzaOfPizzaadicional != null) {
                    oldPizzaOfPizzaadicional.setPizzaadicional(null);
                    oldPizzaOfPizzaadicional = em.merge(oldPizzaOfPizzaadicional);
                }
                pizzaadicional.setPizza(pizza);
                pizzaadicional = em.merge(pizzaadicional);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPizza(pizza.getIdPizza()) != null) {
                throw new PreexistingEntityException("Pizza " + pizza + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pizza pizza) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pizza persistentPizza = em.find(Pizza.class, pizza.getIdPizza());
            Tipo idTipoOld = persistentPizza.getIdTipo();
            Tipo idTipoNew = pizza.getIdTipo();
            Sabor idSaborOld = persistentPizza.getIdSabor();
            Sabor idSaborNew = pizza.getIdSabor();
            Pizzaadicional pizzaadicionalOld = persistentPizza.getPizzaadicional();
            Pizzaadicional pizzaadicionalNew = pizza.getPizzaadicional();
            List<String> illegalOrphanMessages = null;
            if (pizzaadicionalOld != null && !pizzaadicionalOld.equals(pizzaadicionalNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pizzaadicional " + pizzaadicionalOld + " since its pizza field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getIdTipo());
                pizza.setIdTipo(idTipoNew);
            }
            if (idSaborNew != null) {
                idSaborNew = em.getReference(idSaborNew.getClass(), idSaborNew.getIdSabor());
                pizza.setIdSabor(idSaborNew);
            }
            if (pizzaadicionalNew != null) {
                pizzaadicionalNew = em.getReference(pizzaadicionalNew.getClass(), pizzaadicionalNew.getIdPizza());
                pizza.setPizzaadicional(pizzaadicionalNew);
            }
            pizza = em.merge(pizza);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getPizzaList().remove(pizza);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getPizzaList().add(pizza);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idSaborOld != null && !idSaborOld.equals(idSaborNew)) {
                idSaborOld.getPizzaList().remove(pizza);
                idSaborOld = em.merge(idSaborOld);
            }
            if (idSaborNew != null && !idSaborNew.equals(idSaborOld)) {
                idSaborNew.getPizzaList().add(pizza);
                idSaborNew = em.merge(idSaborNew);
            }
            if (pizzaadicionalNew != null && !pizzaadicionalNew.equals(pizzaadicionalOld)) {
                Pizza oldPizzaOfPizzaadicional = pizzaadicionalNew.getPizza();
                if (oldPizzaOfPizzaadicional != null) {
                    oldPizzaOfPizzaadicional.setPizzaadicional(null);
                    oldPizzaOfPizzaadicional = em.merge(oldPizzaOfPizzaadicional);
                }
                pizzaadicionalNew.setPizza(pizza);
                pizzaadicionalNew = em.merge(pizzaadicionalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pizza.getIdPizza();
                if (findPizza(id) == null) {
                    throw new NonexistentEntityException("The pizza with id " + id + " no longer exists.");
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
            Pizza pizza;
            try {
                pizza = em.getReference(Pizza.class, id);
                pizza.getIdPizza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pizza with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Pizzaadicional pizzaadicionalOrphanCheck = pizza.getPizzaadicional();
            if (pizzaadicionalOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pizza (" + pizza + ") cannot be destroyed since the Pizzaadicional " + pizzaadicionalOrphanCheck + " in its pizzaadicional field has a non-nullable pizza field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipo idTipo = pizza.getIdTipo();
            if (idTipo != null) {
                idTipo.getPizzaList().remove(pizza);
                idTipo = em.merge(idTipo);
            }
            Sabor idSabor = pizza.getIdSabor();
            if (idSabor != null) {
                idSabor.getPizzaList().remove(pizza);
                idSabor = em.merge(idSabor);
            }
            em.remove(pizza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pizza> findPizzaEntities() {
        return findPizzaEntities(true, -1, -1);
    }

    public List<Pizza> findPizzaEntities(int maxResults, int firstResult) {
        return findPizzaEntities(false, maxResults, firstResult);
    }

    private List<Pizza> findPizzaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pizza.class));
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

    public Pizza findPizza(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pizza.class, id);
        } finally {
            em.close();
        }
    }

    public int getPizzaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pizza> rt = cq.from(Pizza.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
