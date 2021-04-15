package cn.zqmy.monkeytool.common.core;


import com.alibaba.fastjson.JSON;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @Description 分页查询入参
 **/
public class BasePageDTO implements Serializable {

    /**
     * 页面大小
     */
    @NotNull
    @Min(1)
    @Max(200)
    private Integer pagesize;

    /**
     * 页码
     */
    @NotNull
    @Min(0)
    private Integer page;

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
