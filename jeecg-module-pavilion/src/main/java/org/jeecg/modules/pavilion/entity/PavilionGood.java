package org.jeecg.modules.pavilion.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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

/**
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
@ApiModel(value="pavilion_good对象", description="商品")
@Data
@TableName("pavilion_good")
public class PavilionGood implements Serializable {
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
	/**商品标题*/
	@Excel(name = "商品标题", width = 15)
    @ApiModelProperty(value = "商品标题")
    private java.lang.String title;
	/**商品介绍*/
	@Excel(name = "商品介绍", width = 15)
    @ApiModelProperty(value = "商品介绍")
    private java.lang.String summary;
	/**价格*/
	@Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "价格")
    private java.math.BigDecimal price;
	/**发布用户*/
	@Excel(name = "发布用户", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @ApiModelProperty(value = "发布用户")
    private java.lang.String userId;
	/**ISBN编号*/
	@Excel(name = "ISBN编号", width = 15)
    @ApiModelProperty(value = "ISBN编号")
    private java.lang.String isbn;
	/**对应图书*/
	@Excel(name = "对应图书", width = 15)
    @ApiModelProperty(value = "对应图书")
    private java.lang.String bookId;
	/**浏览量*/
	@Excel(name = "浏览量", width = 15)
    @ApiModelProperty(value = "浏览量")
    private java.lang.Integer views;
}
