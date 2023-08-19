package com.mypersonalnetwork.database.entity;

public class RelationType {
    private Integer idRelationType;
    private String nameRelation;

    public RelationType(Integer idRelationType, String nameRelation) {
        this.idRelationType = idRelationType;
        this.nameRelation = nameRelation;
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

}
