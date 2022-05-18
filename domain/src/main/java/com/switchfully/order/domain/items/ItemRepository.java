package com.switchfully.order.domain.items;

import com.switchfully.order.domain.Repository;

import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public class ItemRepository extends Repository<Item> {

    @Override
    public List<Item> getAll() {
        return getEntityManager()
                .createQuery("FROM Item", Item.class)
                .getResultList();
    }

    @Override
    public Item get(UUID entityId) {
        return getEntityManager()
                .createQuery("FROM Item WHERE id = :id", Item.class)
                    .setParameter("id", entityId.toString())
                .getSingleResult();
    }
}
