package org.jeecg.modules.pavilion.service.impl;

import org.jeecg.modules.pavilion.entity.PavilionGood;
import org.jeecg.modules.pavilion.entity.PavilionGoodImage;
import org.jeecg.modules.pavilion.mapper.PavilionGoodImageMapper;
import org.jeecg.modules.pavilion.mapper.PavilionGoodMapper;
import org.jeecg.modules.pavilion.service.IPavilionGoodService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
@Service
public class PavilionGoodServiceImpl extends ServiceImpl<PavilionGoodMapper, PavilionGood> implements IPavilionGoodService {

	@Autowired
	private PavilionGoodMapper pavilionGoodMapper;
	@Autowired
	private PavilionGoodImageMapper pavilionGoodImageMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(PavilionGood pavilionGood, List<PavilionGoodImage> pavilionGoodImageList) {
		pavilionGoodMapper.insert(pavilionGood);
		if(pavilionGoodImageList!=null && pavilionGoodImageList.size()>0) {
			for(PavilionGoodImage entity:pavilionGoodImageList) {
				//外键设置
				entity.setGoodId(pavilionGood.getId());
				pavilionGoodImageMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(PavilionGood pavilionGood,List<PavilionGoodImage> pavilionGoodImageList) {
		pavilionGoodMapper.updateById(pavilionGood);
		
		//1.先删除子表数据
		pavilionGoodImageMapper.deleteByMainId(pavilionGood.getId());
		
		//2.子表数据重新插入
		if(pavilionGoodImageList!=null && pavilionGoodImageList.size()>0) {
			for(PavilionGoodImage entity:pavilionGoodImageList) {
				//外键设置
				entity.setGoodId(pavilionGood.getId());
				pavilionGoodImageMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		pavilionGoodImageMapper.deleteByMainId(id);
		pavilionGoodMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			pavilionGoodImageMapper.deleteByMainId(id.toString());
			pavilionGoodMapper.deleteById(id);
		}
	}
	
}
