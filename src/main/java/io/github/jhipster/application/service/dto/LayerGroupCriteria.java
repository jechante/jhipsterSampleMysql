package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the LayerGroup entity. This class is used in LayerGroupResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /layer-groups?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LayerGroupCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter groupName;

    private LongFilter layersId;

    private LongFilter subGroupsId;

    private LongFilter parentGroupId;

    public LayerGroupCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGroupName() {
        return groupName;
    }

    public void setGroupName(StringFilter groupName) {
        this.groupName = groupName;
    }

    public LongFilter getLayersId() {
        return layersId;
    }

    public void setLayersId(LongFilter layersId) {
        this.layersId = layersId;
    }

    public LongFilter getSubGroupsId() {
        return subGroupsId;
    }

    public void setSubGroupsId(LongFilter subGroupsId) {
        this.subGroupsId = subGroupsId;
    }

    public LongFilter getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(LongFilter parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    @Override
    public String toString() {
        return "LayerGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupName != null ? "groupName=" + groupName + ", " : "") +
                (layersId != null ? "layersId=" + layersId + ", " : "") +
                (subGroupsId != null ? "subGroupsId=" + subGroupsId + ", " : "") +
                (parentGroupId != null ? "parentGroupId=" + parentGroupId + ", " : "") +
            "}";
    }

}
