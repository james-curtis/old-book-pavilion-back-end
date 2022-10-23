package org.jeecg.modules.pavilion.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.pavilion.entity.PavilionGoodImage;
import org.jeecg.modules.pavilion.entity.PavilionGood;
import org.jeecg.modules.pavilion.vo.PavilionGoodPage;
import org.jeecg.modules.pavilion.service.IPavilionGoodService;
import org.jeecg.modules.pavilion.service.IPavilionGoodImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
@Api(tags="商品")
@RestController
@RequestMapping("/pavilion/pavilionGood")
@Slf4j
public class PavilionGoodController {
	@Autowired
	private IPavilionGoodService pavilionGoodService;
	@Autowired
	private IPavilionGoodImageService pavilionGoodImageService;
	
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
	 * @param pavilionGoodPage
	 * @return
	 */
	@AutoLog(value = "商品-添加")
	@ApiOperation(value="商品-添加", notes="商品-添加")
    //@RequiresPermissions("org.jeecg.modules:pavilion_good:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PavilionGoodPage pavilionGoodPage) {
		PavilionGood pavilionGood = new PavilionGood();
		BeanUtils.copyProperties(pavilionGoodPage, pavilionGood);
		pavilionGoodService.saveMain(pavilionGood, pavilionGoodPage.getPavilionGoodImageList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pavilionGoodPage
	 * @return
	 */
	@AutoLog(value = "商品-编辑")
	@ApiOperation(value="商品-编辑", notes="商品-编辑")
    //@RequiresPermissions("org.jeecg.modules:pavilion_good:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PavilionGoodPage pavilionGoodPage) {
		PavilionGood pavilionGood = new PavilionGood();
		BeanUtils.copyProperties(pavilionGoodPage, pavilionGood);
		PavilionGood pavilionGoodEntity = pavilionGoodService.getById(pavilionGood.getId());
		if(pavilionGoodEntity==null) {
			return Result.error("未找到对应数据");
		}
		pavilionGoodService.updateMain(pavilionGood, pavilionGoodPage.getPavilionGoodImageList());
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
		pavilionGoodService.delMain(id);
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
		this.pavilionGoodService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
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
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商品图片信息-通过主表ID查询")
	@ApiOperation(value="商品图片信息-通过主表ID查询", notes="商品图片信息-通过主表ID查询")
	@GetMapping(value = "/queryPavilionGoodImageByMainId")
	public Result<IPage<PavilionGoodImage>> queryPavilionGoodImageListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PavilionGoodImage> pavilionGoodImageList = pavilionGoodImageService.selectByMainId(id);
		IPage <PavilionGoodImage> page = new Page<>();
		page.setRecords(pavilionGoodImageList);
		page.setTotal(pavilionGoodImageList.size());
		return Result.OK(page);
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
      // Step.1 组装查询条件查询数据
      QueryWrapper<PavilionGood> queryWrapper = QueryGenerator.initQueryWrapper(pavilionGood, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
           List<String> selectionList = Arrays.asList(selections.split(","));
           queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<PavilionGood>  pavilionGoodList = pavilionGoodService.list(queryWrapper);

      // Step.3 组装pageList
      List<PavilionGoodPage> pageList = new ArrayList<PavilionGoodPage>();
      for (PavilionGood main : pavilionGoodList) {
          PavilionGoodPage vo = new PavilionGoodPage();
          BeanUtils.copyProperties(main, vo);
          List<PavilionGoodImage> pavilionGoodImageList = pavilionGoodImageService.selectByMainId(main.getId());
          vo.setPavilionGoodImageList(pavilionGoodImageList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "商品列表");
      mv.addObject(NormalExcelConstants.CLASS, PavilionGoodPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品数据", "导出人:"+sysUser.getRealname(), "商品"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("org.jeecg.modules:pavilion_good:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<PavilionGoodPage> list = ExcelImportUtil.importExcel(file.getInputStream(), PavilionGoodPage.class, params);
              for (PavilionGoodPage page : list) {
                  PavilionGood po = new PavilionGood();
                  BeanUtils.copyProperties(page, po);
                  pavilionGoodService.saveMain(po, page.getPavilionGoodImageList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
