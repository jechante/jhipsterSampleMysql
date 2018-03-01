package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import io.github.jhipster.application.domain.enumeration.ServerType;
import io.github.jhipster.application.domain.enumeration.PointQueryType;
import io.github.jhipster.application.domain.enumeration.PoiType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Layer entity. This class is used in LayerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /layers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LayerCriteria implements Serializable {
    /**
     * Class for filtering ServerType
     */
    public static class ServerTypeFilter extends Filter<ServerType> {
    }

    /**
     * Class for filtering PointQueryType
     */
    public static class PointQueryTypeFilter extends Filter<PointQueryType> {
    }

    /**
     * Class for filtering PoiType
     */
    public static class PoiTypeFilter extends Filter<PoiType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter layerName;

    private StringFilter identifier;

    private ServerTypeFilter source;

    private PointQueryTypeFilter pointQueryType;

    private StringFilter style;

    private PoiTypeFilter poiType;

    private StringFilter poiURL;

    private LongFilter legendsId;

    private LongFilter gisServerId;

    private LongFilter layerGroupId;

    public LayerCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLayerName() {
        return layerName;
    }

    public void setLayerName(StringFilter layerName) {
        this.layerName = layerName;
    }

    public StringFilter getIdentifier() {
        return identifier;
    }

    public void setIdentifier(StringFilter identifier) {
        this.identifier = identifier;
    }

    public ServerTypeFilter getSource() {
        return source;
    }

    public void setSource(ServerTypeFilter source) {
        this.source = source;
    }

    public PointQueryTypeFilter getPointQueryType() {
        return pointQueryType;
    }

    public void setPointQueryType(PointQueryTypeFilter pointQueryType) {
        this.pointQueryType = pointQueryType;
    }

    public StringFilter getStyle() {
        return style;
    }

    public void setStyle(StringFilter style) {
        this.style = style;
    }

    public PoiTypeFilter getPoiType() {
        return poiType;
    }

    public void setPoiType(PoiTypeFilter poiType) {
        this.poiType = poiType;
    }

    public StringFilter getPoiURL() {
        return poiURL;
    }

    public void setPoiURL(StringFilter poiURL) {
        this.poiURL = poiURL;
    }

    public LongFilter getLegendsId() {
        return legendsId;
    }

    public void setLegendsId(LongFilter legendsId) {
        this.legendsId = legendsId;
    }

    public LongFilter getGisServerId() {
        return gisServerId;
    }

    public void setGisServerId(LongFilter gisServerId) {
        this.gisServerId = gisServerId;
    }

    public LongFilter getLayerGroupId() {
        return layerGroupId;
    }

    public void setLayerGroupId(LongFilter layerGroupId) {
        this.layerGroupId = layerGroupId;
    }

    @Override
    public String toString() {
        return "LayerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (layerName != null ? "layerName=" + layerName + ", " : "") +
                (identifier != null ? "identifier=" + identifier + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (pointQueryType != null ? "pointQueryType=" + pointQueryType + ", " : "") +
                (style != null ? "style=" + style + ", " : "") +
                (poiType != null ? "poiType=" + poiType + ", " : "") +
                (poiURL != null ? "poiURL=" + poiURL + ", " : "") +
                (legendsId != null ? "legendsId=" + legendsId + ", " : "") +
                (gisServerId != null ? "gisServerId=" + gisServerId + ", " : "") +
                (layerGroupId != null ? "layerGroupId=" + layerGroupId + ", " : "") +
            "}";
    }

}
