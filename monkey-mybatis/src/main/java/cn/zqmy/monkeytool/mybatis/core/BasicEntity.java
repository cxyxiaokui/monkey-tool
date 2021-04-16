package cn.zqmy.monkeytool.mybatis.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 基础 Entity 信息
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class BasicEntity {
    /**
     * 创建者
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date updateTime;
    @ApiModelProperty(hidden = true)
    private Integer deleteFlag;
    /**
     * 租户ID
     */
    private Long tenantId;

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
