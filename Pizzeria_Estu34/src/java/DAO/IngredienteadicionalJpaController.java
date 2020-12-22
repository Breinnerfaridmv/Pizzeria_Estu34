/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Ingredienteadicional;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Pizzaadicional;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author PC
 */
public class IngredienteadicionalJpaController implements Serializable {

    public IngredienteadicionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingredienteadicional ingredienteadicional) throws PreexistingEntityException, Exception {
        if (ingredienteadicional.getPizzaadicionalList() == null) {
            ingredienteadicional.setPizzaadicionalList(new ArrayList<Pizzaadicional>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pizzaadicional> attachedPizzaadicionalList = new ArrayList<Pizzaadicional>();
            for (Pizzaadicional pizzaadicionalListPizzaadicionalToAttach : ingredienteadicional.getPizzaadicionalList()) {
                pizzaadicionalListPizzaadicionalToAttach = em.getReference(pizzaadicionalListPizzaadicionalToAttach.getClass(), pizzaadicionalListPizzaadicionalToAttach.getIdPizza());
                attachedPizzaadicionalList.add(pizzaadicionalListPizzaadicionalToAttach);
            }
            ingredienteadicional.setPizzaadicionalList(attachedPizzaadicionalList);
            em.persist(ingredienteadicional);
            for (Pizzaadicional pizzaadicionalListPizzaadicional : ingredienteadicional.getPizzaadicionalList()) {
                Ingredienteadicional oldIdIngredienteOfPizzaadicionalListPizzaadicional = pizzaadicionalListPizzaadicional.getIdIngrediente();
                pizzaadicionalListPizzaadicional.setIdIngrediente(ingredienteadicional);
                pizzaadicionalListPizzaadicional = em.merge(pizzaadicionalListPizzaadicional);
                if (oldIdIngredienteOfPizzaadicionalListPizzaadicional != null) {
                    oldIdIngredienteOfPizzaadicionalListPizzaadicional.getPizzaadicionalList().remove(pizzaadicionalListPizzaadicional);
                    oldIdIngredienteOfPizzaadicionalListPizzaadicional = em.merge(oldIdIngredienteOfPizzaadicionalListPizzaadicional);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngredienteadicional(ingredienteadicional.getIdIngrediente()) != null) {
                throw new PreexistingEntityException("Ingredienteadicional " + ingredienteadicional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingredienteadicional ingredienteadicional) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingredienteadicional persistentIngredienteadicional = em.find(Ingredienteadicional.class, ingredienteadicional.getIdIngrediente());
            List<Pizzaadicional> pizzaadicionalListOld = persistentIngredienteadicional.getPizzaadicionalList();
            List<Pizzaadicional> pizzaadicionalListNew = ingredienteadicional.getPizzaadicionalList();
            List<String> illegalOrphanMessages = null;
            for (Pizzaadicional pizzaadicionalListOldPizzaadicional : pizzaadicionalListOld) {
                if (!pizzaadicionalListNew.contains(pizzaadicionalListOldPizzaadicional)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pizzaadicional " + pizzaadicionalListOldPizzaadicional + " since its idIngrediente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pizzaadicional> attachedPizzaadicionalListNew = new ArrayList<Pizzaadicional>();
            for (Pizzaadicional pizzaadicionalListNewPizzaadicionalToAttach : pizzaadicionalListNew) {
                pizzaadicionalListNewPizzaadicionalToAttach = em.getReference(pizzaadicionalListNewPizzaadicionalToAttach.getClass(), pizzaadicionalListNewPizzaadicionalToAttach.getIdPizza());
                attachedPizzaadicionalListNew.add(pizzaadicionalListNewPizzaadicionalToAttach);
            }
            pizzaadicionalListNew = attachedPizzaadicionalListNew;
            ingredienteadicional.setPizzaadicionalList(pizzaadicionalListNew);
            ingredienteadicional = em.merge(ingredienteadicional);
            for (Pizzaadicional pizzaadicionalListNewPizzaadicional : pizzaadicionalListNew) {
                if (!pizzaadicionalListOld.contains(pizzaadicionalListNewPizzaadicional)) {
                    Ingredienteadicional oldIdIngredienteOfPizzaadicionalListNewPizzaadicional = pizzaadicionalListNewPizzaadicional.getIdIngrediente();
                    pizzaadicionalListNewPizzaadicional.setIdIngrediente(ingredienteadicional);
                    pizzaadicionalListNewPizzaadicional = em.merge(pizzaadicionalListNewPizzaadicional);
                    if (oldIdIngredienteOfPizzaadicionalListNewPizzaadicional != null && !oldIdIngredienteOfPizzaadicionalListNewPizzaadicional.equals(ingredienteadicional)) {
                        oldIdIngredienteOfPizzaadicionalListNewPizzaadicional.getPizzaadicionalList().remove(pizzaadicionalListNewPizzaadicional);
                        oldIdIngredienteOfPizzaadicionalListNewPizzaadicional = em.merge(oldIdIngredienteOfPizzaadicionalListNewPizzaadicional);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingredienteadicional.getIdIngrediente();
                if (findIngredienteadicional(id) == null) {
                    throw new NonexistentEntityException("The ingredienteadicional with id " + id + " no longer exists.");
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
            Ingredienteadicional ingredienteadicional;
            try {
                ingredienteadicional = em.getReference(Ingredienteadicional.class, id);
                ingredienteadicional.getIdIngrediente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingredienteadicional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pizzaadicional> pizzaadicionalListOrphanCheck = ingredienteadicional.getPizzaadicionalList();
            for (Pizzaadicional pizzaadicionalListOrphanCheckPizzaadicional : pizzaadicionalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingredienteadicional (" + ingredienteadicional + ") cannot be destroyed since the Pizzaadicional " + pizzaadicionalListOrphanCheckPizzaadicional + " in its pizzaadicionalList field has a non-nullable idIngrediente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ingredienteadicional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingredienteadicional> findIngredienteadicionalEntities() {
        return findIngredienteadicionalEntities(true, -1, -1);
    }

    public List<Ingredienteadicional> findIngredienteadicionalEntities(int maxResults, int firstResult) {
        return findIngredienteadicionalEntities(false, maxResults, firstResult);
    }

    private List<Ingredienteadicional> findIngredienteadicionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingredienteadicional.class));
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

    public Ingredienteadicional findIngredienteadicional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingredienteadicional.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredienteadicionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingredienteadicional> rt = cq.from(Ingredienteadicional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
