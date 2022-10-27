package org.jeecg.modules.pavilion.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.pavilion.entity.PavilionBook;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 图书
 * @Author: jeecg-boot
 * @Date:   2022-10-20
 * @Version: V1.0
 */
public interface IPavilionBookService extends IService<PavilionBook> {

    /**
     * 图书信息查询API
     * @param keyword 查询关键词
     * @return 书本信息的List
     */
    Result<List<PavilionBook>> getBookInformation(String keyword);
}
