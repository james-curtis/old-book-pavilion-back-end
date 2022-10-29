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
import org.jeecg.modules.pavilion.entity.PavilionGoodImage;
import org.jeecg.modules.pavilion.service.IPavilionGoodImageService;

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
 * @Description: 商品图片信息
 * @Author: jeecg-boot
 * @Date:   2022-10-20
 * @Version: V1.0
 */
@Api(tags="商品图片信息")
@RestController
@RequestMapping("/pavilion/pavilionGoodImage")
@Slf4j
public class PavilionGoodImageController extends JeecgController<PavilionGoodImage, IPavilionGoodImageService> {
	@Autowired
	private IPavilionGoodImageService pavilionGoodImageService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pavilionGoodImage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商品图片信息-分页列表查询")
	@ApiOperation(value="商品图片信息-分页列表查询", notes="商品图片信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PavilionGoodImage>> queryPageList(PavilionGoodImage pavilionGoodImage,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PavilionGoodImage> queryWrapper = QueryGenerator.initQueryWrapper(pavilionGoodImage, req.getParameterMap());
		Page<PavilionGoodImage> page = new Page<PavilionGoodImage>(pageNo, pageSize);
		IPage<PavilionGoodImage> pageList = pavilionGoodImageService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pavilionGoodImage
	 * @return
	 */
	@AutoLog(value = "商品图片信息-添加")
	@ApiOperation(value="商品图片信息-添加", notes="商品图片信息-添加")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good_image:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PavilionGoodImage pavilionGoodImage) {
		pavilionGoodImageService.save(pavilionGoodImage);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pavilionGoodImage
	 * @return
	 */
	@AutoLog(value = "商品图片信息-编辑")
	@ApiOperation(value="商品图片信息-编辑", notes="商品图片信息-编辑")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good_image:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PavilionGoodImage pavilionGoodImage) {
		pavilionGoodImageService.updateById(pavilionGoodImage);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品图片信息-通过id删除")
	@ApiOperation(value="商品图片信息-通过id删除", notes="商品图片信息-通过id删除")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good_image:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		pavilionGoodImageService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品图片信息-批量删除")
	@ApiOperation(value="商品图片信息-批量删除", notes="商品图片信息-批量删除")
	//@RequiresPermissions("org.jeecg.modules:pavilion_good_image:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pavilionGoodImageService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商品图片信息-通过id查询")
	@ApiOperation(value="商品图片信息-通过id查询", notes="商品图片信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PavilionGoodImage> queryById(@RequestParam(name="id",required=true) String id) {
		PavilionGoodImage pavilionGoodImage = pavilionGoodImageService.getById(id);
		if(pavilionGoodImage==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pavilionGoodImage);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pavilionGoodImage
    */
    //@RequiresPermissions("org.jeecg.modules:pavilion_good_image:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PavilionGoodImage pavilionGoodImage) {
        return super.exportXls(request, pavilionGoodImage, PavilionGoodImage.class, "商品图片信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("pavilion_good_image:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PavilionGoodImage.class);
    }

}
