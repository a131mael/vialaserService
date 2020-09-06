package org.viaLaser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.viaLaser.model.Pessoa;
import org.viaLaser.model.PessoaFisica;
import org.viaLaser.model.PessoaJuridica;

@Stateless
public class PessoaService {

	@PersistenceContext(unitName = "ViaLaserDS")
	private EntityManager em;
	

	public List<Pessoa> find(int first, int size, String orderBy, String order, Map<String, Object> filtros) {
		List<Pessoa> pessoas = new ArrayList<>();
		if(filtros.size()==0){
			pessoas.addAll(findPessoas(first, size, orderBy, order, filtros,Pessoa.class));
		}else{
			pessoas.addAll(findPessoas(first, size, orderBy, order, filtros,PessoaJuridica.class));
			pessoas.addAll(findPessoas(first, size, orderBy, order, filtros,PessoaFisica.class));
		}
		return pessoas;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Pessoa> findPessoas(int first, int size, String orderBy, String order, Map<String, Object> filtros, Class tipo) {
		
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery criteria = cb.createQuery(tipo);
			Root member = criteria.from(tipo);
			CriteriaQuery cq = criteria.select(member);
			Map<String, Object> parans = new HashMap<String, Object>();
			
			if(filtros.containsKey("nomeRazao")){
				if(tipo == PessoaJuridica.class){
					parans.put("razaoSocial", filtros.get("nomeRazao"));
				}else{
					parans.put("nome", filtros.get("nomeRazao"));
				}
				parans.remove("nomeRazao");
			}
			if(filtros.containsKey("cpfCnpj")){
				if(tipo == PessoaJuridica.class){
					parans.put("cnpj", filtros.get("cpfCnpj"));
				}else{
					parans.put("cpf", filtros.get("cpfCnpj"));
				}
				parans.remove("cpfCnpj");
			}

			final List<Predicate> predicates = new ArrayList<Predicate>();
			
			for (Map.Entry<String, Object> entry : parans.entrySet()) {

				Predicate pred = cb.and();
				if (entry.getValue() instanceof String) {
					pred = cb.and(pred, cb.like(member.<String> get(entry.getKey()), "%" + entry.getValue() + "%"));
				} else {
					pred = cb.equal(member.get(entry.getKey()), entry.getValue());
				}
				predicates.add(pred);
			}

			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			cq.orderBy((order.equals("asc") ? cb.asc(member.get(orderBy)) : cb.desc(member.get(orderBy))));
			Query q = em.createQuery(criteria);
			q.setFirstResult(first);
			q.setMaxResults(size);
			List<Pessoa> pessoas = new ArrayList<>();
			
			for(Pessoa p : (List<Pessoa>) q.getResultList()){
				if(p instanceof PessoaJuridica){
					((PessoaJuridica) p).getTelefones().size();
				}
				pessoas.add(p);
			}
			return pessoas;

		} catch (NoResultException nre) {
			return new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public long count() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
			Root<Pessoa> member = countQuery.from(Pessoa.class);
			countQuery.select(cb.count(member));

			Query q = em.createQuery(countQuery);
			return (long) q.getSingleResult();

		} catch (NoResultException nre) {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public Pessoa findById(Long id) {
		Pessoa pessoa = em.find(Pessoa.class, id);
		if(pessoa instanceof PessoaJuridica){
			((PessoaJuridica) pessoa).getTelefones().size();
		}
		return pessoa;
	}
	
	public Pessoa save(PessoaFisica pessoaFisica) {
		Pessoa pessoaPersistente = null;
		
		try {
			if (pessoaFisica.getId() != null && pessoaFisica.getId() != 0L) {
				pessoaPersistente = findById(pessoaFisica.getId());
			} else {
				pessoaPersistente = new PessoaFisica();
			}
			
			em.persist(pessoaPersistente);
			
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			// builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("email", "Email taken");

		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());

			e.printStackTrace();
		}

		return pessoaPersistente;
	}
	
	public Pessoa save(PessoaJuridica pessoaJuridica) {
		Pessoa pessoaPersistente = null;
		
		try {
		
			if (pessoaJuridica.getId() != null && pessoaJuridica.getId() != 0L) {
				pessoaPersistente = findById(pessoaJuridica.getId());
			} else {
				pessoaPersistente = new PessoaJuridica();
			}
		
			em.persist(pessoaPersistente);
			
	} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());

			e.printStackTrace();
		}

		return pessoaPersistente;
	}
	
	public Pessoa save(Pessoa pessoa) {
		Pessoa pessoaPersistente = null;
		
		try {
		
			if (pessoa.getId() != null && pessoa.getId() != 0L) {
				pessoaPersistente = findById(pessoa.getId());
				
				if(pessoa instanceof PessoaFisica){
					((PessoaFisica) pessoa).copy((PessoaFisica) pessoaPersistente);	
				}else{
					((PessoaJuridica) pessoa).copy((PessoaJuridica) pessoaPersistente);
				}
				
			} else {
				pessoaPersistente = pessoa;
			}
		
			em.persist(pessoaPersistente);
			
		}catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());

			e.printStackTrace();
		}

		return pessoaPersistente;
	}

	public void remover(Pessoa pessoa) {
		em.remove(findById(pessoa.getId()));
	}
	
}
