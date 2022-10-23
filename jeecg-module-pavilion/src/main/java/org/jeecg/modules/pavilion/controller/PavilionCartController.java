package org.jeecg.modules.pavilion.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.pavilion.entity.PavilionCart;
import org.jeecg.modules.pavilion.service.IPavilionCartService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 购物车记录
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
@Api(tags="购物车记录")
@RestController
@RequestMapping("/pavilion/pavilionCart")
@Slf4j
public class PavilionCartController extends JeecgController<PavilionCart, IPavilionCartService> {
	@Autowired
	private IPavilionCartService pavilionCartService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pavilionCart
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "购物车记录-分页列表查询")
	@ApiOperation(value="购物车记录-分页列表查询", notes="购物车记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PavilionCart>> queryPageList(PavilionCart pavilionCart,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PavilionCart> queryWrapper = QueryGenerator.initQueryWrapper(pavilionCart, req.getParameterMap());
		Page<PavilionCart> page = new Page<PavilionCart>(pageNo, pageSize);
		IPage<PavilionCart> pageList = pavilionCartService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pavilionCart
	 * @return
	 */
	@AutoLog(value = "购物车记录-添加")
	@ApiOperation(value="购物车记录-添加", notes="购物车记录-添加")
	//@RequiresPermissions("org.jeecg.modules:pavilion_cart:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PavilionCart pavilionCart) {
		pavilionCartService.save(pavilionCart);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pavilionCart
	 * @return
	 */
	@AutoLog(value = "购物车记录-编辑")
	@ApiOperation(value="购物车记录-编辑", notes="购物车记录-编辑")
	//@RequiresPermissions("org.jeecg.modules:pavilion_cart:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PavilionCart pavilionCart) {
		pavilionCartService.updateById(pavilionCart);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "购物车记录-通过id删除")
	@ApiOperation(value="购物车记录-通过id删除", notes="购物车记录-通过id删除")
	//@RequiresPermissions("org.jeecg.modules:pavilion_cart:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		pavilionCartService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "购物车记录-批量删除")
	@ApiOperation(value="购物车记录-批量删除", notes="购物车记录-批量删除")
	//@RequiresPermissions("org.jeecg.modules:pavilion_cart:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pavilionCartService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "购物车记录-通过id查询")
	@ApiOperation(value="购物车记录-通过id查询", notes="购物车记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PavilionCart> queryById(@RequestParam(name="id",required=true) String id) {
		PavilionCart pavilionCart = pavilionCartService.getById(id);
		if(pavilionCart==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pavilionCart);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pavilionCart
    */
    //@RequiresPermissions("org.jeecg.modules:pavilion_cart:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PavilionCart pavilionCart) {
        return super.exportXls(request, pavilionCart, PavilionCart.class, "购物车记录");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("pavilion_cart:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PavilionCart.class);
    }

}
