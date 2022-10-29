package org.jeecg.modules.pavilion.service.impl;

import org.jeecg.modules.pavilion.entity.PavilionGoodImage;
import org.jeecg.modules.pavilion.mapper.PavilionGoodImageMapper;
import org.jeecg.modules.pavilion.service.IPavilionGoodImageService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 商品图片信息
 * @Author: jeecg-boot
 * @Date:   2022-10-23
 * @Version: V1.0
 */
@Service
public class PavilionGoodImageServiceImpl extends ServiceImpl<PavilionGoodImageMapper, PavilionGoodImage> implements IPavilionGoodImageService {
	
	@Autowired
	private PavilionGoodImageMapper pavilionGoodImageMapper;
	
	@Override
	public List<PavilionGoodImage> selectByMainId(String mainId) {
		return pavilionGoodImageMapper.selectByMainId(mainId);
	}
}
