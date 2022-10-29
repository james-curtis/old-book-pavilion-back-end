package org.jeecg.modules.pavilion.service;

import org.jeecg.modules.pavilion.entity.PavilionGoodImage;
import org.jeecg.modules.pavilion.entity.PavilionGood;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
public interface IPavilionGoodService extends IService<PavilionGood> {

	/**
	 * 添加一对多
	 *
	 * @param pavilionGood
	 * @param pavilionGoodImageList
	 */
	public void saveMain(PavilionGood pavilionGood,List<PavilionGoodImage> pavilionGoodImageList) ;
	
	/**
	 * 修改一对多
	 *
	 * @param pavilionGood
	 * @param pavilionGoodImageList
	 */
	public void updateMain(PavilionGood pavilionGood,List<PavilionGoodImage> pavilionGoodImageList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
