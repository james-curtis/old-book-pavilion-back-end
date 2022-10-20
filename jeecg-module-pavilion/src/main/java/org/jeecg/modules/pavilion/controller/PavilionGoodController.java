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
import org.jeecg.modules.pavilion.entity.PavilionGood;
import org.jeecg.modules.pavilion.service.IPavilionGoodService;

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
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date:   2022-10-20
 * @Version: V1.0
 */
@Api(tags="商品")
@RestController
@RequestMapping("/pavilion/pavilionGood")
@Slf4j
public class PavilionGoodController extends JeecgController<PavilionGood, IPavilionGoodService> {
	@Autowired
	private IPavilionGoodService pavilionGoodService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pavilionGood
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商品-分页列表查询")
	@ApiOperation(value="商品-分页列表查询", notes="商品-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PavilionGood>> queryPageList(PavilionGood pavilionGood,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PavilionGood> queryWrapper = QueryGenerator.initQueryWrapper(pavilionGood, req.getParameterMap());
		Page<PavilionGood> page = new Page<PavilionGood>(pageNo, pageSize);
		IPage<PavilionGood> pageList = pavilionGoodService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pavilionGood
	 * @return
	 */
	@AutoLog(value = "商品-添加")
	@ApiOperation(value="商品-添加", notes="商品-添加")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PavilionGood pavilionGood) {
		pavilionGoodService.save(pavilionGood);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pavilionGood
	 * @return
	 */
	@AutoLog(value = "商品-编辑")
	@ApiOperation(value="商品-编辑", notes="商品-编辑")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PavilionGood pavilionGood) {
		pavilionGoodService.updateById(pavilionGood);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品-通过id删除")
	@ApiOperation(value="商品-通过id删除", notes="商品-通过id删除")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		pavilionGoodService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品-批量删除")
	@ApiOperation(value="商品-批量删除", notes="商品-批量删除")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pavilionGoodService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商品-通过id查询")
	@ApiOperation(value="商品-通过id查询", notes="商品-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PavilionGood> queryById(@RequestParam(name="id",required=true) String id) {
		PavilionGood pavilionGood = pavilionGoodService.getById(id);
		if(pavilionGood==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pavilionGood);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pavilionGood
    */
    //@RequiresPermissions("org.jeecg.modules:pavilion_good:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PavilionGood pavilionGood) {
        return super.exportXls(request, pavilionGood, PavilionGood.class, "商品");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("pavilion_good:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PavilionGood.class);
    }

}
