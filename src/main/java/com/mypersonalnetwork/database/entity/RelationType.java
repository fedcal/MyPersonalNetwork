package com.mypersonalnetwork.database.entity;

public class RelationType {
    private Integer idRelationType;
    private String nameRelation;
    private String typeRelation;

    public RelationType(Integer idRelationType, String nameRelation, String typeRelation) {
        this.idRelationType = idRelationType;
        this.nameRelation = nameRelation;
        this.typeRelation = typeRelation;
    }

    public Integer getIdRelationType() {
        return idRelationType;
    }

    public void setIdRelationType(Integer idRelationType) {
        this.idRelationType = idRelationType;
    }

    public String getNameRelation() {
        return nameRelation;
    }

    public void setNameRelation(String nameRelation) {
        this.nameRelation = nameRelation;
    }

    public String getTypeRelation() {
        return typeRelation;
    }

    public void setTypeRelation(String typeRelation) {
        this.typeRelation = typeRelation;
    }
}
