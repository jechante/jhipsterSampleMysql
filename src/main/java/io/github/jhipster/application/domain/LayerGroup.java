package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LayerGroup.
 */
@Entity
@Table(name = "layer_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LayerGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "layerGroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Layer> layers = new HashSet<>();

    @OneToMany(mappedBy = "parentGroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LayerGroup> subGroups = new HashSet<>();

    @ManyToOne
    private LayerGroup parentGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public LayerGroup groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Layer> getLayers() {
        return layers;
    }

    public LayerGroup layers(Set<Layer> layers) {
        this.layers = layers;
        return this;
    }

    public LayerGroup addLayers(Layer layer) {
        this.layers.add(layer);
        layer.setLayerGroup(this);
        return this;
    }

    public LayerGroup removeLayers(Layer layer) {
        this.layers.remove(layer);
        layer.setLayerGroup(null);
        return this;
    }

    public void setLayers(Set<Layer> layers) {
        this.layers = layers;
    }

    public Set<LayerGroup> getSubGroups() {
        return subGroups;
    }

    public LayerGroup subGroups(Set<LayerGroup> layerGroups) {
        this.subGroups = layerGroups;
        return this;
    }

    public LayerGroup addSubGroups(LayerGroup layerGroup) {
        this.subGroups.add(layerGroup);
        layerGroup.setParentGroup(this);
        return this;
    }

    public LayerGroup removeSubGroups(LayerGroup layerGroup) {
        this.subGroups.remove(layerGroup);
        layerGroup.setParentGroup(null);
        return this;
    }

    public void setSubGroups(Set<LayerGroup> layerGroups) {
        this.subGroups = layerGroups;
    }

    public LayerGroup getParentGroup() {
        return parentGroup;
    }

    public LayerGroup parentGroup(LayerGroup layerGroup) {
        this.parentGroup = layerGroup;
        return this;
    }

    public void setParentGroup(LayerGroup layerGroup) {
        this.parentGroup = layerGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LayerGroup layerGroup = (LayerGroup) o;
        if (layerGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), layerGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LayerGroup{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            "}";
    }
}
