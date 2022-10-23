package org.jeecg.modules.pavilion.service;

import org.jeecg.modules.pavilion.entity.PavilionGoodImage;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 商品图片信息
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
public interface IPavilionGoodImageService extends IService<PavilionGoodImage> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<PavilionGoodImage>
	 */
	public List<PavilionGoodImage> selectByMainId(String mainId);
}
