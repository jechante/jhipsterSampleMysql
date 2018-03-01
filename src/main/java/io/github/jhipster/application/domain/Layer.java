package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.ServerType;

import io.github.jhipster.application.domain.enumeration.PointQueryType;

import io.github.jhipster.application.domain.enumeration.PoiType;

/**
 * A Layer.
 */
@Entity
@Table(name = "layer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@JsonIdentityInfo(
//    generator = ObjectIdGenerators.PropertyGenerator.class,
//    property = "id")
public class Layer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "layer_name")
    private String layerName;

    /**
     * GIS服务器中该图层标识代码
     */
    @ApiModelProperty(value = "GIS服务器中该图层标识代码")
    @Column(name = "identifier")
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private ServerType source;

    /**
     * 地图点击查询
     */
    @ApiModelProperty(value = "地图点击查询")
    @Enumerated(EnumType.STRING)
    @Column(name = "point_query_type")
    private PointQueryType pointQueryType;

    /**
     * 当ServerType为服务器端渲染且除了默认样式还有其他样式时，需要设置显示样式
     */
    @ApiModelProperty(value = "当ServerType为服务器端渲染且除了默认样式还有其他样式时，需要设置显示样式")
    @Column(name = "style")
    private String style;

    /**
     * POI相关
     */
    @ApiModelProperty(value = "POI相关")
    @Enumerated(EnumType.STRING)
    @Column(name = "poi_type")
    private PoiType poiType;

    @Column(name = "poi_url")
    private String poiURL;

    @OneToMany(mappedBy = "layer",fetch = FetchType.EAGER)
//    @JsonManagedReference
    @JsonIgnoreProperties("layer")
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Legend> legends = new HashSet<>();

    @ManyToOne
    private GisServer gisServer;

    @ManyToOne
    @JsonIgnoreProperties({"layers", "subGroups", "parentGroup"}) // 出于业务考虑，其实可以忽略掉，但出于CRUD维护关系需要保留id和name
    private LayerGroup layerGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLayerName() {
        return layerName;
    }

    public Layer layerName(String layerName) {
        this.layerName = layerName;
        return this;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Layer identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ServerType getSource() {
        return source;
    }

    public Layer source(ServerType source) {
        this.source = source;
        return this;
    }

    public void setSource(ServerType source) {
        this.source = source;
    }

    public PointQueryType getPointQueryType() {
        return pointQueryType;
    }

    public Layer pointQueryType(PointQueryType pointQueryType) {
        this.pointQueryType = pointQueryType;
        return this;
    }

    public void setPointQueryType(PointQueryType pointQueryType) {
        this.pointQueryType = pointQueryType;
    }

    public String getStyle() {
        return style;
    }

    public Layer style(String style) {
        this.style = style;
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public PoiType getPoiType() {
        return poiType;
    }

    public Layer poiType(PoiType poiType) {
        this.poiType = poiType;
        return this;
    }

    public void setPoiType(PoiType poiType) {
        this.poiType = poiType;
    }

    public String getPoiURL() {
        return poiURL;
    }

    public Layer poiURL(String poiURL) {
        this.poiURL = poiURL;
        return this;
    }

    public void setPoiURL(String poiURL) {
        this.poiURL = poiURL;
    }

    public Set<Legend> getLegends() {
        return legends;
    }

    public Layer legends(Set<Legend> legends) {
        this.legends = legends;
        return this;
    }

    public Layer addLegends(Legend legend) {
        this.legends.add(legend);
        legend.setLayer(this);
        return this;
    }

    public Layer removeLegends(Legend legend) {
        this.legends.remove(legend);
        legend.setLayer(null);
        return this;
    }

    public void setLegends(Set<Legend> legends) {
        this.legends = legends;
    }

    public GisServer getGisServer() {
        return gisServer;
    }

    public Layer gisServer(GisServer gisServer) {
        this.gisServer = gisServer;
        return this;
    }

    public void setGisServer(GisServer gisServer) {
        this.gisServer = gisServer;
    }

    public LayerGroup getLayerGroup() {
        return layerGroup;
    }

    public Layer layerGroup(LayerGroup layerGroup) {
        this.layerGroup = layerGroup;
        return this;
    }

    public void setLayerGroup(LayerGroup layerGroup) {
        this.layerGroup = layerGroup;
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
        Layer layer = (Layer) o;
        if (layer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), layer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Layer{" +
            "id=" + getId() +
            ", layerName='" + getLayerName() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", source='" + getSource() + "'" +
            ", pointQueryType='" + getPointQueryType() + "'" +
            ", style='" + getStyle() + "'" +
            ", poiType='" + getPoiType() + "'" +
            ", poiURL='" + getPoiURL() + "'" +
            "}";
    }
}
