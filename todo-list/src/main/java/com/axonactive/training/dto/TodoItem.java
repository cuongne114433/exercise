package com.axonactive.training.dto;

import java.time.Instant;

public class TodoItem {
    private Long id;

    private String item;

    private Instant createdDate;

    private Boolean isCompleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = Instant.now();
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
