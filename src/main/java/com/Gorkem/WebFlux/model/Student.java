package com.Gorkem.WebFlux.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "student")
@Document(indexName = "student")
public class Student implements Serializable, Persistable<UUID> {

    @Id
    private UUID id;

    private String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    private String email;

    private boolean isUpdated = false;

    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    @Column("course_id")
    private Set<UUID> courses;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return !this.isUpdated || id == null;
    }




}
