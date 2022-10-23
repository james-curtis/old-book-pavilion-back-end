package org.jeecg.modules.pavilion.mapper;

import java.util.List;
import org.jeecg.modules.pavilion.entity.PavilionGoodImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商品图片信息
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
public interface PavilionGoodImageMapper extends BaseMapper<PavilionGoodImage> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<PavilionGoodImage>
   */
	public List<PavilionGoodImage> selectByMainId(@Param("mainId") String mainId);
}
