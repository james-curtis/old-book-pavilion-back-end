package org.jeecg.modules.pavilion.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.pavilion.entity.PavilionBook;
import org.jeecg.modules.pavilion.service.IPavilionBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * @Description: 图书
 * @Author: jeecg-boot
 * @Date: 2022-10-20
 * @Version: V1.0
 */
@Api(tags = "图书")
@RestController
@RequestMapping("/pavilion/pavilionBook")
@Slf4j
public class PavilionBookController extends JeecgController<PavilionBook, IPavilionBookService> {

    @Autowired
    private IPavilionBookService pavilionBookService;

    /**
     * 分页列表查询
     *
     * @param pavilionBook
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "图书-分页列表查询")
    @ApiOperation(value = "图书-分页列表查询", notes = "图书-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<PavilionBook>> queryPageList(PavilionBook pavilionBook,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<PavilionBook> queryWrapper = QueryGenerator.initQueryWrapper(pavilionBook, req.getParameterMap());
        Page<PavilionBook> page = new Page<PavilionBook>(pageNo, pageSize);
        IPage<PavilionBook> pageList = pavilionBookService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param pavilionBook
     * @return
     */
    @AutoLog(value = "图书-添加")
    @ApiOperation(value = "图书-添加", notes = "图书-添加")
    //@RequiresPermissions("org.jeecg.modules:pavilion_book:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody PavilionBook pavilionBook) {
        pavilionBookService.save(pavilionBook);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param pavilionBook
     * @return
     */
    @AutoLog(value = "图书-编辑")
    @ApiOperation(value = "图书-编辑", notes = "图书-编辑")
    //@RequiresPermissions("org.jeecg.modules:pavilion_book:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody PavilionBook pavilionBook) {
        pavilionBookService.updateById(pavilionBook);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "图书-通过id删除")
    @ApiOperation(value = "图书-通过id删除", notes = "图书-通过id删除")
    //@RequiresPermissions("org.jeecg.modules:pavilion_book:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        pavilionBookService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "图书-批量删除")
    @ApiOperation(value = "图书-批量删除", notes = "图书-批量删除")
    //@RequiresPermissions("org.jeecg.modules:pavilion_book:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.pavilionBookService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "图书-通过id查询")
    @ApiOperation(value = "图书-通过id查询", notes = "图书-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<PavilionBook> queryById(@RequestParam(name = "id", required = true) String id) {
        PavilionBook pavilionBook = pavilionBookService.getById(id);
        if (pavilionBook == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(pavilionBook);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param pavilionBook
     */
    //@RequiresPermissions("org.jeecg.modules:pavilion_book:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PavilionBook pavilionBook) {
        return super.exportXls(request, pavilionBook, PavilionBook.class, "图书");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("pavilion_book:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PavilionBook.class);
    }

    /**
     * 图书信息查询API
     *
     * @param keyword 待查询的关键词
     * @return 书本信息的List
     */
    @GetMapping(value = "getBookInformation")
    @ResponseBody
    public Result<List<PavilionBook>> getBookInformation(String keyword) {
        return pavilionBookService.getBookInformation(keyword);
    }


}
