package cn.zqmy.monkeytool.common.core;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @Description: 分页VO对象
 */
@ApiModel(value = "PageListVO", description = "分页VO对象")
public class PageListVO<T> implements Serializable {
    /**
     * 分页查询结果
     */
    @ApiModelProperty(value = "分页查询结果", required = false)
    private List<T> list;
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", required = true)
    private Integer page;
    /**
     * 页尺寸
     */
    @ApiModelProperty(value = "页尺寸", required = true)
    private Integer pagesize;
    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数", required = true)
    private Integer total;

    public PageListVO() {
    }

    /**
     * 构建分页返回结果
     *
     * @param list      当前页条目信息
     * @param page      当前页码
     * @param pagesize  当前页尺寸
     * @param total     总条目数
     * @author guanqi
     */
    public PageListVO(List<T> list, Integer page, Integer pagesize, Integer total) {
        this.list = list;
        this.page = page;
        this.pagesize = pagesize;
        this.total = total;
    }

    /**
     * 构建分页返回结果
     *
     * @param page 当前页码
     * @param pagesize  当前页尺寸
     * @author guanqi
     */
    public PageListVO(Integer page, Integer pagesize) {
        list = new ArrayList<>(0);
        this.page = page;
        this.pagesize = pagesize;
        total = 0;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
