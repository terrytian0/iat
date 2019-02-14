package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.KeywordApiService;
import com.terry.iat.service.KeywordService;
import com.terry.iat.service.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author terry @Auther: houyin.tian @Date: 2018/10/20 11:28 @Description:
 */
@ResponseBody
@RestController
@RequestMapping(value = "/keyword")
public class KeywordController extends BaseController {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private KeywordApiService keywordApiService;

    /**
     * 创建关键字
     *
     * @param keywordVO
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result create(@RequestBody KeywordVO keywordVO) {
        return success(keywordService.create(keywordVO));
    }

    /**
     * 修改关键字
     *
     * @param keywordVO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody KeywordVO keywordVO) {
        return success(keywordService.update(keywordVO));
    }

    /**
     * 获取关键字
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result info(Long id) {
        return success(keywordService.getById(id));
    }

    /**
     * 获取关键字列表
     *
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result search(Integer pageNumber, Integer pageSize, String searchText, Long serviceId) {
        return success(keywordService.getByServiceId(pageNumber, pageSize, searchText, serviceId));
    }

    /**
     * 删除关键字
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestBody List<Long> ids) {
        return success(keywordService.delete(ids));
    }

    /**
     * 向关键字中添加Api
     *
     * @param addApiVO
     * @return
     */
    @RequestMapping(value = "/api/add", method = RequestMethod.PUT)
    public Result addApi(@RequestBody AddApiVO addApiVO) {
        return success(keywordApiService.create(addApiVO));
    }

    /**
     * 从关键字中移除Api
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/api/remove", method = RequestMethod.DELETE)
    public Result removeApi(@RequestBody List<Long> ids) {
        return success(keywordApiService.delete(ids));
    }

    /**
     * 关键字中的Api重新排序
     * @param indexVO
     * @return
     */
    @PutMapping(path = "/idx")
    public Result updateIdx(@RequestBody KeywordIndexVO indexVO){
        keywordApiService.updateIdx(indexVO);
        return success();
    }

    @GetMapping(path = "/parameters")
    public Result getParameters(Long keywordId){
        return success(keywordService.getParameters(keywordId,null));
    }

    @PostMapping(path = "/debug")
    public Result debug(@RequestBody KeywordDebugVO keywordDebugVO){
        return success(keywordService.debug(keywordDebugVO));
    }
}
