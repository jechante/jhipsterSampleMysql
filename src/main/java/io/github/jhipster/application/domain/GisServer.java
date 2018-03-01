package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.ServerVendor;

/**
 * A GisServer.
 */
@Entity
@Table(name = "gis_server")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GisServer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "vendor")
    private ServerVendor vendor;

    @OneToMany(mappedBy = "gisServer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Layer> layers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public GisServer serverName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUrl() {
        return url;
    }

    public GisServer url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ServerVendor getVendor() {
        return vendor;
    }

    public GisServer vendor(ServerVendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(ServerVendor vendor) {
        this.vendor = vendor;
    }

    public Set<Layer> getLayers() {
        return layers;
    }

    public GisServer layers(Set<Layer> layers) {
        this.layers = layers;
        return this;
    }

    public GisServer addLayers(Layer layer) {
        this.layers.add(layer);
        layer.setGisServer(this);
        return this;
    }

    public GisServer removeLayers(Layer layer) {
        this.layers.remove(layer);
        layer.setGisServer(null);
        return this;
    }

    public void setLayers(Set<Layer> layers) {
        this.layers = layers;
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
        GisServer gisServer = (GisServer) o;
        if (gisServer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gisServer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GisServer{" +
            "id=" + getId() +
            ", serverName='" + getServerName() + "'" +
            ", url='" + getUrl() + "'" +
            ", vendor='" + getVendor() + "'" +
            "}";
    }
}
