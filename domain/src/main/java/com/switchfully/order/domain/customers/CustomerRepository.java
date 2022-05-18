package com.switchfully.order.domain.customers;

import com.switchfully.order.domain.Repository;

import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public class CustomerRepository extends Repository<Customer>{

    @Override
    public List<Customer> getAll() {
        return getEntityManager()
                .createQuery("FROM Customer", Customer.class)
                .getResultList();
    }

    @Override
    public Customer get(UUID entityId) {
        return getEntityManager()
                .createQuery("FROM Customer WHERE id = :id", Customer.class)
                    .setParameter("id", entityId.toString())
                .getSingleResult();
    }
}
