package com.switchfully.order.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "ID")
    private String id;

    protected BaseEntity() {
        // JPA requires a no-arg constructor
    }

    protected BaseEntity(UUID id) {
        if (id != null) {
            this.id = id.toString();
        }
    }

    public void generateId() throws IllegalStateException {
        if (id != null) {
            throw new IllegalStateException("Generating an ID for a customer that already has " +
                    "an ID (" + id + ") is not allowed.");
        }
        id = UUID.randomUUID().toString();
    }

    public UUID getId() {
        if(id == null) {
            return null;
        }
        return UUID.fromString(id);
    }

}
