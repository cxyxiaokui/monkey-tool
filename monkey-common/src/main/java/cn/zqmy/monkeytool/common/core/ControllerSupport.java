package cn.zqmy.monkeytool.common.core;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Controller 扩展功能封装
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class ControllerSupport {
    /**
     * 无数据的成功响应
     *
     * @return
     */
    public Result sucess() {
        return ResultGenerator.genSuccessResult();
    }

    /**
     * pageHelper 分页成功响应
     *
     * @param list
     * @return
     */
    public Result pageSucess(List list) {
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 有数据的成功响应
     *
     * @param object
     * @return
     */
    public Result sucess(Object object) {
        return ResultGenerator.genSuccessResult(object);
    }
}
