package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Legend.
 */
@Entity
@Table(name = "legend")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@JsonIdentityInfo(
//    generator = ObjectIdGenerators.PropertyGenerator.class,
//    property = "id")
public class Legend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "legend_name")
    private String legendName;

    @Column(name = "logo")
    private String logo;

    @ManyToOne
//    @JsonBackReference
    @JsonIgnoreProperties("legends")
    private Layer layer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLegendName() {
        return legendName;
    }

    public Legend legendName(String legendName) {
        this.legendName = legendName;
        return this;
    }

    public void setLegendName(String legendName) {
        this.legendName = legendName;
    }

    public String getLogo() {
        return logo;
    }

    public Legend logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Layer getLayer() {
        return layer;
    }

    public Legend layer(Layer layer) {
        this.layer = layer;
        return this;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
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
        Legend legend = (Legend) o;
        if (legend.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), legend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Legend{" +
            "id=" + getId() +
            ", legendName='" + getLegendName() + "'" +
            ", logo='" + getLogo() + "'" +
            "}";
    }
}
