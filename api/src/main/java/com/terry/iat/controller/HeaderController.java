package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.HeaderService;
import com.terry.iat.service.vo.HeaderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/** @author terry @Auther: houyin.tian @Date: 2018/10/20 11:28 @Description: */
@ResponseBody
@RestController
@RequestMapping(value = "/api/header")
public class HeaderController extends BaseController {

  @Autowired private HeaderService headerService;

  /**
   * 添加Header到Api
   *
   * @param headerVOList
   * @return
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public Result create(@RequestBody List<HeaderVO> headerVOList) {
    return success(headerService.create(headerVOList));
  }

  /**
   * 修改Header
   *
   * @param headerVO
   * @return
   */
  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public Result update(@RequestBody HeaderVO headerVO) {
    return success(headerService.update(headerVO));
  }

  /**
   * 删除Api中的Header
   *
   * @param headerId
   * @return
   */
  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public Result delete(Long headerId) {
    return success(headerService.delete(Arrays.asList(headerId)));
  }

  /**
   * 通过ApiId获取Header列表
   *
   * @param apiId
   * @return
   */
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Result get(Long apiId) {
    return success(headerService.getByApiId(apiId));
  }
}
