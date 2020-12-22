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
import DTO.Ingredienteadicional;
import DTO.Pizzaadicional;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author PC
 */
public class PizzaadicionalJpaController implements Serializable {

    public PizzaadicionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pizzaadicional pizzaadicional) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Pizza pizzaOrphanCheck = pizzaadicional.getPizza();
        if (pizzaOrphanCheck != null) {
            Pizzaadicional oldPizzaadicionalOfPizza = pizzaOrphanCheck.getPizzaadicional();
            if (oldPizzaadicionalOfPizza != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pizza " + pizzaOrphanCheck + " already has an item of type Pizzaadicional whose pizza column cannot be null. Please make another selection for the pizza field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pizza pizza = pizzaadicional.getPizza();
            if (pizza != null) {
                pizza = em.getReference(pizza.getClass(), pizza.getIdPizza());
                pizzaadicional.setPizza(pizza);
            }
            Ingredienteadicional idIngrediente = pizzaadicional.getIdIngrediente();
            if (idIngrediente != null) {
                idIngrediente = em.getReference(idIngrediente.getClass(), idIngrediente.getIdIngrediente());
                pizzaadicional.setIdIngrediente(idIngrediente);
            }
            em.persist(pizzaadicional);
            if (pizza != null) {
                pizza.setPizzaadicional(pizzaadicional);
                pizza = em.merge(pizza);
            }
            if (idIngrediente != null) {
                idIngrediente.getPizzaadicionalList().add(pizzaadicional);
                idIngrediente = em.merge(idIngrediente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPizzaadicional(pizzaadicional.getIdPizza()) != null) {
                throw new PreexistingEntityException("Pizzaadicional " + pizzaadicional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pizzaadicional pizzaadicional) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pizzaadicional persistentPizzaadicional = em.find(Pizzaadicional.class, pizzaadicional.getIdPizza());
            Pizza pizzaOld = persistentPizzaadicional.getPizza();
            Pizza pizzaNew = pizzaadicional.getPizza();
            Ingredienteadicional idIngredienteOld = persistentPizzaadicional.getIdIngrediente();
            Ingredienteadicional idIngredienteNew = pizzaadicional.getIdIngrediente();
            List<String> illegalOrphanMessages = null;
            if (pizzaNew != null && !pizzaNew.equals(pizzaOld)) {
                Pizzaadicional oldPizzaadicionalOfPizza = pizzaNew.getPizzaadicional();
                if (oldPizzaadicionalOfPizza != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pizza " + pizzaNew + " already has an item of type Pizzaadicional whose pizza column cannot be null. Please make another selection for the pizza field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pizzaNew != null) {
                pizzaNew = em.getReference(pizzaNew.getClass(), pizzaNew.getIdPizza());
                pizzaadicional.setPizza(pizzaNew);
            }
            if (idIngredienteNew != null) {
                idIngredienteNew = em.getReference(idIngredienteNew.getClass(), idIngredienteNew.getIdIngrediente());
                pizzaadicional.setIdIngrediente(idIngredienteNew);
            }
            pizzaadicional = em.merge(pizzaadicional);
            if (pizzaOld != null && !pizzaOld.equals(pizzaNew)) {
                pizzaOld.setPizzaadicional(null);
                pizzaOld = em.merge(pizzaOld);
            }
            if (pizzaNew != null && !pizzaNew.equals(pizzaOld)) {
                pizzaNew.setPizzaadicional(pizzaadicional);
                pizzaNew = em.merge(pizzaNew);
            }
            if (idIngredienteOld != null && !idIngredienteOld.equals(idIngredienteNew)) {
                idIngredienteOld.getPizzaadicionalList().remove(pizzaadicional);
                idIngredienteOld = em.merge(idIngredienteOld);
            }
            if (idIngredienteNew != null && !idIngredienteNew.equals(idIngredienteOld)) {
                idIngredienteNew.getPizzaadicionalList().add(pizzaadicional);
                idIngredienteNew = em.merge(idIngredienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pizzaadicional.getIdPizza();
                if (findPizzaadicional(id) == null) {
                    throw new NonexistentEntityException("The pizzaadicional with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pizzaadicional pizzaadicional;
            try {
                pizzaadicional = em.getReference(Pizzaadicional.class, id);
                pizzaadicional.getIdPizza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pizzaadicional with id " + id + " no longer exists.", enfe);
            }
            Pizza pizza = pizzaadicional.getPizza();
            if (pizza != null) {
                pizza.setPizzaadicional(null);
                pizza = em.merge(pizza);
            }
            Ingredienteadicional idIngrediente = pizzaadicional.getIdIngrediente();
            if (idIngrediente != null) {
                idIngrediente.getPizzaadicionalList().remove(pizzaadicional);
                idIngrediente = em.merge(idIngrediente);
            }
            em.remove(pizzaadicional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pizzaadicional> findPizzaadicionalEntities() {
        return findPizzaadicionalEntities(true, -1, -1);
    }

    public List<Pizzaadicional> findPizzaadicionalEntities(int maxResults, int firstResult) {
        return findPizzaadicionalEntities(false, maxResults, firstResult);
    }

    private List<Pizzaadicional> findPizzaadicionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pizzaadicional.class));
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

    public Pizzaadicional findPizzaadicional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pizzaadicional.class, id);
        } finally {
            em.close();
        }
    }

    public int getPizzaadicionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pizzaadicional> rt = cq.from(Pizzaadicional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
