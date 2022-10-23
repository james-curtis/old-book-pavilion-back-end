package org.jeecg.modules.pavilion.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 图书
 * @Author: jeecg-boot
 * @Date:   2022-10-20
 * @Version: V1.0
 */
@Data
@TableName("pavilion_book")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pavilion_book对象", description="图书")
public class PavilionBook implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**主题词*/
	@Excel(name = "主题词", width = 15)
    @ApiModelProperty(value = "主题词")
    private java.lang.String keyword;
	/**开本*/
	@Excel(name = "开本", width = 15)
    @ApiModelProperty(value = "开本")
    private java.lang.String folio;
	/**中图法分类*/
	@Excel(name = "中图法分类", width = 15)
    @ApiModelProperty(value = "中图法分类")
    private java.lang.String clc;
	/**CIP核准号*/
	@Excel(name = "CIP核准号", width = 15)
    @ApiModelProperty(value = "CIP核准号")
    private java.lang.String cip;
	/**印次*/
	@Excel(name = "印次", width = 15)
    @ApiModelProperty(value = "印次")
    private java.lang.String impression;
	/**版次*/
	@Excel(name = "版次", width = 15)
    @ApiModelProperty(value = "版次")
    private java.lang.String revision;
	/**封面图*/
	@Excel(name = "封面图", width = 15)
    @ApiModelProperty(value = "封面图")
    private java.lang.String cover;
	/**页数*/
	@Excel(name = "页数", width = 15)
    @ApiModelProperty(value = "页数")
    private java.lang.String pages;
	/**语种*/
	@Excel(name = "语种", width = 15)
    @ApiModelProperty(value = "语种")
    private java.lang.String language;
	/**目录信息*/
	@Excel(name = "目录信息", width = 15)
    @ApiModelProperty(value = "目录信息")
    private java.lang.String catalog;
	/**ISBN编号*/
	@Excel(name = "ISBN编号", width = 15)
    @ApiModelProperty(value = "ISBN编号")
    private java.lang.String isbn;
	/**书名*/
	@Excel(name = "书名", width = 15)
    @ApiModelProperty(value = "书名")
    private java.lang.String name;
	/**副书名*/
	@Excel(name = "副书名", width = 15)
    @ApiModelProperty(value = "副书名")
    private java.lang.String subtitle;
	/**作者名*/
	@Excel(name = "作者名", width = 15)
    @ApiModelProperty(value = "作者名")
    private java.lang.String authorName;
	/**作者id*/
	@Excel(name = "作者id", width = 15)
    @ApiModelProperty(value = "作者id")
    private java.lang.String authorId;
	/**发布时间*/
	@Excel(name = "发布时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布时间")
    private java.util.Date releaseTime;
	/**摘要*/
	@Excel(name = "摘要", width = 15)
    @ApiModelProperty(value = "摘要")
    private java.lang.String summary;
	/**出版社*/
	@Excel(name = "出版社", width = 15)
    @ApiModelProperty(value = "出版社")
    private java.lang.String press;
	/**出版地*/
	@Excel(name = "出版地", width = 15)
    @ApiModelProperty(value = "出版地")
    private java.lang.String publicationPlace;
	/**出版时间*/
	@Excel(name = "出版时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出版时间")
    private java.util.Date publishTime;
	/**标定价格*/
	@Excel(name = "标定价格", width = 15)
    @ApiModelProperty(value = "标定价格")
    private java.math.BigDecimal price;
	/**装帧方式*/
	@Excel(name = "装帧方式", width = 15)
    @ApiModelProperty(value = "装帧方式")
    private java.lang.String bindingMode;
}
